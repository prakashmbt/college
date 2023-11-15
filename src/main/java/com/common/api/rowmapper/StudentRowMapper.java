package com.common.api.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;
import com.common.api.response.Student;
import com.common.api.util.DataUtil;

public class StudentRowMapper extends DataUtil implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

		Student student = new Student();
		try {
			student = new Student();
			student.setId(resultSetToInt(rs, "id"));
			student.setDepartmentId(resultSetToInt(rs, "department_id"));
			student.setStudentName(resultSetToString(rs, "student_name"));
	        Date sqlDateOfBirth = getAdjustedSqlDate(rs, "date_of_birth");
	        student.setDateOfBirth(sqlDateOfBirth);

            student.setDateOfBirth(sqlDateOfBirth);
	        student.setGender(resultSetToString(rs, "gender"));
			student.setContactNumber(resultSetToLong(rs, "contact_number"));
			student.setEmail(resultSetToString(rs, "email"));
		} catch (Exception e) {
		}

		return student;
	}
	
	private Date getAdjustedSqlDate(ResultSet rs, String columnName) throws SQLException {
	    Timestamp timestamp = rs.getTimestamp(columnName);
	    if (timestamp != null) {
	        timestamp.setTime(timestamp.getTime() + 24 * 60 * 60 * 1000);
	    }
	    return (timestamp != null) ? new Date(timestamp.getTime()) : null;
	}

}
