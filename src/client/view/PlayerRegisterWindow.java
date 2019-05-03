package client.view;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Labels;
import client.controller.PlayerRegisterController_Client;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class PlayerRegisterWindow extends JFrame implements Labels 
{
	
	protected JPanel contentPane;
	protected JLabel lblUsername, lblPlayerMark, lblPlayerType;
	protected JTextField playerInputField;
	protected JButton btnDoneRegisteringPlayer;
	protected JComboBox<String> cbxPlayerType, cbxPlayerMark;
	
	/**
	 * Create the frame.
	 */
	public PlayerRegisterWindow() 
	{	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // custom close operation.
		setBounds(100, 100, 672, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		playerInputField = new JTextField();
		playerInputField.setBounds(385, 52, 206, 20);
		contentPane.add(playerInputField);
		playerInputField.setColumns(10);
		
		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsername.setBounds(440, 11, 79, 20);
		contentPane.add(lblUsername);
		
		btnDoneRegisteringPlayer = new JButton("Done");
		btnDoneRegisteringPlayer.setBounds(258, 95, 172, 23);
		contentPane.add(btnDoneRegisteringPlayer);
		
		cbxPlayerType = new JComboBox<String>();
		cbxPlayerType.setBounds(196, 52, 158, 20);
		contentPane.add(cbxPlayerType);
		cbxPlayerType.addItem(HUMAN_PLAYER_LABEL);
		cbxPlayerType.addItem(RANDOM_PLAYER_LABEL);
		cbxPlayerType.addItem(BLOCKING_PLAYER_LABEL);
		cbxPlayerType.addItem(SMART_PLAYER_LABEL);
		
		cbxPlayerMark = new JComboBox<String>();
		cbxPlayerMark.setBounds(28, 52, 158, 20);
		contentPane.add(cbxPlayerMark);
		cbxPlayerMark.addItem("Player "+String.valueOf(LETTER_O));
		cbxPlayerMark.addItem("Player "+String.valueOf(LETTER_X));
		
		lblPlayerMark = new JLabel("Player Mark Type");
		lblPlayerMark.setFont(new Font("Arial", Font.BOLD, 14));
		lblPlayerMark.setBounds(46, 21, 158, 20);
		contentPane.add(lblPlayerMark);
		
		lblPlayerType = new JLabel("Player Type");
		lblPlayerType.setFont(new Font("Arial", Font.BOLD, 14));
		lblPlayerType.setBounds(229, 21, 100, 20);
		contentPane.add(lblPlayerType);
		
		this.setVisible(true);
	}
	
	public void linkAllSourcesToController(PlayerRegisterController_Client prController)
	{
		btnDoneRegisteringPlayer.addActionListener(prController);
		this.addWindowListener(prController);
	}

	public JPanel getContentPane() 
	{
		return contentPane;
	}

	public JLabel getLblUsername() 
	{
		return lblUsername;
	}

	public JLabel getLblPlayerMark() 
	{
		return lblPlayerMark;
	}

	public JLabel getLblPlayerType() 
	{
		return lblPlayerType;
	}

	public JTextField getPlayerInputField() 
	{
		return playerInputField;
	}

	public JButton getBtnDoneRegisteringPlayer() 
	{
		return btnDoneRegisteringPlayer;
	}

	public JComboBox<String> getCbxPlayerType() 
	{
		return cbxPlayerType;
	}

	public JComboBox<String> getCbxPlayerMark() 
	{
		return cbxPlayerMark;
	}

	public void setContentPane(JPanel contentPane) 
	{
		this.contentPane = contentPane;
	}

	public void setLblUsername(JLabel lblUsername) 
	{
		this.lblUsername = lblUsername;
	}

	public void setLblPlayerMark(JLabel lblPlayerMark) 
	{
		this.lblPlayerMark = lblPlayerMark;
	}

	public void setLblPlayerType(JLabel lblPlayerType) 
	{
		this.lblPlayerType = lblPlayerType;
	}

	public void setPlayerInputField(JTextField playerInputField)
	{
		this.playerInputField = playerInputField;
	}

	public void setBtnDoneRegisteringPlayer(JButton btnDoneRegisteringPlayer)
	{
		this.btnDoneRegisteringPlayer = btnDoneRegisteringPlayer;
	}

	public void setCbxPlayerType(JComboBox<String> cbxPlayerType) 
	{
		this.cbxPlayerType = cbxPlayerType;
	}

	public void setCbxPlayerMark(JComboBox<String> cbxPlayerMark) 
	{
		this.cbxPlayerMark = cbxPlayerMark;
	}
}
