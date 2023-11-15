package com.common.api.resource;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.api.constant.APIFixedConstant;
import com.common.api.response.Student;
import com.common.api.util.ErrorValidation;
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
public class StudentResource extends APIFixedConstant {
	
	@Value("${DELETE_SUCCESS}")
	public String deleteSuccess = "";

	@Value("${INSERT_EXCEPTION_ERROR}")
	public String insertExceptionError = "";

	@Value("${UPDATE_EXCEPTION_ERROR}")
	public String updateExceptionError = "";

	@Value("${DELETE_EXCEPTION_ERROR}")
	public String deleteExceptionError = "";

	@Value("${DELETE_ALL_SUCCESS}")
	public String deleteAllRecordsSuccess = "";

	@Value("${RECORD_NOT_FOUND}")
	public String recordNotFound = "";

	@Autowired
	StudentService studentDao;

	@Autowired
	private ErrorValidation errorValidation;
	
	@RequestMapping(value = "/student", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Object> getStudentByCriteria(
			@RequestParam(name = "id", required = false, defaultValue = "0") Integer id,
			@RequestParam(name = "departmentId", required = false, defaultValue = "0") Integer departmentId,
			@RequestParam(name = "studentName", required = false, defaultValue = "") String studentName,
			@RequestParam(name = "dateOfBirth", required = false) @JsonFormat(pattern="yyyy-MM-dd") Date dateOfBirth,
			@RequestParam(name = "gender", required = false, defaultValue = "") String gender,
			@RequestParam(name = "contactNumber", required = false, defaultValue = "0") Long contactNumber,
			@RequestParam(name = "email", required = false, defaultValue = "") String email) {
		
		ResponseEntity<Object> response = ResponseEntity.ok("");
		ErrorValidation errorResponse = errorValidation.validateInput(Optional.of(id), Optional.empty());
		if (!errorResponse.getErrors().isEmpty()) {
			
			response =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} else {

			try {			
				    
				List<Student> students = studentDao.getAllStudent(id, departmentId, studentName, dateOfBirth, gender, contactNumber, email);
				if (students.isEmpty()) {
					errorResponse.addError(RECORD_NOT_FOUND);
					response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
				}else {
					response = ResponseEntity.status(HttpStatus.OK).body(students);
				}
			} catch (DataAccessException e) {
			}

		}
		return response;
	}

	@RequestMapping(value = "/student", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addStudent(@RequestBody Student student) {
		ResponseEntity<Object> response = ResponseEntity.ok("");
		ErrorValidation errorResponse = errorValidation.validateInput(Optional.empty(), Optional.of(student));
		if (!errorResponse.getErrors().isEmpty()) {
			response =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} else {

			try {
				Student addedStudent = studentDao.addStudent(student);
				int addedId = addedStudent.getId();
				if (addedId > 0) {
					response = ResponseEntity.status(HttpStatus.CREATED).body(addedStudent);
				} else {
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(insertExceptionError);
				}
			} catch (DataAccessException e) {
			}
		}
		return response;
	}

	@RequestMapping(value = "/student", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateStudent(@RequestParam(name = "id", required = false, defaultValue = "0") int id,@RequestBody Student student) {
		ResponseEntity<Object> response = ResponseEntity.ok("");
		ErrorValidation errorResponse = errorValidation.validateInput(Optional.of(id), Optional.of(student));
		System.out.println(errorResponse.getErrors());
		if (!errorResponse.getErrors().isEmpty()) {
			response =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} else {
			try {
				student.setId(id);
				int rowsAffected = studentDao.updateStudent(student);
				if (rowsAffected > 0) {
					response = ResponseEntity.status(HttpStatus.ACCEPTED).body(student);
				} else {
					errorResponse.addError(RECORD_NOT_FOUND);
					response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
				}
			} catch (DataAccessException e) {
			}
		}
		return response;
	}

	@RequestMapping(value = "/student", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteStudent(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
		ResponseEntity<Object> response = ResponseEntity.ok("");
		ErrorValidation errorResponse = errorValidation.validateInput(Optional.of(id), Optional.empty());

		if (!errorResponse.getErrors().isEmpty()) {
			response =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} else {

			try {
				int rowsAffected = studentDao.deleteStudent(id);
				String successMessage = (id > 0) ? deleteSuccess : deleteAllRecordsSuccess;
				if (rowsAffected > 0) {
					response = ResponseEntity.status(HttpStatus.OK).body(successMessage);
				} else {
					errorResponse.addError(RECORD_NOT_FOUND);
					response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
				}
			} catch (DataAccessException e) {
			}
			
		}
		return response;
	}

}
