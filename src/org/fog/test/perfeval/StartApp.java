package org.fog.test.perfeval;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;


@SuppressWarnings("serial")
public class StartApp extends JFrame {

	public static void main(String[] args) {
	//StartApp frameTabel = new StartApp();
	}
	
	JLabel welcome = new JLabel("Welcome! Please select an option.");
	JButton bup = new JButton("Upload");
	JButton bdown = new JButton("Download");
	JPanel panel = new JPanel();
	
	StartApp(){
	super("Welcome");
	setSize(300,200);
	setLocation(500,280);
	panel.setLayout (null);
	
	welcome.setBounds(50,20,250,60);
	bup.setBounds(30,90,100,20);
	bdown.setBounds(180,90,100,20);
	
	panel.add(welcome);
	panel.add(bup);
	panel.add(bdown);
	
	getContentPane().add(panel);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	
	bup.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			UploaderClient regFace =new UploaderClient();
			regFace.showProgressBarDemo();
			dispose();
		}
		});
	
	bdown.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			Downloader regFace =new Downloader();
			regFace.setVisible(true);
		    dispose();
		}
		});
	
	}

}
