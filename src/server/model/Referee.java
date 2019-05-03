package server.model;

import server.Labels;

public class Referee implements Labels{
	
	// the object of this class runs and oversees the TicTacToe game.
	
	
	// ATTRIBUTES.
	
	public Player currentPlayer; //whichever player is the current player.
	Game theGame;
	
	// INITIALIZER METHODS.
	
	public Referee(Game theGame) 
	{
		this.theGame=theGame;
	}
	
	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer=currentPlayer;
	}
	
	// OPERATIONAL METHODS.

	public void setMatchup()
	{
		theGame.xPlayer.setOpponent(theGame.oPlayer);
		theGame.oPlayer.setOpponent(theGame.xPlayer);
		setCurrentPlayer(theGame.xPlayer); //xPlayer is always the first to play (standard TicTacToe rule).
	}
	
	/**
	 * This checks for the current result of the match: that is, whether any of the two players has won,
	 * or if the match ended in a tie.
	 * @return the current result of the game.
	 */
	public String getGameResult()
	{
		if (theGame.theBoard.xWins()==true)
		{
			//xPlayer won the game.
			String result = "\n "+theGame.xPlayer.getPlayerName() + " won the game." + "\n The game is now over.";
			return result;
		}
		else if (theGame.theBoard.oWins()==true)
		{
			//oPlayer won the game.
			String result = "\n "+theGame.oPlayer.getPlayerName() + " won the game." + "\n The game is now over.";
			return result;
		}
		else if (theGame.theBoard.isFull()==true)
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
		if(theGame.theBoard.markCount>0)
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
		
		if(theGame.xPlayer==null||theGame.oPlayer==null)
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
		if(theGame.xPlayer==null)
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
		if(theGame.oPlayer==null)
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
		if(theGame.xPlayer.getOpponent()==null||theGame.oPlayer.getOpponent()==null)
		{			
			return false;
		}
		else
		{
			return true;
		}
	}
}
