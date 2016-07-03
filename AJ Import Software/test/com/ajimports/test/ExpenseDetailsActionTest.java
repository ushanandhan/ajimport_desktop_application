package com.ajimports.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ajimports.dao.ExpenseDetailsDAO;
import com.ajimports.domain.ExpenseDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContextTest.xml")
public class ExpenseDetailsActionTest {

	ExpenseDetails expenseDetails;
	
	@Autowired
	ExpenseDetailsDAO expenseDetailsDAO;
	
	@Before
	public void setup(){
		expenseDetails = new ExpenseDetails();
		expenseDetails.setExpendedFor("Test");
		expenseDetails.setCausedPrice(1200);
		expenseDetails.setExpendedDate(new Date());
	}
	
	@Test
	public void add(){
		expenseDetailsDAO.add(expenseDetails);
	}
	
	@Test
	public void getAllExpense(){
		expenseDetailsDAO.getAllExpense(expenseDetails);
	}
	
	@Test
	public void getTotalExpense(){
		expenseDetailsDAO.getTotalExpense();
	}
	
	@Test
	public void getExpenseByParam(){
		expenseDetailsDAO.getExpenseDetailsByParam("jeeva");
	}
}
