package server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import server.Labels;
import server.model.Game;

public class GameController implements Labels, Runnable
{
	protected Game theGame;

	protected BufferedReader inputFromClient1;
	protected PrintWriter outputToClient1;
	protected PrintWriter broadcastToClient1;

	protected BufferedReader inputFromClient2;
	protected PrintWriter outputToClient2;
	protected PrintWriter broadcastToClient2;
	
	protected boolean AllClientsReady=false;
	
	//INITIALIZER METHODS.

	/**
	 * Instantiates the controller.
	 * @param theGame
	 * @param socketForClient1
	 * @param socketForBroadcastingToClient1
	 * @param socketForClient2
	 * @param socketForBroadcastingToClient2
	 * @throws IOException
	 */
	public GameController(Game theGame, Socket socketForClient1, Socket socketForBroadcastingToClient1, 
			Socket socketForClient2, Socket socketForBroadcastingToClient2) throws IOException
	{
		this.theGame=theGame;

		inputFromClient1 = new BufferedReader(new InputStreamReader(socketForClient1.getInputStream()));
		outputToClient1 = new PrintWriter(socketForClient1.getOutputStream(), true);
		broadcastToClient1 = new PrintWriter(socketForBroadcastingToClient1.getOutputStream(), true);

		inputFromClient2 = new BufferedReader(new InputStreamReader(socketForClient2.getInputStream()));
		outputToClient2= new PrintWriter(socketForClient2.getOutputStream(), true);
		broadcastToClient2 = new PrintWriter(socketForBroadcastingToClient2.getOutputStream(), true);
		
		AllClientsReady=true;
		JsonObject infoToClient =new JsonObject();
		infoToClient.addProperty("broadcast_allClientsReady", Boolean.toString(AllClientsReady));
		broadcastToClients(infoToClient.toString());
	}

	// THREAD METHODS.

	@Override
	public void run() 
	{
		try 
		{
			while(true) //prevents the server from dying.
			{
				if (inputFromClient1!=null || inputFromClient2!=null)
				{
					if (inputFromClient1.ready())
					{
						this.listenAndRespondToClient(inputFromClient1, outputToClient1);
					}
					else if (inputFromClient2.ready())
					{
						this.listenAndRespondToClient(inputFromClient2, outputToClient2);
					}
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	//OPERATIONAL METHODS.

	/**
	 * Handles all communications coming in from client, and the responses to the client.
	 * @param inputFromClient
	 * @param outputToClient
	 * @throws IOException
	 */
	synchronized public void listenAndRespondToClient(BufferedReader inputFromClient, PrintWriter outputToClient) throws IOException
	{
		/* this method is running endlessly (in a while loop), and will 
		 * only execute if a connection from client has been made or 
		 * remains intact, and data has been outputted by the client.
		 */

		// Handling of data sent from client's controller.

		String messageFromClient = inputFromClient.readLine();


		if(messageFromClient!=null)
		{
			JsonObject jsonFromClient = new JsonParser().parse(messageFromClient).getAsJsonObject();

			if (jsonFromClient.get("server_createPlayer") != null)
			{
				createPlayer(jsonFromClient);
			}
			
			if(jsonFromClient.get("server_setMatchup") != null)
			{
				theGame.setMatchup();
			}

			if(jsonFromClient.get("server_checkMatchupIsSet") != null)
			{
				boolean  result=theGame.checkMatchupIsSet();
				outputToClient.println(Boolean.toString(result));
			}

			if(jsonFromClient.get("server_checkBothPlayersRegistered") != null)
			{
				boolean  result = theGame.checkBothPlayersRegistered();
				outputToClient.println(Boolean.toString(result));
			}

			if(jsonFromClient.get("server_checkXPlayerRegistered") != null)
			{
				boolean  result = theGame.checkXPlayerRegistered();
				outputToClient.println(Boolean.toString(result));
			}

			if(jsonFromClient.get("server_checkOPlayerRegistered") != null)
			{
				boolean  result = theGame.checkOPlayerRegistered();
				outputToClient.println(Boolean.toString(result));
			}

			if(jsonFromClient.get("server_setOpponentOfCurrentAsCurrent") != null)
			{
				theGame.setOpponentOfCurrentAsCurrent();
			}

			if(jsonFromClient.get("server_checkIfAnyMoveYet") != null)
			{
				boolean status=theGame.checkIfAnyMoveYet();
				outputToClient.println(Boolean.toString(status));
			}

			if(jsonFromClient.get("server_checkIfToContinue") != null)
			{
				boolean contStatus=theGame.checkIfToContinue();
				outputToClient.println(Boolean.toString(contStatus));
			}

			if(jsonFromClient.get("server_getCurrentPlayerMark") != null)
			{
				String playerMark=theGame.getCurrentPlayerMark();
				outputToClient.println(playerMark);
			}

			if(jsonFromClient.get("server_getCurrentPlayerName") != null)
			{
				String playerName=theGame.getCurrentPlayerName();
				outputToClient.println(playerName);
			}
			
			if(jsonFromClient.get("server_getCurrentPlayerType") != null)
			{
				String playerType=theGame.getCurrentPlayerType();
				outputToClient.println(playerType);
			}

			if(jsonFromClient.get("server_isCellPlayed") != null)
			{				
				JsonArray args = (JsonArray) jsonFromClient.get("server_isCellPlayed");
				
				int row = Integer.parseInt(args.get(0).getAsString());
				int col = Integer.parseInt(args.get(1).getAsString());
				boolean cellStatus = theGame.getBoard().isCellPlayed(row, col);
				outputToClient.println(Boolean.toString(cellStatus));
			}

			if(jsonFromClient.get("server_play") != null)
			{
				JsonArray args = (JsonArray) jsonFromClient.get("server_play");

				int row = Integer.parseInt(args.get(0).getAsString());
				int col = Integer.parseInt(args.get(1).getAsString());

				theGame.getCurrentPlayer().play(row,col);
			}
			
			if(jsonFromClient.get("server_playForComputerPlayer") != null)
			{
				ArrayList<Integer> cellPlayed = theGame.getCurrentPlayer().play();
				JsonArray cPlayed = new JsonArray();
				cPlayed.add(cellPlayed.get(0));
				cPlayed.add(cellPlayed.get(1));
		        
				jsonFromClient.remove("server_playForComputerPlayer");
				jsonFromClient.add("server_playForComputerPlayer", cPlayed);
		        
				outputToClient.println(jsonFromClient.toString());
			}

			//BROADCASTING.

			if (jsonFromClient.get("broadcast_displayMove") != null)
			{				
				broadcastToClients(jsonFromClient.toString());
			}
			
			if(jsonFromClient.get("broadcast_startNewGame") != null)
			{
				this.theGame= new Game();
				
				broadcastToClients(jsonFromClient.toString());
			}

			if(jsonFromClient.get("broadcast_displayCurrentPlayer") != null)
			{
				broadcastToClients(jsonFromClient.toString());
			}

			if(jsonFromClient.get("broadcast_displayGameResult") != null)
			{
				String gameResult=theGame.getGameResult();
				gameResult=gameResult.replaceAll(Pattern.quote("\n"), "**");
				
				jsonFromClient.remove("broadcast_displayGameResult");
				jsonFromClient.addProperty("broadcast_displayGameResult", gameResult);

				broadcastToClients(jsonFromClient.toString());				
			}
		}
	}

	// HELPER METHODS.

	private void createPlayer(JsonObject jsonFromClient)
	{
		JsonArray args = (JsonArray) jsonFromClient.get("server_createPlayer");

		String playerUsername = args.get(0).getAsString();
		String playerType = args.get(1).getAsString();
		String playerMark= args.get(2).getAsString();

		if(playerMark.charAt(0)==LETTER_X)
		{
			theGame.createXPlayer(playerUsername, playerType);
		}
		if(playerMark.charAt(0)==LETTER_O)
		{
			theGame.createOPlayer(playerUsername, playerType);
		}	
	}

	/**
	 * Handles communications that are sent (broadcast) to all clients.
	 * @param instruction
	 * @throws IOException
	 */
	synchronized private void broadcastToClients(String instruction) throws IOException
	{
		// HANDLING OF REQUEST TO BROADCAST THAT IS SENT FROM CLIENT CONTROLLER.

		if(instruction!=null)
		{
			broadcastToClient1.println(instruction);
			broadcastToClient2.println(instruction);
		}
	}
}
