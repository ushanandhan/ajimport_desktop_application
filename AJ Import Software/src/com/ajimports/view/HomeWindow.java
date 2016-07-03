/*
 * Created by JFormDesigner on Sun Nov 06 12:44:53 IST 2011
 */

package com.ajimports.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;

import com.ajimports.utill.ClockFrame;


/**
 * @author ushan
 */
public class HomeWindow extends JFrame {
	
	@Autowired
	ProductView productView;
	
	@Autowired
	UserRegisterView userRegisterView;
	
	@Autowired
	ExpenseDetailsView expenseDetails;
	
	public HomeWindow() {
		initComponents();
	}

	private void menuItem1ActionPerformed(ActionEvent e) {
		productView.setVisible(true);
	}

	private void adminMenuItemActionPerformed(ActionEvent e) {
		userRegisterView.setVisible(true);
	}

	protected void processWindowEvent(WindowEvent we) {
		if(we.getID() == WindowEvent.WINDOW_CLOSING) {
			int option = JOptionPane.showConfirmDialog(this, "Do you want to Exit?");
			if(option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}else{
				
			}
		}
	}

	private void menuItem5ActionPerformed(ActionEvent e) {
		expenseDetails.setVisible(true);
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		buisnessMenu = new JMenu();
		menuItem1 = new JMenuItem();
		menuItem3 = new JMenuItem();
		homeMenu = new JMenu();
		menuItem5 = new JMenuItem();
		adminMenu = new JMenu();
		userRegMenuItem = new JMenuItem();

		//======== this ========
		setTitle("Aj Import's Application");
		setIconImage(new ImageIcon("D:\\Ushan\\Software Programs\\Desktop Applications\\AJ Import Software\\icons\\Adobe-Acrobat-Standard-icon.png").getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//======== menuBar1 ========
		{

			//======== buisnessMenu ========
			{
				buisnessMenu.setText("Buisness Application");
				buisnessMenu.setMnemonic('B');

				//---- menuItem1 ----
				menuItem1.setText("Product Registration");
				menuItem1.setIcon(new ImageIcon("D:\\Ushan\\Software Programs\\Desktop Applications\\AJ Import Software\\icons\\icon.png"));
				menuItem1.setMnemonic('P');
				menuItem1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						menuItem1ActionPerformed(e);
					}
				});
				buisnessMenu.add(menuItem1);

				//---- menuItem3 ----
				menuItem3.setText("Bill Book");
				menuItem3.setIcon(new ImageIcon("D:\\Ushan\\Software Programs\\Desktop Applications\\AJ Import Software\\icons\\Partnership-icon.png"));
				menuItem3.setMnemonic('B');
				buisnessMenu.add(menuItem3);
			}
			menuBar1.add(buisnessMenu);

			//======== homeMenu ========
			{
				homeMenu.setText("Home Application");
				homeMenu.setMnemonic('O');
				homeMenu.setIcon(null);

				//---- menuItem5 ----
				menuItem5.setText("Expense History");
				menuItem5.setIcon(new ImageIcon("D:\\Ushan\\Software Programs\\Desktop Applications\\AJ Import Software\\icons\\Font-Book-icon.png"));
				menuItem5.setMnemonic('E');
				menuItem5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						menuItem5ActionPerformed(e);
					}
				});
				homeMenu.add(menuItem5);
			}
			menuBar1.add(homeMenu);

			//======== adminMenu ========
			{
				adminMenu.setText("Administrator");
				adminMenu.setMnemonic('A');

				//---- userRegMenuItem ----
				userRegMenuItem.setText("User Registration");
				userRegMenuItem.setIcon(new ImageIcon("D:\\Ushan\\Software Programs\\Desktop Applications\\AJ Import Software\\icons\\iPad-happy-icon.png"));
				userRegMenuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adminMenuItemActionPerformed(e);
					}
				});
				adminMenu.add(userRegMenuItem);
			}
			menuBar1.add(adminMenu);
		}
		setJMenuBar(menuBar1);

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
		setSize(530, 375);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		
		clockDisplay = new ClockFrame();
		contentPane.add(clockDisplay);
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu buisnessMenu;
	private JMenuItem menuItem1;
	private JMenuItem menuItem3;
	private JMenu homeMenu;
	private JMenuItem menuItem5;
	private JMenu adminMenu;
	private JMenuItem userRegMenuItem;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	ClockFrame clockDisplay;
	
}
