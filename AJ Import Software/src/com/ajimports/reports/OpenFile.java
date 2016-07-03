package com.ajimports.reports;

import java.awt.Desktop;
import java.io.File;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.ajimports.dao.ProductDAO;
import com.ajimports.view.HomeWindow;
import com.ajimports.view.ProductView;

public class OpenFile {
	
	public static Logger logger = Logger.getLogger(OpenFile.class);
	public static String fileName;
	
	public static void openPdf(String reportName){
		try {
			Calendar dateForAppend = Calendar.getInstance();
			fileName ="- "+"( "+dateForAppend.get(Calendar.DATE)+" - "+(dateForAppend.get(Calendar.MONTH)+1)+" - "+dateForAppend.get(Calendar.YEAR)+" )"; 
			File pdfFile = new File("D://PROGRAMS//Reports//"+reportName+fileName+".pdf");
			if (pdfFile.exists()) {
    			if (Desktop.isDesktopSupported()) {
    				Desktop.getDesktop().open(pdfFile);
    			} else {
    				logger.error("Awt Desktop is not supported!");
    			}
    		} else {
    			logger.error("File not exists!");
    		}
    		logger.info("File is saved in Documents...");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
	}
	
}
