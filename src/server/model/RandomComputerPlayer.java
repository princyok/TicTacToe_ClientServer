package server.model;

import java.util.ArrayList;

public class RandomComputerPlayer extends Player{
	
	//CONSTRUCTOR.
	
	public RandomComputerPlayer(String name, char mark) 
	{
		super(name,mark);
	}
	
	//MEHTODS.

	/**
	 * Carries out a move for the player, provided the game has not been won or tied.
	 * This WILL NOT accept invalid inputs like cells that are beyond the scope of the gameboard and
	 * cells that have already been played.
	 */
	public ArrayList<Integer> play()
	{
		ArrayList<Integer> cellPlayed= new ArrayList<Integer>(2);
		if (theGame.theBoard.xWins()==false && 
			theGame.theBoard.oWins()==false && 
			theGame.theBoard.isFull()==false)
		{
			cellPlayed = this.makeMove();
		}
		
		return cellPlayed;
	}
	
	//HELPER METHODS.
	/**
	 * Makes a move for the player. Does not check whether the game has been won or not.
	 * This WILL NOT accept invalid inputs like cells that are beyond the scope of the gameboard and
	 * cells that have already been played.
	 */
	public ArrayList<Integer> makeMove()
	{	
		int row=-1, col=-1; //initialize with values that would be impossible (a cell outside the board).
		
		
		while (theGame.theBoard.isCellPlayed(row, col)==false)
		{	
			RandomGenerator r = new RandomGenerator();
			row=r.generateRandNumber(0,2);
			col=r.generateRandNumber(0,2);
		
			if (theGame.theBoard.isCellPlayed(row, col)==false)
			{
				theGame.theBoard.addMark(row, col, mark);
			}
			else
			{
				row=-1; col=-1; //resetting to a cell outside the board.
			}
		}
		
		ArrayList<Integer> cellPlayed = new ArrayList<Integer>(2);
		cellPlayed.add(row);
		cellPlayed.add(col);
		
		return cellPlayed;
	}

	@Override
	public void makeMove(int row, int col) {}

	@Override
	public void play(int row, int col) {}
}
