package server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class MainWindow extends JFrame 
{
	protected JPanel mainpane;
	protected JLabel lblServer;
	protected JLabel lblNoteAboutClients;
	protected JButton btnCloseServer;
	protected JTextArea displayBoard;
	
	public MainWindow() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 328);
		
		mainpane = new JPanel();
		mainpane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainpane);
		
		mainpane.setLayout(null);
	
		lblServer = new JLabel("TicTacToe Server");
		lblServer.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblServer.setBounds(109, 11, 191, 29);
		mainpane.add(lblServer); 

		lblNoteAboutClients = new JLabel("NOTE: Two clients are needed for a game to run.");
		lblNoteAboutClients.setBounds(10, 51, 352, 14);
		mainpane.add(lblNoteAboutClients);

		btnCloseServer = new JButton("CLOSE THE SERVER");
		btnCloseServer.setBounds(151, 229, 161, 59);
		mainpane.add(btnCloseServer);
		
		displayBoard = new JTextArea((new Timestamp(System.currentTimeMillis())).toString()+" Server is now running...");
		displayBoard.setLineWrap(true);
		displayBoard.setEditable(false);
		displayBoard.setBounds(10, 76, 414, 142);
		JScrollPane scroll = new JScrollPane (displayBoard, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(10, 76, 414, 142);
		mainpane.add(scroll);
		
		this.setTitle("Server");
		this.setResizable(false);
		this.setVisible(true);
		
		// ANONYMOUS LISTENERS.

		btnCloseServer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	public void printToDisplay(String s) 
	{
		displayBoard.setText(displayBoard.getText()+"\n"+s);
	}
}
