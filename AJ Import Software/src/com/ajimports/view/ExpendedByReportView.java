/*
 * Created by JFormDesigner on Thu Jul 05 13:26:19 IST 2012
 */

package com.ajimports.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.ajimports.action.ExpenseDetailsAction;
import com.ajimports.domain.ExpenseDetails;
import com.ajimports.main.AjimportMain;
import com.ajimports.reports.OpenFile;

/**
 * @author ushan
 */
public class ExpendedByReportView extends JFrame {
	
	@Autowired
	ExpenseDetailsAction expenseDetailsAction;
	
	public ExpendedByReportView() {
		initComponents();
	}

	private void printReport_btnActionPerformed(ActionEvent e) {
		String param = param_txt.getText();
		String reportName = "EXPENDED_BY_REPORT";
		Calendar dateForAppend = Calendar.getInstance();
		String appendDate ="- "+"( "+dateForAppend.get(Calendar.DATE)+" - "+(dateForAppend.get(Calendar.MONTH)+1)+" - "+dateForAppend.get(Calendar.YEAR)+" )"; 
		try{
			HashMap map = new HashMap();
			map.put("PARAM", param);
			JasperDesign jasperDesign = JRXmlLoader.load("src\\com\\ajimports\\reports\\Expended_By_Report.jrxml");  
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);  
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, AjimportMain.getCon());  
	        //JasperViewer.viewReport(jasperPrint);
	        JasperExportManager.exportReportToPdfFile(jasperPrint, "D://Murugesu//Documents//"+reportName+appendDate+".pdf");
	        //JasperExportManager.exportReportToHtmlFile(jasperPrint, "D://Murugesu//Documents//"+reportName+".html");
	        OpenFile.openPdf(reportName);
		}catch (Exception ex) {
				System.out.println(ex.getMessage());
		}
	}

	private void view_btnActionPerformed(ActionEvent e) {
		int total=0 ;
		try {
			String param = param_txt.getText();
			List<ExpenseDetails> expenseDetailsList=expenseDetailsAction.getExpenseDetailsByParam(param);
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			while(model.getRowCount()!=0){
				model.removeRow(0);
			}
			int y = 1;
			for (ExpenseDetails expenseDetails : expenseDetailsList) {
				Vector<Object> items = new Vector<Object>();
				items.add(expenseDetails.getExpendedDate());
				items.add(expenseDetails.getExpendedFor());
				items.add(expenseDetails.getCausedPrice());
				model.addRow(items);
				y++;
				total=total+expenseDetails.getCausedPrice();
			}
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		System.out.println("Total Cost : "+total);
		total_lbl.setText(""+total+"");
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		param_txt = new JTextField();
		printReport_btn = new JButton();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		view_btn = new JButton();
		label2 = new JLabel();
		total_lbl = new JLabel();

		//======== this ========
		setTitle("Expended By Report");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Filter By :");
		label1.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(label1);
		label1.setBounds(new Rectangle(new Point(35, 25), label1.getPreferredSize()));

		//---- param_txt ----
		param_txt.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		contentPane.add(param_txt);
		param_txt.setBounds(120, 25, 180, param_txt.getPreferredSize().height);

		//---- printReport_btn ----
		printReport_btn.setText("Print Report");
		printReport_btn.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		printReport_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printReport_btnActionPerformed(e);
			}
		});
		contentPane.add(printReport_btn);
		printReport_btn.setBounds(new Rectangle(new Point(180, 65), printReport_btn.getPreferredSize()));

		//======== scrollPane1 ========
		{
			scrollPane1.setFont(new Font("Times New Roman", Font.ITALIC, 14));

			//---- table1 ----
			table1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Date", "Expended For", "Price"
				}
			));
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(25, 110, 350, 190);

		//---- view_btn ----
		view_btn.setText("View");
		view_btn.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		view_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view_btnActionPerformed(e);
			}
		});
		contentPane.add(view_btn);
		view_btn.setBounds(new Rectangle(new Point(75, 65), view_btn.getPreferredSize()));

		//---- label2 ----
		label2.setText("Total :");
		label2.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(label2);
		label2.setBounds(new Rectangle(new Point(220, 310), label2.getPreferredSize()));

		//---- total_lbl ----
		total_lbl.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		total_lbl.setText("---------------");
		contentPane.add(total_lbl);
		total_lbl.setBounds(280, 310, 85, total_lbl.getPreferredSize().height);

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
		setSize(400, 365);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JTextField param_txt;
	private JButton printReport_btn;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton view_btn;
	private JLabel label2;
	private JLabel total_lbl;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
