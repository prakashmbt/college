package com.common.api.resource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.common.api.constant.APIFixedConstant;
import com.common.api.response.Student;
import com.common.api.rowmapper.StudentRowMapper;

@Repository
public class StudentServiceImpl extends APIFixedConstant implements StudentService {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbctemplate;
	
	@Override
	public List<Student> getAllStudent(Integer id, Integer departmentId, String studentName, Date dateOfBirth, String gender, Long contactNumber, String email) {
	    List<Student> response = new ArrayList<>();
		try {
	    String sql = "SELECT * FROM " + TABLE_STUDENTS + " WHERE "
	    		+ "((:id > 0 AND id = :id) OR (:id <= 0)) AND "
	    		+ "((:departmentId > 0 AND department_id = :departmentId) OR (:departmentId <= 0)) AND "
	    		+ "((:dateOfBirth IS NULL OR date_of_birth = :dateOfBirth )) AND "
	    		+ "((length(:studentName) > 0 AND student_name = :studentName) OR (length(:studentName) <= 0)) AND "
	    		+ "((length(:gender) > 0 AND gender = :gender) OR (length(:gender) <= 0)) AND "
	    		+ "((:contactNumber > 0 AND contact_number = :contactNumber) OR (:contactNumber <= 0)) AND "
	    		+ "((length(:email) > 0 AND email = :email) OR (length(:email) <= 0))";	

	    MapSqlParameterSource mapper = new MapSqlParameterSource();

		 mapper.addValue("id", id);
		 mapper.addValue("departmentId", departmentId);
		 mapper.addValue("studentName", studentName);
		 mapper.addValue("dateOfBirth", dateOfBirth);
		 mapper.addValue("gender", gender);
		 mapper.addValue("contactNumber", contactNumber);
		 mapper.addValue("email", email);
		 response = namedParameterJdbctemplate.query(sql, mapper, new StudentRowMapper());
		}catch(DataAccessException e) {
	        
		}
	    return response;
	}

	@Override
	public Student addStudent(Student student) {	
		try {
			String sql = "INSERT INTO "+ TABLE_STUDENTS +" (department_id, student_name, date_of_birth, gender, contact_number, email) " +
                    "VALUES (:departmentId, :studentName, :dateOfBirth, :gender, :contactNumber, :email)";
			MapSqlParameterSource mapper = new MapSqlParameterSource();
			mapper.addValue("departmentId", student.getDepartmentId());
			mapper.addValue("studentName", student.getStudentName());
			mapper.addValue("dateOfBirth", student.getDateOfBirth());
			mapper.addValue("gender", student.getGender());
			mapper.addValue("contactNumber", student.getContactNumber());
			mapper.addValue("email", student.getEmail());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int rowsAffected = namedParameterJdbctemplate.update(sql, mapper,keyHolder);
			Number generatedKey = keyHolder.getKey();

			if(rowsAffected > 0 && generatedKey !=null) {
				student.setId(generatedKey.intValue());
				return student;
			}else {
				return new Student();
			}
		}catch(DataAccessException e) {
	        
		}
		return student;
	}

	@Override
	public int updateStudent(Student student) {
		int response = 0;
		try {
			 String sql = "UPDATE "+ TABLE_STUDENTS +" SET department_id = :departmentId, student_name = :studentName, "
					 + "date_of_birth = :dateOfBirth, gender = :gender, contact_number = :contactNumber, "
					 + "email = :email WHERE id = :id";
			 MapSqlParameterSource mapper = new MapSqlParameterSource();
			 mapper.addValue("id", student.getId());
			 mapper.addValue("departmentId", student.getDepartmentId());
			 mapper.addValue("studentName", student.getStudentName());
			 mapper.addValue("dateOfBirth", student.getDateOfBirth());
			 mapper.addValue("gender", student.getGender());
			 mapper.addValue("contactNumber", student.getContactNumber());
			 mapper.addValue("email", student.getEmail());

			 response =  namedParameterJdbctemplate.update(sql, mapper);

		}catch(DataAccessException e) {
			
		}	
		return response;
	}

	@Override
	public int deleteStudent(int id) {
		int response = 0;
		try {
			String sql = "DELETE FROM "+ TABLE_STUDENTS +" WHERE "
					+ "((:id > 0 AND id = :id) OR (:id <= 0))";
			MapSqlParameterSource mapper = new MapSqlParameterSource();
			mapper.addValue("id", id);
			response =  namedParameterJdbctemplate.update(sql, mapper);
		}catch(DataAccessException e) {
			
		}
		return response;
	}

}

