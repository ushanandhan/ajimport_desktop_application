package com.ajimports.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ajimports.domain.Product;


public class ProductDAO extends HibernateDaoSupport {
	public static Logger logger = Logger.getLogger(ProductDAO.class);
	
	public void add(Product product){
		Transaction tr = getSession().beginTransaction();
		logger.info("Enter to Create Product");
		getSession().save(product);
		tr.commit();
	}
	
	public List<Product> getAllProducts(Product product){
		logger.info("Enter to getAll Products");
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("date", product.getDate()));
		return criteria.list();
	}
}
