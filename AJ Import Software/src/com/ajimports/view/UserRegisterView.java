/*
 * Created by JFormDesigner on Thu Nov 17 10:37:04 IST 2011
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.ajimports.action.UserAction;
import com.ajimports.domain.User;
import com.ajimports.main.AjimportMain;
import com.ajimports.reports.OpenFile;
import com.ajimports.utill.ImageResizer;

/**
 * @author USHANANDHAN
 */
public class UserRegisterView extends JFrame {
	
	@Autowired
	UserAction userAction;
	
	public UserRegisterView() {
		initComponents();
		userImage = new File("Images/userImage.png");
		defaultImage = new File("Images/noimage.jpg");
		imageToPanel(userImage);
	}

	
	private void chooseButtonActionPerformed(ActionEvent e) {
		fileChooser = new JFileChooser();
		int status = fileChooser.showOpenDialog(null);
		if(status == fileChooser.APPROVE_OPTION){
			selectedFile = fileChooser.getSelectedFile();
			byte[] bytes = new byte[(int) selectedFile.length()];
				try {
					FileInputStream fis = new FileInputStream(selectedFile);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					for (int readNum; (readNum = fis.read(bytes)) != -1;) {
		                bos.write(bytes, 0, readNum); 
		                System.out.println("read " + readNum + " bytes,");
		            }
					imageByte = bos.toByteArray();
					imageToPanel(selectedFile);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this, e2.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			System.out.println(imageByte);
		}
	}

	private void addButtonActionPerformed(ActionEvent e) {
		try{
			String name = txtUserName.getText();
			char pass[] = txtpasswd.getPassword();
			String password = new String(pass);
			User user = new User();
			user.setUserName(name);
			user.setPassWord(password);
			user.setImage(imageByte);
			int option = JOptionPane.showConfirmDialog(this, "Do you want to Save?");
			if(option == JOptionPane.YES_OPTION) {
				userAction.addUser(user);
			}else{}
			JOptionPane.showMessageDialog(this, "User Successfully Added");
			txtUserName.setText("");
			txtpasswd.setText("");
			txtUserName.requestFocus();
			imageToPanel(userImage);
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	public void imageToPanel(File file){
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(file);
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			resizeImage = ImageResizer.resizeImageWithHint(originalImage,type);
			icon = new ImageIcon(resizeImage);
			imageLabel.setIcon(icon);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void userDetails_btnActionPerformed(ActionEvent e) {
		try {
			String reportName = "USER_DETAILS";
		 	Calendar dateForAppend = Calendar.getInstance();
		 	String appendDate ="- "+"( "+dateForAppend.get(Calendar.DATE)+" - "+(dateForAppend.get(Calendar.MONTH)+1)+" - "+dateForAppend.get(Calendar.YEAR)+" )"; 
		 	JasperDesign jasperDesign = JRXmlLoader.load("src\\com\\ajimports\\reports\\User_Information_Report.jrxml");  
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);  
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, AjimportMain.getCon());  
            //JasperViewer.viewReport(jasperPrint);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D://PROGRAMS//Reports//"+reportName+appendDate+".pdf");
            //JasperExportManager.exportReportToHtmlFile(jasperPrint, "src\\com\\ajimports\\reports\\"+reportName+appendDate+".html");
            OpenFile.openPdf(reportName);
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } 
	}

	private void search_btnActionPerformed(ActionEvent e) {
		try {
			Integer userId = Integer.parseInt(userId_txt.getText());
			User returnUser = userAction.getUserByUserID(userId);
			if (!returnUser.equals(null)){
				byte[] imageByte = returnUser.getImage();
				int size = imageByte.length;
				ByteArrayInputStream stream = new ByteArrayInputStream(imageByte);
				stream.read(imageByte, 0, size);
				icon = new ImageIcon(imageByte);
				imageLabel.setIcon(icon);
				txtUserName.setText(returnUser.getUserName());
				txtpasswd.setText(returnUser.getPassWord());
			}
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
			txtUserName.setText("");
			txtpasswd.setText("");
			txtUserName.requestFocus();
			imageToPanel(defaultImage);
			JOptionPane.showMessageDialog(this, "No User Available","Information",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void update_btnActionPerformed(ActionEvent e) {
		try {
			char pass[] = txtpasswd.getPassword();
			String password = new String(pass);
			User user = new User();
			user.setUserId(Integer.parseInt(userId_txt.getText()));
			user.setUserName(txtUserName.getText());
			user.setPassWord(password);
			user.setImage(imageByte);
			int option = JOptionPane.showConfirmDialog(this, "Do you want to Update?");
			if(option == JOptionPane.YES_OPTION) {
				userAction.updateUser(user);
			}else{}
			JOptionPane.showMessageDialog(this, "User Successfully Updated");
			txtUserName.setText("");
			txtpasswd.setText("");
			txtUserName.requestFocus();
			imageToPanel(userImage);
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label1 = new JLabel();
		txtUserName = new JTextField();
		imagePanel = new JPanel();
		imageLabel = new JLabel();
		chooseButton = new JButton();
		label2 = new JLabel();
		txtpasswd = new JPasswordField();
		label3 = new JLabel();
		addButton = new JButton();
		delete_btn = new JButton();
		update_btn = new JButton();
		userDetails_btn = new JButton();
		search_btn = new JButton();
		userId_txt = new JTextField();
		label4 = new JLabel();

		//======== this ========
		setTitle("User Registrations");
		setResizable(false);
		setIconImage(new ImageIcon("D:\\Ushan\\Software Programs\\Desktop Applications\\AJ Import Software\\icons\\iPad-happy-icon.png").getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("User Name :");
		label1.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(label1);
		label1.setBounds(15, 90, 100, 25);

		//---- txtUserName ----
		txtUserName.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(txtUserName);
		txtUserName.setBounds(120, 95, 160, 25);

		//======== imagePanel ========
		{
			imagePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			imagePanel.setLayout(null);
			imagePanel.add(imageLabel);
			imageLabel.setBounds(0, 0, 135, 125);
		}
		contentPane.add(imagePanel);
		imagePanel.setBounds(335, 72, 135, 125);

		//---- chooseButton ----
		chooseButton.setText("Choose Image");
		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseButtonActionPerformed(e);
			}
		});
		contentPane.add(chooseButton);
		chooseButton.setBounds(330, 223, 153, 23);

		//---- label2 ----
		label2.setText("Password :");
		label2.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(label2);
		label2.setBounds(new Rectangle(new Point(20, 130), label2.getPreferredSize()));

		//---- txtpasswd ----
		txtpasswd.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(txtpasswd);
		txtpasswd.setBounds(120, 128, 160, 27);

		//---- label3 ----
		label3.setText("User Information");
		label3.setFont(new Font("Algerian", Font.PLAIN, 22));
		contentPane.add(label3);
		label3.setBounds(new Rectangle(new Point(140, 15), label3.getPreferredSize()));

		//---- addButton ----
		addButton.setText("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonActionPerformed(e);
			}
		});
		contentPane.add(addButton);
		addButton.setBounds(40, 175, 65, addButton.getPreferredSize().height);

		//---- delete_btn ----
		delete_btn.setText("Delete");
		contentPane.add(delete_btn);
		delete_btn.setBounds(new Rectangle(new Point(130, 175), delete_btn.getPreferredSize()));

		//---- update_btn ----
		update_btn.setText("Update");
		update_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update_btnActionPerformed(e);
			}
		});
		contentPane.add(update_btn);
		update_btn.setBounds(new Rectangle(new Point(216, 175), update_btn.getPreferredSize()));

		//---- userDetails_btn ----
		userDetails_btn.setText("Print");
		userDetails_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userDetails_btnActionPerformed(e);
			}
		});
		contentPane.add(userDetails_btn);
		userDetails_btn.setBounds(130, 215, 65, userDetails_btn.getPreferredSize().height);

		//---- search_btn ----
		search_btn.setText("Search");
		search_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search_btnActionPerformed(e);
			}
		});
		contentPane.add(search_btn);
		search_btn.setBounds(new Rectangle(new Point(40, 215), search_btn.getPreferredSize()));

		//---- userId_txt ----
		userId_txt.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(userId_txt);
		userId_txt.setBounds(120, 60, 45, 26);

		//---- label4 ----
		label4.setText("User Id :");
		label4.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		contentPane.add(label4);
		label4.setBounds(new Rectangle(new Point(44, 60), label4.getPreferredSize()));

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
		setSize(525, 300);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label1;
	private JTextField txtUserName;
	private JPanel imagePanel;
	private JLabel imageLabel;
	private JButton chooseButton;
	private JLabel label2;
	private JPasswordField txtpasswd;
	private JLabel label3;
	private JButton addButton;
	private JButton delete_btn;
	private JButton update_btn;
	private JButton userDetails_btn;
	private JButton search_btn;
	private JTextField userId_txt;
	private JLabel label4;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private JFileChooser fileChooser;
	private File selectedFile,userImage,defaultImage;
	private BufferedImage resizeImage;
	private ImageIcon icon;
	private byte[] imageByte;
	
	
}
