package org.fog.test.perfeval;

import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;


@SuppressWarnings("serial")
public class StartApp extends JFrame {

public static void main(String[] args) {
//StartApp frameTabel = new StartApp();
}

JLabel welcome = new JLabel("Welcome to a New Frame");
JPanel panel = new JPanel();

StartApp(){
super("Welcome");
setSize(300,200);
setLocation(500,280);
panel.setLayout (null);

welcome.setBounds(70,50,150,60);

panel.add(welcome);

getContentPane().add(panel);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
}

}
