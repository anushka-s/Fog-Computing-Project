package org.fog.test.perfeval;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UploaderServer extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UploaderServer server = new UploaderServer();
                    server.setVisible(false);
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    protected static void start() throws IOException {
        // TODO Auto-generated method stub
         System.out.println("Server running...");
         
            /* Listen on port 5555 */
     
            ServerSocket server = new ServerSocket(10001);
     
            /* Accept the sk */
     
            Socket socket = server.accept();
           
            System.out.println("Server accepted client");
            InputStream inputStream=socket.getInputStream();
            byte[] readbyte=new byte[(1024*10)*1024];           
            
                
            FileOutputStream fileOutputStream=new FileOutputStream("C:\\Users\\sanus\\eclipse-workspace\\fogComputingProject\\LogFolder\\upload.png");
            int writebyte;
            int count=0;
            while((writebyte=inputStream.read(readbyte))!=-1)
            {
                if(writebyte>0)
                    count+=writebyte;
                fileOutputStream.write(readbyte, 0, writebyte);
            }
            
            //System.out.println("THE WRITEBYTE VALUE IS "+writebyte+"THE READ BYTE VALUE IS"+count);
            inputStream.close();
            System.out.println("CONNECTION CLOSED");
            

        
    }

    /**
     * Create the frame.
     */
    public UploaderServer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        
    }


}
