/*
 * Created by JFormDesigner on Mon Nov 21 13:07:56 IST 2011
 */

package com.ajimports.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.ajimports.action.ExpenseDetailsAction;
import com.ajimports.domain.ExpenseDetails;

/**
 * @author ushan
 */
public class ExpenseDetailsView extends JFrame {
	
	@Autowired
	ExpenseDetailsAction expenseDetailsAction;
	
	@Autowired
	ReportDetailsView reportDetailsView;
	
	final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public ExpenseDetailsView() {
		initComponents();
	}

	private void addActionPerformed(ActionEvent e) {
		try {
			String expendedFor = expendedFor_txt.getText();
			int priceCaused = Integer.parseInt(priceCaused_txt.getText());
			Date expendedOn = dateFormat.parse(label7.getText());
			ExpenseDetails expenseDetails = new ExpenseDetails();
			expenseDetails.setExpendedFor(expendedFor);
			expenseDetails.setCausedPrice(priceCaused);
			expenseDetails.setExpendedDate(expendedOn);
			expenseDetailsAction.add(expenseDetails);
			List<ExpenseDetails> allExpense = expenseDetailsAction.getAllExpense(expenseDetails);
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			while(model.getRowCount()!=0){
				model.removeRow(0);
			}
			int y = 1;
			for (ExpenseDetails expenseDetails1 : allExpense) {
				Vector<Object> items = new Vector<Object>();
				items.add(y);
				items.add(expenseDetails1.getExpendedFor());
				items.add(expenseDetails1.getCausedPrice());
				model.addRow(items);
				y++;
			}
			List<ExpenseDetails> getTotal = expenseDetailsAction.getTotalExpense();
			label4.setText(getTotal.toString());
			expendedFor_txt.setText("");
			priceCaused_txt.setText("");
			expendedFor_txt.requestFocus();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, e2.getMessage());
		}
	}

	private void button3ActionPerformed(ActionEvent e) {
		try {
			List<ExpenseDetails> getTotal = expenseDetailsAction.getTotalExpense();
			String total = getTotal.toString().replace("[", "").replace("]", "");
			label4.setText(total);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, e2.getMessage());
		}
	}

	private void printActionPerformed(ActionEvent e) {
		reportDetailsView.setVisible(true);
//		this.setVisible(false);
	}

	

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label6 = new JLabel();
		label7 = new JLabel();
		label5 = new JLabel();
		timeLabel = new JLabel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		expendedFor_txt = new JTextField();
		priceCaused_txt = new JTextField();
		button1 = new JButton();
		button2 = new JButton();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		label4 = new JLabel();
		button3 = new JButton();
		label8 = new JLabel();

		//======== this ========
		setTitle("Expense Details");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label6 ----
		label6.setText("Date :");
		label6.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label6);
		label6.setBounds(25, 60, 50, 22);

		//---- label7 ----
		label7.setText("dateLable");
		label7.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label7);
		label7.setBounds(80, 60, 110, 22);

		//---- label5 ----
		label5.setText("Time :");
		label5.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label5);
		label5.setBounds(415, 60, 48, 22);

		//---- timeLabel ----
		timeLabel.setText("timeLabel");
		timeLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(timeLabel);
		timeLabel.setBounds(470, 60, 125, 22);

		//---- label1 ----
		label1.setText("Expense Details");
		label1.setFont(new Font("Algerian", Font.PLAIN, 22));
		contentPane.add(label1);
		label1.setBounds(new Rectangle(new Point(180, 20), label1.getPreferredSize()));

		//---- label2 ----
		label2.setText("Expended For :");
		label2.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label2);
		label2.setBounds(new Rectangle(new Point(130, 105), label2.getPreferredSize()));

		//---- label3 ----
		label3.setText("Price Caused :");
		label3.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label3);
		label3.setBounds(130, 135, 110, 20);
		contentPane.add(expendedFor_txt);
		expendedFor_txt.setBounds(260, 105, 160, expendedFor_txt.getPreferredSize().height);
		contentPane.add(priceCaused_txt);
		priceCaused_txt.setBounds(260, 140, 160, priceCaused_txt.getPreferredSize().height);

		//---- button1 ----
		button1.setText("Add");
		button1.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addActionPerformed(e);
			}
		});
		contentPane.add(button1);
		button1.setBounds(new Rectangle(new Point(170, 180), button1.getPreferredSize()));

		//---- button2 ----
		button2.setText("Print Report");
		button2.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printActionPerformed(e);
			}
		});
		contentPane.add(button2);
		button2.setBounds(new Rectangle(new Point(270, 180), button2.getPreferredSize()));

		//======== scrollPane1 ========
		{

			//---- table1 ----
			table1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"S.No", "Expended For", "Cost"
				}
			));
			table1.setFont(new Font("Times New Roman", Font.ITALIC, 18));
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(55, 220, 540, 100);

		//---- label4 ----
		label4.setText("-------------------");
		label4.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label4);
		label4.setBounds(465, 330, 115, label4.getPreferredSize().height);

		//---- button3 ----
		button3.setText("Check Total");
		button3.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button3ActionPerformed(e);
			}
		});
		contentPane.add(button3);
		button3.setBounds(new Rectangle(new Point(425, 180), button3.getPreferredSize()));

		//---- label8 ----
		label8.setText("Total Expense :");
		label8.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label8);
		label8.setBounds(new Rectangle(new Point(345, 330), label8.getPreferredSize()));

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
		setSize(630, 385);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		
		final DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");  
        ActionListener timerListener = new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
            	Date dateForTimer = new Date();
            	String time = timeFormat.format(dateForTimer);  
                timeLabel.setText(time);  
            }  
        };  
        Timer timer = new Timer(1000, timerListener);  
        timer.setInitialDelay(0);  
        timer.start();
        
        // For Date purpose
        date = new Date();
		String dateLabel = dateFormat.format(date);
        label7.setText(dateLabel);
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label6;
	private JLabel label7;
	private JLabel label5;
	private JLabel timeLabel;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JTextField expendedFor_txt;
	private JTextField priceCaused_txt;
	private JButton button1;
	private JButton button2;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JLabel label4;
	private JButton button3;
	private JLabel label8;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private Date date;
}
