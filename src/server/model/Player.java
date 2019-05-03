package server.model;

import java.util.ArrayList;

public abstract class Player {
	
	//ATTRIBUTES.
	
	protected String name;
	protected Game theGame;
	protected Player opponent;
	protected char mark;
	
	//ABSTRACT METHODS.
	
	abstract public void play(int row, int col);
	abstract public ArrayList<Integer> play();
	abstract public void makeMove(int row, int col);
	abstract public ArrayList<Integer> makeMove();

	//CONSTRUCTOR.
	
	/**
	 * For subclasses to instantiate their objects.
	 * @param name the name of the player.
	 * @param letter the letter ("X" or "O") that the player will have.
	 */
	public Player(String name, char letter) 
	{
		this.name=name;
		this.mark=letter;
	}

	//SETTERS.
	
	public void setOpponent(Player opponent)
	{
		this.opponent=opponent;
	}

	public void setGame(Game theGame) 
	{
		this.theGame=theGame;
	}
	
	//GETTERS.
	
	public String getPlayerName()
	{
		return name; 
	}
	
	public String getPlayerMark()
	{
		String playerMark=String.valueOf(mark);
		return playerMark;
	}
	
	public Player getPlayer()
	{
		return this;
	}
	
	public Player getOpponent()
	{
		return this.opponent;
	}
}
