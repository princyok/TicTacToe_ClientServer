package server.model;

import java.io.*;

import server.Labels;

public class Game implements Labels {
	
	// the object of this class runs and controls the TicTacToe game.

	
	//ATTRIBUTES.

	protected Board theBoard; // the board.
	protected Player xPlayer; // the x-player.	
	protected Player oPlayer; // the o-player.
	protected Player currentPlayer; //whichever player is the current player.

	
	//INITIALIZER METHODS.
	
	/**
	 * Instantiates a Game object and also creates an accompanying Board object.
	 * @throws IOException 
	 */
    public Game() throws IOException 
    {
        theBoard  = new Board();
	}
    
	/**
	 * initializes xPlayer.
	 * @throws IOException 
	 */
    public void createXPlayer(String name, String playerType)
    {
    	if(playerType.equals(HUMAN_PLAYER_LABEL))
    	{
			xPlayer = new HumanPlayer(name, LETTER_X);
			xPlayer.setGame(this);
    	}
    	
    	if(playerType.equals(RANDOM_COMP_PLAYER_LABEL))
    	{
			xPlayer = new RandomComputerPlayer(name, LETTER_X);
			xPlayer.setGame(this);
    	}
    	
    	if(playerType.equals(BLOCKING_COMP_PLAYER_LABEL))
    	{
			xPlayer = new BlockingComputerPlayer(name, LETTER_X);
			xPlayer.setGame(this);
    	}
    	
    	if(playerType.equals(WINNING_COMP_PLAYER_LABEL))
    	{
			xPlayer = new WinningComputerPlayer(name, LETTER_X);
			xPlayer.setGame(this);
    	}
    }
    
	/**
	 * initializes oPlayer.
	 * @throws IOException 
	 */    public void createOPlayer(String name, String playerType)
    {	
		if(playerType.equals(HUMAN_PLAYER_LABEL))
    	{
			oPlayer = new HumanPlayer(name, LETTER_O);
			oPlayer.setGame(this);
    	}
		if(playerType.equals(RANDOM_COMP_PLAYER_LABEL))
    	{
			oPlayer = new RandomComputerPlayer(name, LETTER_O);
			oPlayer.setGame(this);
    	}
		if(playerType.equals(BLOCKING_COMP_PLAYER_LABEL))
    	{
			oPlayer = new BlockingComputerPlayer(name, LETTER_O);
			oPlayer.setGame(this);
    	}
		if(playerType.equals(WINNING_COMP_PLAYER_LABEL))
    	{
			oPlayer = new WinningComputerPlayer(name, LETTER_O);
			oPlayer.setGame(this);
    	}
    }
    
    /**
     * Sets up the matchup. 
     */
	public void setMatchup()
	{
		this.xPlayer.setOpponent(this.oPlayer);
		this.oPlayer.setOpponent(this.xPlayer);
		setCurrentPlayer(this.xPlayer); //xPlayer is always the first to play (standard TicTacToe rule).
	}
	
	// OPERATIONAL METHODS.
	
	/**
	 * This checks for the current result of the match: that is, whether any of the two players has won,
	 * or if the match ended in a tie.
	 * @return the current result of the game.
	 */
	public String getGameResult()
	{
		if (this.theBoard.xWins()==true)
		{
			//xPlayer won the game.
			String result = "\n "+this.xPlayer.getPlayerName() + " won the game." + "\n The game is now over.";
			return result;
		}
		else if (this.theBoard.oWins()==true)
		{
			//oPlayer won the game.
			String result = "\n "+this.oPlayer.getPlayerName() + " won the game." + "\n The game is now over.";
			return result;
		}
		else if (this.theBoard.isFull()==true)
		{
			//game is a tie.
			String result = "\n The game ended in a tie." + "\n The game is now over.";
			return result;
		}
		else
		{
			String result = CONTINUE_THE_GAME;
			return result;
		}
	}
	
	/**
	 * This checks whether another turn can be played. If there is a win or a tie,
	 * it will return false (i.e. do not continue the game), else it will
	 * return true (i.e. continue the game).
	 * @return a boolean of whether the game should continue or end.
	 * 
	 */
	public boolean checkIfToContinue()
	{
		if(this.getGameResult()==CONTINUE_THE_GAME)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkIfAnyMoveYet()
	{
		if(this.theBoard.markCount>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkBothPlayersRegistered()
	{
		 //check that players have been created.
		
		if(this.xPlayer==null||this.oPlayer==null)
		{			
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkXPlayerRegistered()
	{
		if(this.xPlayer==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkOPlayerRegistered()
	{
		if(this.oPlayer==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public String getCurrentPlayerMark()
	{
		return currentPlayer.getPlayerMark();
	}
	
	public String getCurrentPlayerName()
	{
		return currentPlayer.getPlayerName();
	}
	
	
	public String getCurrentPlayerType()
	{
		return currentPlayer.getClass().getName();
	}
	
	public void setOpponentOfCurrentAsCurrent()
	{
		currentPlayer=currentPlayer.getOpponent();
	}
	
	public boolean checkMatchupIsSet()
	{
		if(this.xPlayer.getOpponent()==null||this.oPlayer.getOpponent()==null)
		{			
			return false;
		}
		else
		{
			return true;
		}
	}
    
    //GETTERS and SETTERS.
	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer=currentPlayer;
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
    public Board getBoard()
    {
    	return theBoard;
    }
    
    public Player getXPlayer()
    {
    	return xPlayer;
    }
    
    public Player getOPlayer()
    {
    	return oPlayer;
    }
}
