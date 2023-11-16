package com.user.mapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.Address;
import com.user.User;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSetToInt(resultSet, "id"));
        user.setRoleId(resultSetToInt(resultSet, "role_id"));
        user.setUserName(resultSetToString(resultSet, "user_name"));
        user.setEmailAddress(resultSetToString(resultSet, "email_address"));
        user.setPhoneNumber(resultSetToLong(resultSet, "phone_number"));
        user.setSalary(resultSetToFloat(resultSet, "salary"));
        user.setSkills(resultSetToListOfString(resultSet, "skills"));
        user.setAddress(resultSetToAddress(resultSet, "address"));

        return user;
    }

    public String resultSetToString(ResultSet resultSet, String key) {
        try {
            String result = resultSet.getString(key);
            return (result != null) ? result : "";
        } catch (SQLException e) {
            return "";
        }
    }

    public int resultSetToInt(ResultSet resultSet, String key) {
        try {
            int result = resultSet.getInt(key);
            return (result > 0) ? result : 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    public long resultSetToLong(ResultSet resultSet, String key) {
        try {
            long result = resultSet.getLong(key);
            return (result > 0) ? result : 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    public float resultSetToFloat(ResultSet resultSet, String key) {
        try {
            float result = resultSet.getFloat(key);
            return (result > 0) ? result : 0.0f;
        } catch (SQLException e) {
            return 0.0f;
        }
    }

    public List<String> resultSetToListOfString(ResultSet resultSet, String key) {
        try {
            String jsonString = resultSet.getString(key);
            if (jsonString != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return Arrays.asList(objectMapper.readValue(jsonString, String[].class));
            }
            return Collections.emptyList();
        } catch (SQLException | IOException e) {
            return Collections.emptyList();
        }
    }

  public List<Address> resultSetToAddress(ResultSet resultSet, String key) {
        try {
            String jsonString = resultSet.getString(key);
            if (jsonString != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(jsonString, new TypeReference<List<Address>>() {});
            }
            return new ArrayList<>();
        } catch (SQLException | IOException e) {
            return new ArrayList<>();
        }
    }
  
}
