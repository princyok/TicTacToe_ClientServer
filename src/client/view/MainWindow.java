package client.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.controller.MainController_Client;

import javax.swing.JButton;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {
	
	protected String markOfMainWindow;

	protected JPanel mainpane;
	protected JTextField usernameDisplay, currentPlayerDisplay;
	protected JTextArea mainDisplay;
		
	protected GameplayButton btnZeroZero, btnZeroOne, btnZeroTwo,
	btnOneZero, btnOneOne, btnOneTwo,
	btnTwoZero, btnTwoOne, btnTwoTwo;
	
	protected JButton btnRegisterPlayer,btnRestartGame, btnStartGame;
	
	protected JLabel lblCurrentPlayer,lblUsername, lblPlayerMarkType;

	/**
	 * Create the frame.
	 */
	public MainWindow() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 379);
		
		mainpane = new JPanel();
		mainpane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainpane);
		mainpane.setLayout(null);
		
		btnZeroZero = new GameplayButton("",0,0);
		btnZeroZero.setBounds(17, 62, 46, 34);
		mainpane.add(btnZeroZero);
		
		btnZeroOne = new GameplayButton("",0,1);
		btnZeroOne.setBounds(83, 62, 46, 34);
		mainpane.add(btnZeroOne);
		
		btnZeroTwo = new GameplayButton("",0,2);
		btnZeroTwo.setBounds(149, 62, 46, 34);
		mainpane.add(btnZeroTwo);
		
		btnOneZero = new GameplayButton("",1,0);
		btnOneZero.setBounds(17, 107, 46, 34);
		mainpane.add(btnOneZero);
		
		btnOneOne = new GameplayButton("",1,1);
		btnOneOne.setBounds(83, 107, 46, 34);
		mainpane.add(btnOneOne);
		
		btnOneTwo = new GameplayButton("",1,2);
		btnOneTwo.setBounds(149, 107, 46, 34);
		mainpane.add(btnOneTwo);
		
		btnTwoZero = new GameplayButton("",2,0);
		btnTwoZero.setBounds(17, 152, 46, 34);
		mainpane.add(btnTwoZero);
		
		btnTwoOne = new GameplayButton("",2,1);
		btnTwoOne.setBounds(83, 152, 46, 34);
		mainpane.add(btnTwoOne);
		
		btnTwoTwo = new GameplayButton("",2,2);
		btnTwoTwo.setBounds(149, 152, 46, 34);
		mainpane.add(btnTwoTwo);
		
		lblCurrentPlayer = new JLabel("Mark Type:");
		lblCurrentPlayer.setBounds(17, 261, 90, 14);
		mainpane.add(lblCurrentPlayer);
		
		currentPlayerDisplay = new JTextField();
		currentPlayerDisplay.setEditable(false);
		currentPlayerDisplay.setBounds(126, 258, 86, 20);
		mainpane.add(currentPlayerDisplay);
		currentPlayerDisplay.setColumns(10);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(17, 232, 76, 14);
		mainpane.add(lblUsername);
		
		usernameDisplay = new JTextField();
		usernameDisplay.setEditable(false);
		usernameDisplay.setBounds(126, 229, 178, 20);
		mainpane.add(usernameDisplay);
		usernameDisplay.setColumns(10);
		
		btnRegisterPlayer = new JButton("Register players");
		btnRegisterPlayer.setBounds(236, 306, 178, 23);
		mainpane.add(btnRegisterPlayer);
		
		btnRestartGame = new JButton("Restart the game");
		btnRestartGame.setBounds(34, 306, 136, 23);
		mainpane.add(btnRestartGame);
		
		btnStartGame = new JButton("START GAME");
		btnStartGame.setBounds(314, 197, 117, 92);
		mainpane.add(btnStartGame);
		
		mainDisplay = new JTextArea();
		mainDisplay.setEditable(false);
		mainDisplay.setLineWrap(true);
		mainDisplay.setBounds(253, 62, 178, 124);
		mainpane.add(mainDisplay);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 197, 287, 21);
		mainpane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 289, 287, 17);
		mainpane.add(separator_1);
		
		JLabel lblCurrentPlayer_1 = new JLabel("Current Player");
		lblCurrentPlayer_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCurrentPlayer_1.setBounds(50, 204, 189, 20);
		mainpane.add(lblCurrentPlayer_1);
		
		JLabel lblYouAre = new JLabel("You are:");
		lblYouAre.setHorizontalAlignment(SwingConstants.CENTER);
		lblYouAre.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lblYouAre.setBounds(34, 11, 90, 23);
		mainpane.add(lblYouAre);
		
		lblPlayerMarkType = new JLabel();
		lblPlayerMarkType.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlayerMarkType.setFont(new Font("Segoe UI Historic", Font.PLAIN, 20));
		lblPlayerMarkType.setBounds(134, 5, 276, 33);
		mainpane.add(lblPlayerMarkType);
		resetLblPlayerType();
		
		this.setTitle("TicTacToe");
		this.setResizable(false);
		this.setVisible(true);
	}

	public void linkAllSourcesToListener(MainController_Client mainWinController)
	{
		btnZeroZero.addActionListener(mainWinController);
		btnZeroOne.addActionListener(mainWinController);
		btnZeroTwo.addActionListener(mainWinController);
		btnOneZero.addActionListener(mainWinController);
		btnOneOne.addActionListener(mainWinController);
		btnOneTwo.addActionListener(mainWinController);
		btnTwoZero.addActionListener(mainWinController);
		btnTwoOne.addActionListener(mainWinController);
		btnTwoTwo.addActionListener(mainWinController);
		
		btnRegisterPlayer.addActionListener(mainWinController);
		btnRestartGame.addActionListener(mainWinController);
		btnStartGame.addActionListener(mainWinController);
	}
	
	public void resetLblPlayerType()
	{
		lblPlayerMarkType.setForeground(Color.BLACK);
		lblPlayerMarkType.setText("None registered");
		markOfMainWindow=null;
	}
	
	public void updateLblPlayerType()
	{
		lblPlayerMarkType.setForeground(new Color(204, 51, 0));
		lblPlayerMarkType.setText("Player "+markOfMainWindow);
		Font f = lblPlayerMarkType.getFont();
		float fsize = lblPlayerMarkType.getFont().getSize();
		lblPlayerMarkType.setFont(f.deriveFont(Font.BOLD, fsize));
	}
	
	// GETTERs and SETTERs.
	
	@Deprecated
	public JLabel getLblPlayerMarkType() {
		return lblPlayerMarkType;
	}

	@Deprecated
	public void setLblPlayerMarkType(JLabel lblPlayerMarkType) {
		this.lblPlayerMarkType = lblPlayerMarkType;
	}
	
	public String getMarkOfMainWindow() 
	{
		return markOfMainWindow;
	}

	public void setMarkOfMainWindow(String playerMark) 
	{
		this.markOfMainWindow = playerMark;
	}
	

	public JPanel getMainpane() {
		return mainpane;
	}

	public JTextField getUsernameDisplay() {
		return usernameDisplay;
	}

	public JTextField getCurrentPlayerDisplay() {
		return currentPlayerDisplay;
	}

	public JTextArea getMainDisplay() {
		return mainDisplay;
	}

	public JLabel getLblCurrentPlayer() {
		return lblCurrentPlayer;
	}

	public JLabel getLblUsername() {
		return lblUsername;
	}

	public JButton getBtnStartGame() {
		return btnStartGame;
	}

	public void setBtnStartGame(JButton btnStartGame) {
		this.btnStartGame = btnStartGame;
	}

	public void setMainpane(JPanel mainpane) {
		this.mainpane = mainpane;
	}

	public void setUsernameDisplay(JTextField usernameDisplay) {
		this.usernameDisplay = usernameDisplay;
	}

	public void setCurrentPlayerDisplay(JTextField currentPlayerDisplay) {
		this.currentPlayerDisplay = currentPlayerDisplay;
	}

	public void setMainDisplay(JTextArea mainDisplay) {
		this.mainDisplay = mainDisplay;
	}

	public void setLblCurrentPlayer(JLabel lblCurrentPlayer) {
		this.lblCurrentPlayer = lblCurrentPlayer;
	}

	public void setLblUsername(JLabel lblUsername) {
		this.lblUsername = lblUsername;
	}

	public GameplayButton getBtnZeroZero() 
	{
		return btnZeroZero;
	}

	public GameplayButton getBtnZeroOne() 
	{
		return btnZeroOne;
	}

	public GameplayButton getBtnZeroTwo() 
	{
		return btnZeroTwo;
	}

	public GameplayButton getBtnOneZero() 
	{
		return btnOneZero;
	}

	public GameplayButton getBtnOneOne() {
		return btnOneOne;
	}

	public GameplayButton getBtnOneTwo() {
		return btnOneTwo;
	}

	public GameplayButton getBtnTwoZero() 
	{
		return btnTwoZero;
	}

	public GameplayButton getBtnTwoOne() 
	{
		return btnTwoOne;
	}

	public GameplayButton getBtnTwoTwo() 
	{
		return btnTwoTwo;
	}

	public JButton getBtnRegisterPlayer() 
	{
		return btnRegisterPlayer;
	}

	public JButton getBtnRestartGame() 
	{
		return btnRestartGame;
	}

	public void setBtnZeroZero(GameplayButton btnZeroZero) 
	{
		this.btnZeroZero = btnZeroZero;
	}

	public void setBtnZeroOne(GameplayButton btnZeroOne) 
	{
		this.btnZeroOne = btnZeroOne;
	}

	public void setBtnZeroTwo(GameplayButton btnZeroTwo) 
	{
		this.btnZeroTwo = btnZeroTwo;
	}

	public void setBtnOneZero(GameplayButton btnOneZero) 
	{
		this.btnOneZero = btnOneZero;
	}

	public void setBtnOneOne(GameplayButton btnOneOne) 
	{
		this.btnOneOne = btnOneOne;
	}

	public void setBtnOneTwo(GameplayButton btnOneTwo) 
	{
		this.btnOneTwo = btnOneTwo;
	}

	public void setBtnTwoZero(GameplayButton btnTwoZero) 
	{
		this.btnTwoZero = btnTwoZero;
	}

	public void setBtnTwoOne(GameplayButton btnTwoOne) 
	{
		this.btnTwoOne = btnTwoOne;
	}

	public void setBtnTwoTwo(GameplayButton btnTwoTwo) 
	{
		this.btnTwoTwo = btnTwoTwo;
	}

	public void setBtnRegisterPlayer(JButton btnRegisterPlayer) 
	{
		this.btnRegisterPlayer = btnRegisterPlayer;
	}

	public void setBtnRestartGame(JButton btnRestartGame) 
	{
		this.btnRestartGame = btnRestartGame;
	}
}
