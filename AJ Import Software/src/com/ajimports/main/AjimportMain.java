package com.ajimports.main;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ajimports.view.HomeWindow;
import com.ajimports.view.LoginFormView;

public class AjimportMain {
	
	static Connection con = null;
	
	public static Connection getCon() {
		return con;
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext appConnec = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		DataSource ds = (DataSource) appConnec.getBean("dataSource");
		try {
			con = ds.getConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//HomeWindow view = (HomeWindow) appConnec.getBean("homeWindowView");
		//view.setVisible(true);
		LoginFormView loginForm = (LoginFormView) appConnec.getBean("loginFormView");
		loginForm.setVisible(true);
	}
}
