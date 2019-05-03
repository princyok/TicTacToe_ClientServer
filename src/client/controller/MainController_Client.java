package client.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;

import client.controller.PlayerRegisterController_Client;
import client.view.GameplayButton;
import client.view.MainWindow;
import client.view.PlayerRegisterWindow;
import client.Prompts;

/**
 * The class has four crucial attributes:
 * <br><br>
 * <strong>mainWindow</strong>(aggregation):
 * The main window of the client GUI.
 * <br>
 * <strong>responseFromGame</strong>(composition):
 * the reader that reads the responses from the game.
 * <br>
 * <strong>broadcastFromGame</strong>(composition):
 * the reader that reads the responses from the game.
 * <br>
 * <strong>outputToGame</strong>(composition):
 * the write that writes communications to the game.
 * 
 * @author prince
 *
 */
public class MainController_Client implements ActionListener, Runnable, Prompts
{

	// ATTRIBUTES.

	protected MainWindow mainWindow;

	protected BufferedReader responseFromGame, broadcastFromGame;
	protected PrintWriter outputToGame;
	protected JsonObject jsonToGame;

	protected boolean allClientsReady=false;


	//INITIALIZER METHODS.

	/**
	 * Instantiates the controller for the main window of the GUI.
	 * @param mainWindow
	 * @param socketForGame
	 * @param socketForBroadcastFromGame
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public MainController_Client(MainWindow mainWindow, Socket socketForGame, Socket socketForBroadcastFromGame) 
			throws IOException, InterruptedException
	{
		responseFromGame = new BufferedReader(new InputStreamReader(socketForGame.getInputStream()));
		outputToGame = new PrintWriter(socketForGame.getOutputStream(), true);

		this.mainWindow = mainWindow;

		broadcastFromGame =  new BufferedReader(new InputStreamReader(socketForBroadcastFromGame.getInputStream()));

		jsonToGame = new JsonObject();
	}

	// OVERRIDDEN METHODS.

	/**
	 * This run method will run in a thread that will allow it to perpetually 
	 * listen to the broadcasts from the game controller (in the server side).
	 */
	@Override
	public void run() 
	{
		try 
		{
			while(true)
			{
				if (this.broadcastFromGame!=null && this.broadcastFromGame.ready())
				{
					this.listenToBroadcastFromGame();
				}
			} 
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, GENERIC_ERROR);
		}
	}

	/**
	 * Handles all GUI events.
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(!allClientsReady)
		{
			JOptionPane.showMessageDialog(null, TWO_CLIENTS_NEEDED);
			return;
		}

		if (event.getSource()==mainWindow.getBtnZeroZero() ||
				event.getSource()==mainWindow.getBtnZeroOne() ||
				event.getSource()==mainWindow.getBtnZeroTwo() ||
				event.getSource()==mainWindow.getBtnOneZero() ||
				event.getSource()==mainWindow.getBtnOneOne() ||
				event.getSource()==mainWindow.getBtnOneTwo() ||
				event.getSource()==mainWindow.getBtnTwoZero() ||
				event.getSource()==mainWindow.getBtnTwoOne() ||
				event.getSource()==mainWindow.getBtnTwoTwo())
		{
			try
			{
				registerClickOnGameBoard(event);
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, GENERIC_ERROR);
			}
		}

		if (event.getSource()==mainWindow.getBtnStartGame())
		{
			try
			{
				boolean regStatus = server_checkBothPlayersRegistered();

				if(regStatus==true)
				{
					server_setMatchup();
					nextTurn();
				}
				else
				{
					JOptionPane.showMessageDialog(null, PLEASE_REGISTER);
				}

			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, GENERIC_ERROR);
			}
		}

		if (event.getSource()==mainWindow.getBtnRegisterPlayer())
		{
			try
			{
				PlayerRegisterWindow prFrame = new PlayerRegisterWindow();
				PlayerRegisterController_Client prController = new PlayerRegisterController_Client(prFrame);

				prFrame.linkAllSourcesToController(prController);
				prController.linkToAnotherController(this);
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, GENERIC_ERROR);
			}
		}

		if (event.getSource()==mainWindow.getBtnRestartGame())
		{
			try
			{			
				broadcast_startNewGame();
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, GENERIC_ERROR);
			}
		}
	}

	//OPERATIONAL METHODS.

	/**
	 * Handles all broadcasts from the game controller to all clients.
	 * @throws IOException
	 */
	synchronized public void listenToBroadcastFromGame() throws IOException
	{
		String broadcastedFromGame;

		if (broadcastFromGame.ready())
		{
			broadcastedFromGame = broadcastFromGame.readLine();

			JsonObject jsonFromGameBroadcast = new JsonParser().parse(broadcastedFromGame).getAsJsonObject();

			if (jsonFromGameBroadcast.get("broadcast_displayMove") != null)
			{
				JsonArray args = (JsonArray) jsonFromGameBroadcast.get("broadcast_displayMove");

				String row = args.get(0).getAsString();
				String col = args.get(1).getAsString();
				String playerMark= args.get(2).getAsString();

				displayMove(row, col, playerMark);
			}

			if (jsonFromGameBroadcast.get("broadcast_displayCurrentPlayer") != null)
			{
				JsonArray args = (JsonArray) jsonFromGameBroadcast.get("broadcast_displayCurrentPlayer");

				String playerName = args.get(0).getAsString();
				String playerMark= args.get(1).getAsString();

				displayCurrentPlayer(playerName, playerMark);
			}

			if(jsonFromGameBroadcast.get("broadcast_clearEverything") != null)
			{
				clearEverything();
			}

			if(jsonFromGameBroadcast.get("broadcast_displayGameResult") != null)
			{
				String gameResult = jsonFromGameBroadcast.get("broadcast_displayGameResult").getAsString()
						.replaceAll(Pattern.quote("**"),"\n");
				mainWindow.getMainDisplay().setText(gameResult);
			}

			if(jsonFromGameBroadcast.get("broadcast_startNewGame") != null)
			{
				clearEverything();
				mainWindow.resetLblPlayerType();
			}

			if(jsonFromGameBroadcast.get("broadcast_allClientsReady")!= null)
			{
				String status =jsonFromGameBroadcast.get("broadcast_allClientsReady").getAsString();
				allClientsReady=Boolean.parseBoolean(status);
			}
		}
	}

	/**
	 *  Registers all clicks on the gameboard (the GUI), and sends instructions/data to the server-side.
	 *  
	 * @param event
	 * @throws IOException
	 */
	public void registerClickOnGameBoard(ActionEvent event) throws IOException
	{
		if(server_checkBothPlayersRegistered())
		{	
			if(server_checkMatchupIsSet())
			{
				if(mainWindow.getMarkOfMainWindow().equals(server_getCurrentPlayerMark()))
				{
					GameplayButton buttonClicked = (GameplayButton) ((JButton) event.getSource());

					String rowOfCellClicked = Integer.toString(buttonClicked.getRow());
					String colOfCellClicked = Integer.toString(buttonClicked.getCol());

					boolean cellStatus=server_isCellPlayed(rowOfCellClicked, colOfCellClicked);

					if (cellStatus==false) 
					{
						server_play(rowOfCellClicked, colOfCellClicked);

						cellStatus=server_isCellPlayed(rowOfCellClicked, colOfCellClicked);

						if (cellStatus==true)
						{
							String playerMark=server_getCurrentPlayerMark();
							buttonClicked.setText(server_getCurrentPlayerMark());

							broadcast_displayMove(rowOfCellClicked, colOfCellClicked, playerMark);
						}
						nextTurn();
					}
					else
					{
						JOptionPane.showMessageDialog(null, CELL_ALREADY_PLAYED);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, NOT_CURRENT_PLAYER);
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null, START_GAME_BEFORE_PLAYING);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, PLEASE_REGISTER);
		}
	}

	/**
	 * Takes care of all operations needed to prepare the game (on server-side) and the GUI for the 
	 * next turn after a move has been played or after the game starts. 
	 * And if the current player after that move is the computer, it executes the computer's turn.
	 * @throws IOException
	 */
	synchronized public void nextTurn() throws IOException
	{
		if(server_checkBothPlayersRegistered())
		{
			if (server_checkIfToContinue())
			{
				if(server_checkIfAnyMoveYet()) //if a move has been made (i.e. not the first move of the game).
				{
					server_setOpponentOfCurrentAsCurrent();
				}

				String playerMark=server_getCurrentPlayerMark();
				String playerName=server_getCurrentPlayerName();
				broadcast_displayCurrentPlayer(playerName,playerMark);

				if(!server_getCurrentPlayerType().contains("HumanPlayer")) //if not a human player.
				{
					server_playForComputerPlayer();
					nextTurn();
				}
			}
			else
			{
				broadcast_displayGameResult();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, PLEASE_REGISTER);
		}
	}

	// INSTRUCTIONS to SERVER.


	private void broadcast_displayGameResult()
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod, JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
	}

	private void broadcast_displayCurrentPlayer(String playerName, String playerMark)
	{
		JsonArray args = new JsonArray();
		args.add(playerName);
		args.add(playerMark);

		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod, args);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
	}

	private void broadcast_displayMove(String r, String c, String playerMark)
	{
		JsonArray args = new JsonArray();
		args.add(r);
		args.add(c);
		args.add(playerMark);

		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod, args);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
	}

	private void broadcast_startNewGame() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
	}

	private void server_setOpponentOfCurrentAsCurrent() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
	}

	private void server_play(String row, String col) throws IOException
	{
		JsonArray args = new JsonArray();
		args.add(row);
		args.add(col);

		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod, args);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
	}

	private boolean server_isCellPlayed(String row, String col) throws IOException
	{
		JsonArray args = new JsonArray();
		args.add(row);
		args.add(col);

		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod, args);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String cellStatus=responseFromGame.readLine();		
		return Boolean.parseBoolean(cellStatus);
	}

	private boolean server_checkIfToContinue() throws  IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String contStatus=responseFromGame.readLine();
		return Boolean.parseBoolean(contStatus);
	}

	private boolean server_checkIfAnyMoveYet() throws  IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String anyMoveYet=responseFromGame.readLine();
		return Boolean.parseBoolean(anyMoveYet);
	}

	private String server_getCurrentPlayerName() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String playerName=responseFromGame.readLine();
		return playerName;
	}

	private String server_getCurrentPlayerMark() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String playerMark=responseFromGame.readLine();
		return playerMark;
	}

	private String server_getCurrentPlayerType() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String playerType=responseFromGame.readLine();

		return playerType;
	}

	private boolean server_checkBothPlayersRegistered() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String regStatus = responseFromGame.readLine();
		return Boolean.parseBoolean(regStatus);
	}

	private boolean server_checkMatchupIsSet() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String matchupStatus = responseFromGame.readLine();

		return Boolean.parseBoolean(matchupStatus);
	}

	private void server_setMatchup() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod, JsonNull.INSTANCE);					
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);
	}

	private void server_playForComputerPlayer() throws IOException
	{
		class LocalInner{}; 
		String nameofCurrMethod = LocalInner.class.getEnclosingMethod().getName(); 

		jsonToGame.add(nameofCurrMethod,JsonNull.INSTANCE);
		outputToGame.println(jsonToGame.toString());
		jsonToGame.remove(nameofCurrMethod);

		String response=responseFromGame.readLine();
		JsonObject jsonFromGame = new JsonParser().parse(response).getAsJsonObject();
		JsonArray cellPlayed = (JsonArray) jsonFromGame.get(nameofCurrMethod);
		broadcast_displayMove(cellPlayed.get(0).getAsString(),cellPlayed.get(1).getAsString(), server_getCurrentPlayerMark());
	}

	// HELPER METHODS.

	private void displayCurrentPlayer(String playerName, String playerMark)
	{
		mainWindow.getUsernameDisplay().setText(playerName);
		mainWindow.getCurrentPlayerDisplay().setText("Player "+playerMark);
	}

	private void displayMove(String r, String c, String playerMark)
	{
		int row = Integer.parseInt(r);
		int col = Integer.parseInt(c);

		if (row==0)
		{
			if(col==0)
			{
				mainWindow.getBtnZeroZero().setText(playerMark);
			}
			if(col==1)
			{
				mainWindow.getBtnZeroOne().setText(playerMark);
			}
			if(col==2)
			{
				mainWindow.getBtnZeroTwo().setText(playerMark);
			}
		}
		else if (row==1)
		{
			if(col==0)
			{
				mainWindow.getBtnOneZero().setText(playerMark);
			}
			if(col==1)
			{
				mainWindow.getBtnOneOne().setText(playerMark);
			}
			if(col==2)
			{
				mainWindow.getBtnOneTwo().setText(playerMark);
			}
		}
		else if(row==2)
		{
			if(col==0)
			{
				mainWindow.getBtnTwoZero().setText(playerMark);
			}
			if(col==1)
			{
				mainWindow.getBtnTwoOne().setText(playerMark);
			}
			if(col==2)
			{
				mainWindow.getBtnTwoTwo().setText(playerMark);
			}
		}
	}

	private void clearEverything()
	{
		this.clearAllCells();
		this.clearCurrentPlayerDisplay();
		this.clearUsernameDisplay();
		this.clearMainDisplay();
	}

	/**
	 * Clears all cells on the gameboard in the GUI. Does not send any communications to server.
	 */
	synchronized private void clearAllCells()
	{
		//reset all buttons to blank.

		Component [] allComps = ((Component [])mainWindow.getMainpane().getComponents());

		for(int i=0;i<allComps.length;++i)
		{	
			Component comp = allComps[i];
			if(comp instanceof GameplayButton)
			{
				GameplayButton cell = (GameplayButton) comp;
				cell.setText("");
			}
		}
	}

	/**
	 * clear the current player display.
	 */
	synchronized public void clearCurrentPlayerDisplay()
	{
		mainWindow.getCurrentPlayerDisplay().setText("");
	}

	/**
	 * clear the current-player username display.
	 */
	synchronized public void clearUsernameDisplay()
	{
		mainWindow.getUsernameDisplay().setText("");
	}

	/**
	 * clear the main display.
	 */
	synchronized public void clearMainDisplay()
	{
		mainWindow.getMainDisplay().setText("");
	}
}
