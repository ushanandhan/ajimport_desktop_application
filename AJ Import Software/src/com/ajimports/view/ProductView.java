/*
 * Created by JFormDesigner on Fri Nov 04 13:24:51 IST 2011
 */

package com.ajimports.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.ajimports.action.ProductAction;
import com.ajimports.domain.Product;
import com.ajimports.main.AjimportMain;
import com.ajimports.reports.OpenFile;
import com.ajimports.reports.SendMail;

/**
 * @author USHANANDHAN
 */
public class ProductView extends JFrame {
	
	@Autowired
	ProductAction productAction;
	
	@Autowired
	SendMail sendMail;
	
	final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public ProductView() {
		initComponents();
	}

	private void addButtonActionPerformed(ActionEvent e) {
		try{
			String productName = p_nametxt.getText();
			int productQuantity = Integer.parseInt(p_quantitytxt.getText());
			int productPricePerEach = Integer.parseInt(p_pricetxt.getText());
			Date tdayDate = dateFormat.parse(label7.getText());
			Product product = new Product();
			product.setProductName(productName);
			product.setProductQuantity(productQuantity);
			product.setProductPrice(productPricePerEach);
			product.setDate(tdayDate);
			productAction.add(product);
			List<Product> allProduct = productAction.getAllProducts(product);
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			while(model.getRowCount()!=0){
				model.removeRow(0);
			}
			int y = 1;
			for (Product prod : allProduct) {
				Vector<Object> items = new Vector<Object>();
				items.add(y);
				items.add(prod.getProductName());
				items.add(prod.getProductQuantity());
				items.add(prod.getProductPrice());
				model.addRow(items);
				y++;
			}
			p_nametxt.setText("");
			p_pricetxt.setText("");
			p_quantitytxt.setText("");
			p_nametxt.requestFocus();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}

	private void clearbuttonActionPerformed(ActionEvent e) {
		p_nametxt.setText("");
		p_pricetxt.setText("");
		p_quantitytxt.setText("");
		p_nametxt.requestFocus();
	}

	private void printbuttonActionPerformed(ActionEvent e) {
		 try {
				String reportName = "A J IMPORT";
			 	Calendar dateForAppend = Calendar.getInstance();
			 	String appendDate ="- "+"( "+dateForAppend.get(Calendar.DATE)+" - "+(dateForAppend.get(Calendar.MONTH)+1)+" - "+dateForAppend.get(Calendar.YEAR)+" )"; 
			 	JasperDesign jasperDesign = JRXmlLoader.load("src\\com\\ajimports\\reports\\AJ_IMPORT'S_Product_Report.jrxml");  
	            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);  
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, AjimportMain.getCon());  
	            //JasperViewer.viewReport(jasperPrint);
	            JasperExportManager.exportReportToPdfFile(jasperPrint, "F://PROGRAMS//Reports//"+reportName+appendDate+".pdf");
	            //JasperExportManager.exportReportToHtmlFile(jasperPrint, "src\\com\\ajimports\\reports\\"+reportName+appendDate+".html");
	            OpenFile.openPdf(reportName);
//	            sendMail.sendFile("Today "+date, "Report Summary",reportName);
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	        } 
	}

	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		p_nametxt = new JTextField();
		p_quantitytxt = new JTextField();
		p_pricetxt = new JTextField();
		addButton = new JButton();
		clearbutton = new JButton();
		printbutton = new JButton();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		label5 = new JLabel();
		timeLabel = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();

		//======== this ========
		setTitle("AJ IMPORT Product Registration");
		setIconImage(new ImageIcon("D:\\Murugesu\\AJ Import Software\\icons\\icon.png").getImage());
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("Aj Import Product Addition");
		label1.setFont(new Font("Algerian", Font.PLAIN, 26));
		contentPane.add(label1);
		label1.setBounds(70, 20, 380, label1.getPreferredSize().height);

		//---- label2 ----
		label2.setText("Product ");
		label2.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label2);
		label2.setBounds(50, 125, 75, label2.getPreferredSize().height);

		//---- label3 ----
		label3.setText("Quantity ");
		label3.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label3);
		label3.setBounds(new Rectangle(new Point(50, 165), label3.getPreferredSize()));

		//---- label4 ----
		label4.setText("Price ");
		label4.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label4);
		label4.setBounds(new Rectangle(new Point(50, 205), label4.getPreferredSize()));

		//---- p_nametxt ----
		p_nametxt.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		p_nametxt.setToolTipText("Type Product Here");
		contentPane.add(p_nametxt);
		p_nametxt.setBounds(160, 125, 150, p_nametxt.getPreferredSize().height);

		//---- p_quantitytxt ----
		p_quantitytxt.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		p_quantitytxt.setToolTipText("Type Quantity Here");
		contentPane.add(p_quantitytxt);
		p_quantitytxt.setBounds(160, 165, 150, p_quantitytxt.getPreferredSize().height);

		//---- p_pricetxt ----
		p_pricetxt.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		p_pricetxt.setToolTipText("Type Price Here");
		contentPane.add(p_pricetxt);
		p_pricetxt.setBounds(160, 205, 150, p_pricetxt.getPreferredSize().height);

		//---- addButton ----
		addButton.setText("ADD");
		addButton.setMnemonic('A');
		addButton.setToolTipText("Click here to Add Product");
		addButton.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonActionPerformed(e);
			}
		});
		contentPane.add(addButton);
		addButton.setBounds(330, 125, 90, addButton.getPreferredSize().height);

		//---- clearbutton ----
		clearbutton.setText("CLEAR");
		clearbutton.setMnemonic('C');
		clearbutton.setToolTipText("Click here to Clear All Fields");
		clearbutton.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		clearbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearbuttonActionPerformed(e);
			}
		});
		contentPane.add(clearbutton);
		clearbutton.setBounds(330, 160, 90, clearbutton.getPreferredSize().height);

		//---- printbutton ----
		printbutton.setText("PRINT REPORT");
		printbutton.setMnemonic('P');
		printbutton.setToolTipText("Click Here to Print Report");
		printbutton.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		printbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printbuttonActionPerformed(e);
			}
		});
		contentPane.add(printbutton);
		printbutton.setBounds(new Rectangle(new Point(330, 195), printbutton.getPreferredSize()));

		//======== scrollPane1 ========
		{

			//---- table1 ----
			table1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"S_NO", "PRODUCT DETAILS", "QUANTITY", "PRICE"
				}
			));
			table1.setFont(new Font("Times New Roman", Font.ITALIC, 16));
			table1.setToolTipText("List Of Product Details");
			scrollPane1.setViewportView(table1);
		}
		contentPane.add(scrollPane1);
		scrollPane1.setBounds(30, 250, 450, 210);

		//---- label5 ----
		label5.setText("Time :");
		label5.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label5);
		label5.setBounds(new Rectangle(new Point(310, 75), label5.getPreferredSize()));

		//---- timeLabel ----
		timeLabel.setText("timeLabel");
		timeLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(timeLabel);
		timeLabel.setBounds(365, 75, 125, timeLabel.getPreferredSize().height);

		//---- label6 ----
		label6.setText("Date :");
		label6.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label6);
		label6.setBounds(50, 75, 50, label6.getPreferredSize().height);

		//---- label7 ----
		label7.setText("dateLable");
		label7.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		contentPane.add(label7);
		label7.setBounds(105, 75, 110, label7.getPreferredSize().height);

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
		setSize(530, 510);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		
		// For Clock Purpose
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
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JTextField p_nametxt;
	private JTextField p_quantitytxt;
	private JTextField p_pricetxt;
	private JButton addButton;
	private JButton clearbutton;
	private JButton printbutton;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JLabel label5;
	private JLabel timeLabel;
	private JLabel label6;
	private JLabel label7;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	Date date;
}


