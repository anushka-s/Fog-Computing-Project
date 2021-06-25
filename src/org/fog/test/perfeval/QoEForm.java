package org.fog.test.perfeval;
import javax.swing.*;
import java.awt.event.*;

public class QoEForm{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        JFrame frame = new JFrame("QoE Feedback Form");  
		JButton blogin = new JButton("Submit");
		JPanel panel = new JPanel();
		JTextField name = new JTextField(15);
		JTextField email = new JTextField(15);
		JTextField phone = new JTextField(15);
		JTextField file = new JTextField(20);
		JTextField speed = new JTextField(2);
		JTextArea comments = new JTextArea();
		JLabel label1 = new JLabel("User Name");
		JLabel label2 = new JLabel("Email");
		JLabel label3 = new JLabel("Phone Number");
		JLabel label4 = new JLabel("Task Type");
		JLabel label5 = new JLabel("Task Information");
		JLabel label6 = new JLabel("Name Of File");
		JLabel label7 = new JLabel("Network Information");
		JLabel label8 = new JLabel("Are You Facing Internet Issues?");
		JLabel label9 = new JLabel("Network Connection Speed?");
		JLabel label10 = new JLabel("Mbps");
		JLabel label11 = new JLabel("What Type Of Network Are You Using?");
		JLabel label12 = new JLabel("Comments");
		JRadioButton r1=new JRadioButton("Upload");    
		JRadioButton r2=new JRadioButton("Download");
		JRadioButton r3=new JRadioButton("Yes");    
		JRadioButton r4=new JRadioButton("No");
		JRadioButton r5 = new JRadioButton("3G/4G");
		JRadioButton r6 = new JRadioButton("Cable");
		ButtonGroup bg=new ButtonGroup();    
		ButtonGroup bg1=new ButtonGroup();
		ButtonGroup bg2 = new ButtonGroup();

		frame.setSize(650,320);
		frame.setLocation(500,280);
		panel.setLayout (null);

		name.setBounds(80,30,150,20);
		phone.setBounds(100,95,150,20);
        label1.setBounds(10, 30, 100, 20);
		email.setBounds(80,65,150,20);
		label2.setBounds(10,65, 100, 20);
		label3.setBounds(10,95,100,20);
		label4.setBounds(10,120,100,20);
		r1.setBounds(70,120,70,20);    
		r2.setBounds(140,120,100,20); 
		label5.setBounds(325, 0, 200, 40);
		label6.setBounds(280, 30, 100, 20);
		file.setBounds(355, 30, 150, 20);
		label7.setBounds(325, 50, 200, 40);
		label8.setBounds(280,80,200,20);
		label9.setBounds(280, 100, 200, 20);
		speed.setBounds(450, 100, 30, 20);
		label10.setBounds(490, 100, 40, 20);
		r3.setBounds(470, 80, 50, 20);
		r4.setBounds(520, 80, 50, 20);
		label11.setBounds(280, 120, 250, 20);
		label12.setBounds(200, 160, 100, 20);
		comments.setBounds(180,180,150, 50);
		blogin.setBounds(200,250,80,20);

		r5.setBounds(500, 120, 60, 20);
		r6.setBounds(560, 120, 70, 20);

		bg.add(r1);bg.add(r2);    
		bg1.add(r3);bg.add(r4);
		bg2.add(r5);bg2.add(r6);
		
		panel.add(blogin);
		panel.add(name);
		panel.add(email);
		panel.add(label1);
		panel.add(label3);
		panel.add(label2);
		panel.add(phone);
		panel.add(label7);
		panel.add(r1);
		panel.add(r2);
		panel.add(label4);
		panel.add(label5);
		panel.add(label6);
		panel.add(file);
		panel.add(r4);
		panel.add(r3);
		panel.add(label8);
		panel.add(speed);
		panel.add(label10);
		panel.add(label9);
		panel.add(label11);
		panel.add(r5);
		panel.add(r6);
		panel.add(label12);
		panel.add(comments);
		
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		blogin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			
		}
		});
	}
}
