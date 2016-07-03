package com.ajimports.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ajimports.domain.User;

public class UserDAO extends HibernateDaoSupport {

	public static Logger logger = Logger.getLogger(UserDAO.class);
	private String domainPakage = "com.ajimports.domain.";
	
	public User getUser(User user){
		logger.info("Enter to get User");
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("userName", user.getUserName()));
		criteria.add(Restrictions.eq("passWord", user.getPassWord()));
		return (User) criteria.uniqueResult();
	}
	
	public void addUser(User user){
		Transaction tr = getSession().beginTransaction();
		logger.info("Entered to add user");
		getSession().save(user);
		tr.commit();
		logger.info("Leaving from add user");
	}
	
	public boolean checkLogin(User user){
		logger.info("Enter to check User");
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("userName", user.getUserName()));
		criteria.add(Restrictions.eq("passWord", user.getPassWord()));
		if(criteria.uniqueResult() != null ){
			return true;
		}else{
			return false;
		}
	}
	
	public User getUserByUserID(int userId){
		logger.info("Get User By User ID ");
		Query query = null;
		String queryString = "select user from "+domainPakage+"User user where user.userId="+userId;
		query = getSession().createQuery(queryString);
		User user = (User)query.uniqueResult();
		return user;
	}
	
	public void updateUser(User user){
		logger.info("Updating User Information");
		Transaction tr = getSession().beginTransaction();
		User user1 = (User)getSession().get(User.class, user.getUserId());
		user1.setUserName(user.getUserName());
		user1.setPassWord(user.getPassWord());
		user1.setImage(user.getImage());
		getSession().update(user);
//		getHibernateTemplate().update(user1);
		tr.commit();
		/*Query query = null;
		String queryString = "update "+domainPakage+"User set userName="+user.getUserName()+", passWord="+user.getPassWord()+",image="+user.getImage()+" where userId="+user.getUserId();
		query = getSession().createQuery(queryString);
		query.executeUpdate();*/
	}
}
