/*
 * Created by JFormDesigner on Sun Jul 01 12:58:25 IST 2012
 */

package com.ajimports.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.*;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendarCombo;
import org.springframework.beans.factory.annotation.Autowired;

import com.ajimports.action.ExpenseDetailsAction;
import com.ajimports.main.AjimportMain;
import com.ajimports.reports.OpenFile;

/**
 * @author ushan
 */
public class ReportView extends JFrame {
	
	@Autowired
	ExpenseDetailsAction expenseDetailsAction;

	public ReportView() {
		initComponents();
	}

	private void print_btnActionPerformed(ActionEvent e) {
		expenseDetailsAction.getExpenseDetailsWithInRange(fromDate, endDate);
		String reportName = "MONTLY_REPORT_WITH_DATE_RANGE";
		Calendar dateForAppend = Calendar.getInstance();
		String appendDate ="- "+"( "+dateForAppend.get(Calendar.DATE)+" - "+(dateForAppend.get(Calendar.MONTH)+1)+" - "+dateForAppend.get(Calendar.YEAR)+" )"; 
		try{
			HashMap map = new HashMap();
			map.put("FROM_DATE", fromDate);
			map.put("TO_DATE", endDate);
			JasperDesign jasperDesign = JRXmlLoader.load("src\\com\\ajimports\\reports\\Date_Range_Report.jrxml");  
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);  
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, AjimportMain.getCon());  
	        //JasperViewer.viewReport(jasperPrint);
	        JasperExportManager.exportReportToPdfFile(jasperPrint, "D://PROGRAMS//Reports//"+reportName+appendDate+".pdf");
//	        JasperExportManager.exportReportToHtmlFile(jasperPrint, "D://Murugesu//Documents//"+reportName+".html");
	        OpenFile.openPdf(reportName);
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private class MyDateListener implements DateListener {

		public void dateChanged(DateEvent e) {
			Calendar c = e.getSelectedDate();
			if (c != null) {
//				System.out.println("From Date : "+c.getTime());
				fromDate=calendar1.getDate();
			} else {
				System.out.println("No time selected.");
			}
		}

	}

	private class MyDateListener2 implements DateListener {

		public void dateChanged(DateEvent e) {
			Calendar c = e.getSelectedDate();
			if (c != null) {
//				System.out.println("End Date : "+c.getTime());
				endDate= calendar2.getDate();
			} else {
				System.out.println("No time selected.");
			}
		}

	}
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		label2 = new JLabel();
		print_btn = new JButton();
		label3 = new JLabel();

		//======== this ========
		setTitle("Report Generation");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("From :");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		contentPane.add(label1);
		label1.setBounds(new Rectangle(new Point(35, 45), label1.getPreferredSize()));

		//---- label2 ----
		label2.setText("To :");
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		contentPane.add(label2);
		label2.setBounds(new Rectangle(new Point(330, 45), label2.getPreferredSize()));

		//---- print_btn ----
		print_btn.setText("Print Report");
		print_btn.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		print_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print_btnActionPerformed(e);
			}
		});
		contentPane.add(print_btn);
		print_btn.setBounds(220, 105, 170, print_btn.getPreferredSize().height);

		//---- label3 ----
		label3.setText("Expense Details With In Range");
		label3.setFont(new Font("Algerian", Font.ITALIC, 22));
		contentPane.add(label3);
		label3.setBounds(new Rectangle(new Point(120, 10), label3.getPreferredSize()));

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		setSize(635, 195);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		//---- Calender1 ----
		calendar1 = new JCalendarCombo(JCalendarCombo.DISPLAY_DATE | JCalendarCombo.DISPLAY_TIME, true);
		calendar1.setEditable(true);
		calendar1.addDateListener(listener);
		contentPane.add(calendar1);
		calendar1.setBounds(95, 45, 200, calendar1.getPreferredSize().height);
		calendar2 = new JCalendarCombo(JCalendarCombo.DISPLAY_DATE | JCalendarCombo.DISPLAY_TIME, true);
		calendar2.setEditable(true);
		calendar2.addDateListener(listener2);
		contentPane.add(calendar2);
		calendar2.setBounds(370, 45, 200, calendar2.getPreferredSize().height);
	}
	
	

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JLabel label2;
	private JButton print_btn;
	private JLabel label3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private JCalendarCombo calendar1;
	private JCalendarCombo calendar2;
	private Date fromDate = new Date();
	private Date endDate = new Date();
	MyDateListener listener = new MyDateListener();
	MyDateListener2 listener2 = new MyDateListener2();
	/*public static void main(String[] args) {
		new ReportView().setVisible(true);
		
	}*/
}
