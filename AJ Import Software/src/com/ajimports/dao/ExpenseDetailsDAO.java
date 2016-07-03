package com.ajimports.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ajimports.domain.ExpenseDetails;

public class ExpenseDetailsDAO extends HibernateDaoSupport {
	public static Logger logger = Logger.getLogger(ExpenseDetailsDAO.class);
	private String domainPakage = "com.ajimports.domain.";
	public void add(ExpenseDetails expenseDetails){
		Transaction tr = getSession().beginTransaction();
		logger.info("Enter to Add Expense");
		getSession().save(expenseDetails);
		tr.commit();
	}
	
	public List<ExpenseDetails> getAllExpense(ExpenseDetails expenseDetails){
		logger.info("Enter to getAll Expenses");

//		Criteria criteria = getSession().createCriteria(ExpenseDetails.class);
//		criteria.add(Restrictions.eq("expendedDate", expenseDetails.getExpendedDate()));
//		return criteria.list();
		Query query = null;
		try {
			String queryString = "SELECT expenseDetails FROM "+domainPakage+"ExpenseDetails expenseDetails ";
			query = getSession().createQuery(queryString);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<ExpenseDetails> list = query.list();
		for (ExpenseDetails expenseDetails1 : list) {
			System.out.println("Expended for is : "+expenseDetails1.getExpendedFor());
			System.out.println("Price is : "+expenseDetails1.getCausedPrice());
		}
		return list;
	}
	
	public List<ExpenseDetails> getTotalExpense(){
		Query query = null;
		logger.info("Enter to get Total Expenses so far!!!!!!!!!!!!");
//		Criteria criteria = getSession().createCriteria(ExpenseDetails.class);
		try {
//			criteria.setProjection(Projections.sum("causedPrice"));
			String queryString = "SELECT sum(e.causedPrice) as total FROM "+domainPakage+"ExpenseDetails e ";
			query = getSession().createQuery(queryString);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return query.list();
//		return criteria.list();
	}
	
	public List getExpenseDetailsWithInRange(Date fromDate,Date endDate){
		logger.info("Enter to get DataType With In Range");
		Query query = null;
		String queryString = "select expenseDetails from "+domainPakage+"ExpenseDetails expenseDetails where expenseDetails.expendedDate between :dateFrom and :dateTo";
		query = getSession().createQuery(queryString);
		query.setDate("dateFrom", fromDate);
		query.setDate("dateTo", endDate);
		List<ExpenseDetails> list = query.list();
		for (ExpenseDetails expenseDetails : list) {
			System.out.println("Expended is : "+expenseDetails.getExpendedFor());
			System.out.println("Price is : "+expenseDetails.getCausedPrice());
		}
		
		return null;
	}
	
	public List<ExpenseDetails> getExpenseDetailsByParam(String param){
		logger.info("Enter to get DataType With In Range");
		Query query = null;
		String queryString = "select expenseDetails from "+domainPakage+"ExpenseDetails expenseDetails where expenseDetails.expendedFor like '%"+param+"%'";
		query = getSession().createQuery(queryString);
		List<ExpenseDetails> list = query.list();
		for (ExpenseDetails expenseDetails : list) {
			System.out.println("Expended is : "+expenseDetails.getExpendedFor());
			System.out.println("Price is : "+expenseDetails.getCausedPrice());
		}
		
		return list;
	}
}
