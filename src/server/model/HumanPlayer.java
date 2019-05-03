package server.model;
import java.io.IOException;
import java.util.ArrayList;

public class HumanPlayer extends Player {
	
	//CONSTRUCTOR.
	
	/**
	 * Instantiates an object of type Player.
	 * @param name the name of the player.
	 * @param letter the letter ("X" or "O") that the player will player
	 */
	public HumanPlayer(String name, char letter) 
	{
		
		super(name,letter);
	}
	
	//METHODS.
	
	/**
	 * Carries out a move for the player, provided the game has not been won or tied.
	 * This will not accept invalid inputs like cells that are beyond the scope of the gameboard and
	 * cells that have already been played.
	 * @throws NumberFormatException //
	 * @throws IOException //
	 */
	@Override
	public void play(int row, int col)
	{

		if (theGame.theBoard.xWins()==false && 
			theGame.theBoard.oWins()==false && 
			theGame.theBoard.isFull()==false)
		{
			this.makeMove(row, col);
		}
	}

	@Override
	public ArrayList<Integer> play() {return null;}

	//HELPER METHODS.
	/**
	 * Makes a move for the player. Does not check whether the game has been won or not.
	 * This will not accept invalid inputs like cells that are beyond the scope of the gameboard and
	 * cells that have already been played.
	 */
	@Override
	public void makeMove(int row, int col)
	{	
		if (theGame.theBoard.isCellPlayed(row, col)==false)
		{
			theGame.theBoard.addMark(row, col, mark);
		}
	}

	@Override
	public ArrayList<Integer> makeMove() {return null;}
}