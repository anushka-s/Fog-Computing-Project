package org.fog.test.perfeval;
import javax.swing.*;
import java.awt.event.*;

public class Login{
	public static void main(String[] args) {
        JFrame frame = new JFrame("JFrame Example");  
		JButton blogin = new JButton("Login");
		JPanel panel = new JPanel();
		JTextField txuser = new JTextField(15);
		JPasswordField pass = new JPasswordField(15);

		frame.setSize(300,200);
		frame.setLocation(500,280);
		panel.setLayout (null);

		txuser.setBounds(70,30,150,20);
		pass.setBounds(70,65,150,20);
		blogin.setBounds(110,100,80,20);

		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);

		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);



blogin.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ae) {
String puname = txuser.getText();
String ppaswd = String.valueOf(pass.getPassword());
if(puname.equals("test") && ppaswd.equals("12345"))
	{
	StartApp regFace =new StartApp();
	regFace.setVisible(true);
	frame.dispose();
	} 
else {
	JOptionPane.showMessageDialog(null,"Wrong Password / Username");
	txuser.setText("");
	pass.setText("");
	txuser.requestFocus(); 
	}

}
});
}
}
