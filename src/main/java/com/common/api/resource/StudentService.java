package com.common.api.resource;

import java.sql.Date;
import java.util.List;

import com.common.api.response.Student;

public interface StudentService {

	public Student addStudent(Student student);

	public int updateStudent(Student student);

	public int deleteStudent(int id);
	
	public List<Student> getAllStudent(Integer id, Integer departmentId, String studentName, Date dateOfBirth , String gender, Long contactNumber, String email);
}
