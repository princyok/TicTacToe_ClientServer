package client.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import client.view.PlayerRegisterWindow;
import client.controller.MainController_Client;
import client.Prompts;

public class PlayerRegisterController_Client extends WindowAdapter implements Prompts, ActionListener 
{
	protected PlayerRegisterWindow prWindow;
	protected MainController_Client mainWindowController;
	protected JsonObject jsonToGame;
	
	//INITIALIZER METHODS.
	/**
	 * Initializes the player registration window with the already instantiated 
	 * player registration window.
	 * @param prFrame
	 */
	public PlayerRegisterController_Client(PlayerRegisterWindow prFrame)
	{
		this.prWindow = prFrame;
		jsonToGame = new JsonObject();
	}
	
	/**
	 * Initializes the controller main window with the already instantiated
	 * main window controller.
	 * @param mainWindowController
	 */
	public void linkToAnotherController(MainController_Client mainWindowController)
	{
		this.mainWindowController=mainWindowController;
		mainWindowController.mainWindow.setEnabled(false); //make mainframe unclickable.
	}
	
	// EVENT-HANDLING METHODS.
	
	@Override
	public void windowClosing(WindowEvent event)
	{
		mainWindowController.mainWindow.setEnabled(true); //restore clickableness of mainframe.
		prWindow.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource()==prWindow.getBtnDoneRegisteringPlayer())
		{
			try
			{
				String playerType = prWindow.getCbxPlayerType().getSelectedItem().toString();				
				String playerUsername = prWindow.getPlayerInputField().getText();
				String s = prWindow.getCbxPlayerMark().getSelectedItem().toString();
				String playerMark = String.valueOf(s.charAt(s.length() - 1)); //just get last character of the string.
				
				if (!playerUsername.equals(""))
				{
					if(playerMark.equals("X"))
					{
						boolean xPlayerStatus=server_checkXPlayerRegistered();
						
						if(!xPlayerStatus)
						{
							server_createPlayer(playerUsername, playerType, playerMark);
						}
						else
						{
							JOptionPane.showMessageDialog(null, X_ALREADY_TAKEN);
						}	
					}
					
					if(playerMark.equals("O"))
					{
						boolean oPlayerStatus=server_checkOPlayerRegistered();
						
						if(!oPlayerStatus)
						{
							server_createPlayer(playerUsername, playerType, playerMark);
						}
						else
						{
							JOptionPane.showMessageDialog(null, O_ALREADY_TAKEN);
						}
					}
				}
				else
				{			
					JOptionPane.showMessageDialog(null, PROVIDE_USERNAME);
				}
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, GENERIC_ERROR);
			}
		}
	}	
	
	//INSTRUCTIONS to SERVER.
	
	/**
	 * creates the player in the game (in the server side).
	 * @param playerUsername
	 * @param playerType
	 * @param playerMark
	 */
	private void server_createPlayer(String playerUsername, String playerType, String playerMark)
	{
		//create the players.		
		JsonArray args = new JsonArray();
		args.add(playerUsername);
		args.add(playerType);
		args.add(playerMark);
		
		class LocalInner{}; 
        String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 
		
		jsonToGame.add(nameofCurrMethod, args);
		mainWindowController.outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
		
		mainWindowController.mainWindow.setEnabled(true); //restore clickableness of mainframe.
		
		mainWindowController.mainWindow.setMarkOfMainWindow(playerMark);
		
		mainWindowController.mainWindow.updateLblPlayerType();
		
		prWindow.dispose();
	}
	
	private boolean server_checkXPlayerRegistered() throws IOException
	{
		class LocalInner{}; 
        String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 
        
		jsonToGame.add(nameofCurrMethod, JsonNull.INSTANCE);
		mainWindowController.outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
		
		String xPlayerStatus=mainWindowController.responseFromGame.readLine();		
		return Boolean.parseBoolean(xPlayerStatus);
	}
	
	private boolean server_checkOPlayerRegistered() throws IOException
	{
		class LocalInner{}; 
        String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 
        
		jsonToGame.add(nameofCurrMethod, JsonNull.INSTANCE);
		mainWindowController.outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
		
		String OPlayerStatus=mainWindowController.responseFromGame.readLine();		
		return Boolean.parseBoolean(OPlayerStatus);
	}
}
