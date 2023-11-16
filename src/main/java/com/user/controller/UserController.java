package com.user.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.User;
import com.user.config.ResponseConfig;
import com.user.dao.UserDAO;
import com.user.errorhandling.ErrorResponse;

@RestController
public class UserController extends ResponseConfig {

	@RequestMapping(method = RequestMethod.GET)
	 public String welcome() {
	     return "Welcome to the page ";
	 }

	@Autowired
	UserDAO userDao;

	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> getAllUser(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
	    try {
	        List<User> userList = (id > 0) ? userDao.getUserById(id) : userDao.getAllUser();
	        if (userList.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Records Found");
	        }
	        return ResponseEntity.status(HttpStatus.OK).body(userList);
	    } catch (DataAccessException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
	    }
	}

	@RequestMapping(value = "/user",method = RequestMethod.POST, produces={"application/json"})
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		ErrorResponse errorResponse = ErrorResponse.validateUserInput(user);

		if (!errorResponse.getErrors().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	    try {
	        User addedUser = userDao.addUser(user);
	        int addedId = addedUser.getId();
	        if (addedId > 0) {
	        	return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
	        }else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(insertError);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(insertExceptionError + "\nException: " + e.getMessage());
		}
	}

	@RequestMapping(value = "/user/{id}" , method = RequestMethod.PUT, produces={"application/json"})
	 public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable("id")int id) {
		ErrorResponse errorResponse = ErrorResponse.validateUserInput(user);

		if (!errorResponse.getErrors().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
       try {
    	 user.setId(id);
      	 int rowsAffected = userDao.updateUser(user);
       	 if (rowsAffected > 0) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
           } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updateError);
            }
       } catch (DataAccessException e) {
           String errorMessage = updateExceptionError + "\nException: " + e.getMessage();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
       }
   }

	@RequestMapping(path = "/user", method = RequestMethod.DELETE, produces={"application/json"})
	 public ResponseEntity<Object> deleteUser(@RequestParam(name = "id", required = false, defaultValue = "0")int id) {
	    try {
	      int rowsAffected = (id > 0) ? userDao.deleteUserById(id) : userDao.deleteAllUser();
	      System.out.println(rowsAffected);
	      if(rowsAffected > 0 && rowsAffected == 1) {
	    	  return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
	      }else if(rowsAffected > 1){
	    	  return ResponseEntity.status(HttpStatus.OK).body("All Records Deleted");
	      }
	      else {
	    	  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
	      }
	    } catch (DataAccessException e) {
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
	    }
	}

}