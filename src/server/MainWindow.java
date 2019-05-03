package server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class MainWindow extends JFrame 
{
	public JPanel mainpane;
	
	public MainWindow() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 328);
		
		mainpane = new JPanel();
		mainpane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainpane);
		
		mainpane.setLayout(null);
	
		JLabel lblServerIsRunning = new JLabel("The Server is now running...");
		lblServerIsRunning.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblServerIsRunning.setBounds(109, 32, 191, 29);
		mainpane.add(lblServerIsRunning); 

		JLabel lblNoteAboutClients = new JLabel("NOTE: Two clients are needed for the game to run.");
		lblNoteAboutClients.setBounds(10, 72, 352, 14);
		mainpane.add(lblNoteAboutClients);

		JButton btnCloseServer = new JButton("CLOSE THE SERVER");
		btnCloseServer.setBounds(139, 186, 161, 92);
		mainpane.add(btnCloseServer);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		JLabel lblTimeStamp = new JLabel(timestamp.toString());
		lblTimeStamp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeStamp.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTimeStamp.setBounds(109, 11, 191, 23);
		mainpane.add(lblTimeStamp);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(10, 108, 416, 52);
		mainpane.add(textArea);

		btnCloseServer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
}
