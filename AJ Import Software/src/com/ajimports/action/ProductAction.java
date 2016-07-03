package com.ajimports.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ajimports.dao.ProductDAO;
import com.ajimports.domain.Product;

public class ProductAction {
	
	@Autowired
	ProductDAO productDAO;
	
	public void add(Product product){
		productDAO.add(product);
	}
	
	public List<Product> getAllProducts(Product product){
		return productDAO.getAllProducts(product);
	}
}
