package org.fog.test.perfeval;
import javax.swing.*;
import java.awt.event.*;

public class Login{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        JFrame frame = new JFrame("Login Form");  
		JButton blogin = new JButton("Login");
		JPanel panel = new JPanel();
		JTextField txuser = new JTextField(15);
		JPasswordField pass = new JPasswordField(15);
		JLabel label1 = new JLabel("IP Address");
		JLabel label2 = new JLabel("MAC Address");

		frame.setSize(350,200);
		frame.setLocation(500,280);
		panel.setLayout (null);

		txuser.setBounds(90,30,150,20);
        label1.setBounds(10, 30, 100, 20);
		pass.setBounds(90,65,150,20);
		label2.setBounds(10,65, 100, 20);
		blogin.setBounds(110,100,80,20);

		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);
		panel.add(label1);
		panel.add(label2);

		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);



		blogin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
		String puname = txuser.getText();
		String ppaswd = String.valueOf(pass.getPassword());
		if(puname.equals("192.168.1.10") && ppaswd.equals("fe80::3c94:b419:fc76:8fa0%20"))
			{
			StartApp regFace =new StartApp();
			regFace.setVisible(true);
			frame.dispose();
			} 
		else {
			JOptionPane.showMessageDialog(null,"Wrong IP/MAC Address");
			txuser.setText("");
			pass.setText("");
			txuser.requestFocus(); 
			}
		
		}
		});
	}
}
