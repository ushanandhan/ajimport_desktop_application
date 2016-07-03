package com.ajimports.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="expense_details")
public class ExpenseDetails {

	private int expenseId;
	private String expendedFor;
	private int causedPrice;
	private Date expendedDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EXPENSE_ID")
	public int getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}
	
	@Column(name="DESCRIPTION")
	public String getExpendedFor() {
		return expendedFor;
	}
	public void setExpendedFor(String expendedFor) {
		this.expendedFor = expendedFor;
	}
	
	@Column(name="PRICE_CAUSED")
	public int getCausedPrice() {
		return causedPrice;
	}
	public void setCausedPrice(int causedPrice) {
		this.causedPrice = causedPrice;
	}
	
	@Column(name="EXPENDED_DATE")
	public Date getExpendedDate() {
		return expendedDate;
	}
	public void setExpendedDate(Date expendedDate) {
		this.expendedDate = expendedDate;
	}
	
	
}
