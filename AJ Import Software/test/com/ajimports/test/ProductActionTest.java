package com.ajimports.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ajimports.dao.ProductDAO;
import com.ajimports.domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:ApplicationContextTest.xml")
public class ProductActionTest {

	Product product;
	
	@Autowired
	ProductDAO productDAO;
	
	@Before
	public void setup(){
		product = new Product();
		product.setDate(new Date());
		product.setProductName("TestProduct");
		product.setProductPrice(1000);
		product.setProductQuantity(5);
	}
	
	@Test
	public void add(){
		productDAO.add(product);
	}
	
	@Test
	public void getProducts(){
		productDAO.getAllProducts(product);
	}
}
