package com.ajimports.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.ajimports.dao.UserDAO;
import com.ajimports.domain.User;

public class UserAction {

	@Autowired
	UserDAO userDAO;
	
	public void addUser(User user){
		userDAO.addUser(user);
	}
	
	public User getUser(User user){
		return userDAO.getUser(user);
	}
	
	public boolean checkLogin(User user){
		return userDAO.checkLogin(user);
	}
	
	public User getUserByUserID(int userId){
		return userDAO.getUserByUserID(userId);
	}
	
	public void updateUser(User user){
		userDAO.updateUser(user);
	}
}
