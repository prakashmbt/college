package com.user.dao;

import java.util.List;

import com.user.User;
import com.user.constants.Constants;

public abstract class UserDAO extends Constants{

	public abstract List<User> getAllUser();
	
	public abstract List<User> getUserById(int id);

	public abstract User addUser(User user);

	public abstract int updateUser(User user);

	public abstract int deleteUserById(int id);
	
	public abstract int deleteAllUser();
	
}
