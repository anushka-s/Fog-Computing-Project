package org.fog.test.perfeval;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class Downloader extends JFrame {

	private JTextField txtURL;
	private JProgressBar progressBar;
	private JButton btnDownload;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Downloader form = new Downloader();
				form.setVisible(true);
			}
		});
	}

	public Downloader() {

		// Create Form Frame
		super("Download");
		setSize(525, 270);
		setLocation(500, 280);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// Label Title
		final JLabel lblTitle = new JLabel("Download File", JLabel.CENTER);
		lblTitle.setBounds(73, 24, 370, 14);
		getContentPane().add(lblTitle);

		// TextField URL
		txtURL = new JTextField("");
		txtURL.setHorizontalAlignment(SwingConstants.CENTER);
		txtURL.setBounds(73, 66, 370, 20);
		getContentPane().add(txtURL);
		
		// ProgressBar
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setBounds(166, 99, 190, 20);
		getContentPane().add(progressBar);


		// Button Download
		btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDownload.setEnabled(false);
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						new BackgroundWorker().execute();
					}
				});
	
			}
		});
		btnDownload.setBounds(210, 152, 100, 23);
		getContentPane().add(btnDownload);
		
	}
	
	public class BackgroundWorker extends SwingWorker<Void, Void> {

		public BackgroundWorker() {
			addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					progressBar.setValue(getProgress());
				}

			});
		}

		@Override
		protected void done() {
			btnDownload.setEnabled(true);
		}

		@Override
		protected Void doInBackground() throws Exception {

			try {
				URL url = new URL(txtURL.getText());
				
			    URLConnection conexion = url.openConnection();
			    conexion.connect();	    
			    int lenghtOfFile = conexion.getContentLength();    
			    InputStream input = new BufferedInputStream(url.openStream());
				
				// File Name
				String source = txtURL.getText();
				String fileName = source.substring(source.lastIndexOf('/') + 1, source.length());

				// Copy file
				String saveFile = null;
				try {
					saveFile = new File(".").getCanonicalPath() + "\\LogFolder\\" + fileName;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				OutputStream output = new FileOutputStream(saveFile);
				
			    byte data[] = new byte[1024];
			    int count;
			    
			    	long total = 0;
			
			        while ((count = input.read(data)) != -1) {
			        	total += count;
			        	setProgress((int)((total*100)/lenghtOfFile));
			            output.write(data, 0, count);
			        }
			
			        output.flush();
			        output.close();
			        input.close(); 
			        QoEForm regFace =new QoEForm();
	    			dispose();
			        
			} catch (Exception ex) {
				System.err.println(ex);
			}
			
			return null;
			

		}
	}
	
}