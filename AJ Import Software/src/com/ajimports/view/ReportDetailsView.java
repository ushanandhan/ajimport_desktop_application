/*
 * Created by JFormDesigner on Tue Jun 26 21:04:38 IST 2012
 */

package com.ajimports.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.ajimports.main.AjimportMain;
import com.ajimports.reports.OpenFile;

/**
 * @author ushan
 */
public class ReportDetailsView extends JFrame {
	
	@Autowired
	ReportView reportView;
	
	@Autowired
	ExpendedByReportView expendedByReportView;
	
	public ReportDetailsView() {
		initComponents();
	}

	private void monthlyReport_btnActionPerformed(ActionEvent e) {
		reportView.setVisible(true);
	}

	private void dailyReport_btnActionPerformed(ActionEvent e) {
		expendedByReportView.setVisible(true);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dailyReport_btn = new JButton();
		monthlyReport_btn = new JButton();

		//======== this ========
		setTitle("Report Details");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- dailyReport_btn ----
		dailyReport_btn.setText("PURPOSE WISE REPORT");
		dailyReport_btn.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		dailyReport_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dailyReport_btnActionPerformed(e);
			}
		});
		contentPane.add(dailyReport_btn);
		dailyReport_btn.setBounds(75, 30, 235, dailyReport_btn.getPreferredSize().height);

		//---- monthlyReport_btn ----
		monthlyReport_btn.setText("MONTHLY REPORT");
		monthlyReport_btn.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		monthlyReport_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monthlyReport_btnActionPerformed(e);
			}
		});
		contentPane.add(monthlyReport_btn);
		monthlyReport_btn.setBounds(75, 75, 235, monthlyReport_btn.getPreferredSize().height);

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
		setSize(400, 150);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JButton dailyReport_btn;
	private JButton monthlyReport_btn;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
