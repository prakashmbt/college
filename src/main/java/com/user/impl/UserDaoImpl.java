package com.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.user.User;
import com.user.dao.UserDAO;
import com.user.mapper.UserRowMapper;

@Repository
public class UserDaoImpl extends UserDAO{
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Override
	public List<User> getAllUser() {
		 String getQuery = "SELECT * FROM "+ TABLE_NAME;
		 List<User> userList= namedParameterJdbcTemplate.query(getQuery, new UserRowMapper());
		 return userList;
	}
	
    @Override
	public List<User> getUserById(int id) {
         String getQuery = "SELECT * FROM "+ TABLE_NAME +" WHERE id = :id";
         MapSqlParameterSource mapper = new MapSqlParameterSource();
         mapper.addValue("id", id);
         List<User> user = namedParameterJdbcTemplate.query(getQuery, mapper, new UserRowMapper());
         return user;
    }

	@Override
	public User addUser(User user) {
	    String addQuery = "INSERT INTO " + TABLE_NAME + "(role_id, user_name, email_address, phone_number, salary, skills, address) " +
	                        					"VALUES (:roleId, :userName, :emailAddress, :phoneNumber, :salary, :skills, :address)";

	    MapSqlParameterSource mapper = new MapSqlParameterSource();
	    mapper.addValue("roleId",       user.getRoleId());
	    mapper.addValue("userName",     user.getUserName());
	    mapper.addValue("emailAddress", user.getEmailAddress());
	    mapper.addValue("phoneNumber",  user.getPhoneNumber());
	    mapper.addValue("salary", 		user.getSalary());    
	    mapper.addValue("skills", 		convertListToJson(user.getSkills()));
	    mapper.addValue("address", 		convertListToJson(user.getAddress())); 
	    
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    int rowsAffected = namedParameterJdbcTemplate.update(addQuery, mapper, keyHolder);
	    Number generatedKey = keyHolder.getKey();

	    if (rowsAffected > 0 && generatedKey != null) {
	        user.setId(generatedKey.intValue());
	        return user;
	    } else {
	        return new User();
	    }
	}

	public int updateUser(User user) {
	    String updateQuery = "UPDATE " + TABLE_NAME
	    	+ " SET role_id = :roleId, "
	            + "user_name = :userName, "
	            + "email_address = :emailAddress, "
	            + "phone_number = :phoneNumber, "
	            + "salary = :salary, "
	            + "skills = :skills, "
	            + "address = :address "	
	     + "WHERE id = :id";

	    MapSqlParameterSource mapper = new MapSqlParameterSource();

	    mapper.addValue("id", 			user.getId());
	    mapper.addValue("roleId", 		user.getRoleId());
	    mapper.addValue("userName",		user.getUserName());
	    mapper.addValue("emailAddress", user.getEmailAddress());
	    mapper.addValue("phoneNumber", 	user.getPhoneNumber());
	    mapper.addValue("salary", 		user.getSalary());
	    mapper.addValue("skills", 		convertListToJson(user.getSkills()));
	    mapper.addValue("address", 		convertListToJson(user.getAddress()));    
	    return namedParameterJdbcTemplate.update(updateQuery, mapper);
	}
	
    @Override
	public int deleteUserById(int id) {
         String deleteQuery = "DELETE FROM "+ TABLE_NAME +" WHERE id = :id";
         MapSqlParameterSource mapper = new MapSqlParameterSource();
         mapper.addValue("id", id);
         return namedParameterJdbcTemplate.update(deleteQuery, mapper);
    }
    
    @Override
    public int deleteAllUser() {
    	String deleteQuery = "DELETE FROM "+ TABLE_NAME;  	
    	return namedParameterJdbcTemplate.update(deleteQuery, new MapSqlParameterSource());
    }
    
    public String convertListToJson(List<?> list) {
	      ObjectMapper objectMapper = new ObjectMapper();
	      try {
	          return objectMapper.writeValueAsString(list);
	      } catch (Exception e) {
	          throw new RuntimeException(JSON_ERROR, e);
	      }
	  }

}
