package com.common.api.response;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {

	@JsonProperty("id")
	private int id = 0;

	@JsonProperty("department_id")
	private int departmentId = 0;

	@JsonProperty("student_name")
	private String studentName = "";
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("date_of_birth")
	private Date dateOfBirth = null ;
	
	@JsonProperty("gender")
	private String gender = "";
	
	@JsonProperty("contact_number")
	private long contactNumber = 0;
	
	@JsonProperty("email")
	private String email = "";
	
	public Student() {
		
	}
	
	public Student(int id, int departmentId, String studentName, Date dateOfBirth, String gender, long contactNumber,
			String email) {
		super();
		this.id = id;
		this.departmentId = departmentId;
		this.studentName = studentName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.contactNumber = contactNumber;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public long getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", departmentId=" + departmentId + ", studentName=" + studentName
				+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", contactNumber=" + contactNumber
				+ ", email=" + email + "]";
	}

}
