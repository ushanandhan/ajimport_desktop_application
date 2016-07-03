package com.ajimports.utill;

import java.awt.Color;

import javax.swing.JApplet;
import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;



public class ClockFrame extends JInternalFrame{
	static final long serialVersionUID = 101;
	public ClockFrame() {
		JApplet applet = new ClockApplet();
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setEastPane(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setSouthPane(null);
		setBorder(new LineBorder(new Color(0,78,152)));
		applet.init();
		applet.start();
		setContentPane(applet);
		setBounds(375,10,130,130);
		setVisible(true);
	}

}
