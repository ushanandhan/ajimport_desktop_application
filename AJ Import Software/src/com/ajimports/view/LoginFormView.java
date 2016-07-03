/*
 * Created by JFormDesigner on Tue Nov 15 11:46:09 IST 2011
 */

package com.ajimports.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;

import com.ajimports.action.UserAction;
import com.ajimports.domain.User;
import com.ajimports.utill.ImageResizer;

/**
 * @author ushan
 */
public class LoginFormView extends JDialog{
	
	int count=0;
	
	@Autowired
	HomeWindow homeWindowView;
	
	@Autowired
	UserAction userAction;
	
	public LoginFormView(){
		initComponents();
		userImage = new File("Images/userImage.png");
		defaultImage = new File("Images/noimage.jpg");
		imageToPanel(userImage);
	}
	
	/*public LoginFormView(Frame owner) {
		super(owner);
		initComponents();
		System.out.println(userAction);
	}*/


	
	private void okButtonActionPerformed(ActionEvent e) {
		try{
			String userName = txtUName.getText();
			char pass[] = passValue.getPassword();
			String password = new String(pass);
			User user = new User();
			user.setUserName(userName);
			user.setPassWord(password);
			boolean check = false;
			check = userAction.checkLogin(user);
			if (check){
				JOptionPane.showMessageDialog(this, "Hi "+userName+" Welcome to Ajimports Software");
				setVisible(false);
				homeWindowView.setVisible(true);
			}else{
				count++;
				if(count == 3) {
					JOptionPane.showMessageDialog(this,"Only Three Attempt, Press Ok to Exit","Login Error",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				JOptionPane.showMessageDialog(this, "InValid UserName or Password");
				txtUName.setText("");
				passValue.setText("");
				txtUName.requestFocus();
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		System.exit(0);
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

	private void passValueFocusLost(FocusEvent e) {
		try{
			String userName = txtUName.getText();
			char pass[] = passValue.getPassword();
			String password = new String(pass);
			User user = new User();
			user.setUserName(userName);
			user.setPassWord(password);
			//boolean check = false;
			User returnUser = userAction.getUser(user);
			if (!returnUser.equals(null)){
				byte[] imageByte = returnUser.getImage();
				int size = imageByte.length;
				ByteArrayInputStream stream = new ByteArrayInputStream(imageByte);
				stream.read(imageByte, 0, size);
				icon = new ImageIcon(imageByte);
				imageLabel.setIcon(icon);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			txtUName.setText("");
			passValue.setText("");
			txtUName.requestFocus();
			imageToPanel(defaultImage);
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		txtUName = new JTextField();
		passValue = new JPasswordField();
		imagePanel = new JPanel();
		imageLabel = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Login Authendication");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(null);

				//---- label1 ----
				label1.setText("User Name :");
				label1.setFont(new Font("Times New Roman", Font.ITALIC, 14));
				contentPanel.add(label1);
				label1.setBounds(new Rectangle(new Point(15, 10), label1.getPreferredSize()));

				//---- label2 ----
				label2.setText("Password :");
				label2.setFont(new Font("Times New Roman", Font.ITALIC, 14));
				contentPanel.add(label2);
				label2.setBounds(new Rectangle(new Point(20, 40), label2.getPreferredSize()));

				//---- txtUName ----
				txtUName.setFont(new Font("Times New Roman", Font.ITALIC, 14));
				contentPanel.add(txtUName);
				txtUName.setBounds(100, 10, 160, txtUName.getPreferredSize().height);

				//---- passValue ----
				passValue.setFont(new Font("Times New Roman", Font.ITALIC, 14));
				passValue.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						passValueFocusLost(e);
					}
				});
				contentPanel.add(passValue);
				passValue.setBounds(100, 40, 160, passValue.getPreferredSize().height);

				//======== imagePanel ========
				{
					imagePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
					imagePanel.setLayout(null);
					imagePanel.add(imageLabel);
					imageLabel.setBounds(0, 0, 135, 125);
				}
				contentPanel.add(imagePanel);
				imagePanel.setBounds(285, 10, 135, 125);

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < contentPanel.getComponentCount(); i++) {
						Rectangle bounds = contentPanel.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = contentPanel.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					contentPanel.setMinimumSize(preferredSize);
					contentPanel.setPreferredSize(preferredSize);
				}
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JLabel label2;
	private JTextField txtUName;
	private JPasswordField passValue;
	private JPanel imagePanel;
	private JLabel imageLabel;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private File userImage,defaultImage;
	private BufferedImage resizeImage;
	private ImageIcon icon;
	
	
}
