package com.ajimports.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ajimports.dao.UserDAO;
import com.ajimports.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContextTest.xml")
public class UserActionTest {

	User user;
	
	@Autowired
	UserDAO userDAO;
	
	@Before
	public void setup(){
		user = new User();
		user.setUserName("ushan");
		user.setPassWord("nandhan");
	}
	
	@Test
	public void checkLogin(){
		boolean status = userDAO.checkLogin(user);
		assertTrue("Returned user Status is : "+status, status);
	}
}
