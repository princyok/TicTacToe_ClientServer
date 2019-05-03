package server.model;

import java.io.*;

import server.Labels;

public class Game implements Labels {
	
	//ATTRIBUTES.

	protected Board theBoard; // the board.
	protected Referee theRef; // the referee.
	protected Player xPlayer; // the x-player.	
	protected Player oPlayer; // the o-player.

	
	
	//INITIALIZER METHODS.
	
	/**
	 * Creates a board.
	 * 
     * Creates a referee. There is no match without the referee.
     * The referee runs and oversees a match.
     * It sets up the matchup, and oversees the 
     * turn-by-turn gameplay by making the moves for 
     * the players as instructed by them.
	 * @throws IOException 
	 */
    public Game() throws IOException 
    {
        theBoard  = new Board();
        theRef = new Referee(this);
	}
      
    //initializes xPlayer and currentPlayer.
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
    
    //initializes oPlayer and currentPlayer.
    public void createOPlayer(String name, String playerType)
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
    
    //GETTERS and SETTERS.
    
    public Board getBoard()
    {
    	return theBoard;
    }
    
    public Referee getRef()
    {
    	return theRef;
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
