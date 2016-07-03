package com.ajimports.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ajimports.dao.ExpenseDetailsDAO;
import com.ajimports.domain.ExpenseDetails;

public class ExpenseDetailsAction {

	@Autowired
	ExpenseDetailsDAO expenseDetailsDAO;

	public void add(ExpenseDetails expenseDetails){
		expenseDetailsDAO.add(expenseDetails);
	}
	
	public List<ExpenseDetails> getAllExpense(ExpenseDetails expenseDetails){
		return expenseDetailsDAO.getAllExpense(expenseDetails);
	}
	
	public List<ExpenseDetails> getTotalExpense(){
		return expenseDetailsDAO.getTotalExpense();
	}
	
	public void getExpenseDetailsWithInRange(Date fromDate,Date endDate){
		expenseDetailsDAO.getExpenseDetailsWithInRange(fromDate, endDate);
	}
	
	public List<ExpenseDetails> getExpenseDetailsByParam(String param){
		return expenseDetailsDAO.getExpenseDetailsByParam(param);
	}
}
