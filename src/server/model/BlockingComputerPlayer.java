package server.model;

import java.io.IOException;
import java.util.ArrayList;

import server.Labels;

public class BlockingComputerPlayer extends RandomComputerPlayer implements Labels{

	public BlockingComputerPlayer(String name, char mark)
	{
		super(name,mark);
	}

	//MEHTODS.

	/**
	 * Carries out a move for the player, provided the game has not been won or tied.
	 * This WILL NOT accept invalid inputs like cells that are beyond the scope of the gameboard and
	 * cells that have already been played.
	 * @throws NumberFormatException //
	 * @throws IOException //
	 */
	public ArrayList<Integer> play() 
	{
		ArrayList<Integer> cellPlayed=new ArrayList<Integer>(2);
		
		if (theGame.theBoard.xWins()==false && 
				theGame.theBoard.oWins()==false && 
				theGame.theBoard.isFull()==false)
		{
			for (int row = 0; row < 3; row++)
			{
				for (int col = 0; col < 3; col++)
				{
					if(this.testCellForBlocking(row,col)==true)
					{
						theGame.theBoard.addMark(row,col, mark);
						
						cellPlayed.add(row);
						cellPlayed.add(col);

						return cellPlayed;
					}
				}
			}
			cellPlayed=super.makeMove(); //won't run if the label thePlay is cut short by the break statement.
		}
		
		return cellPlayed;
	}

	//HELPER METHODS.

	public boolean testCellForBlocking(int row, int col)
	{
		if (theGame.theBoard.isCellPlayed(row, col)==false)
		{
			opponent.theGame.theBoard.addMark(row, col, opponent.mark);
			opponent.theGame.theBoard.setMarkCount(opponent.theGame.theBoard.getMarkCount()-1);
			if (opponent.theGame.theBoard.xWins()==true || opponent.theGame.theBoard.oWins()==true)
			{
				opponent.theGame.theBoard.addMark(row, col, SPACE_CHAR);
				opponent.theGame.theBoard.setMarkCount(opponent.theGame.theBoard.getMarkCount()-1);
				return true;
			}
			else
				opponent.theGame.theBoard.addMark(row, col, SPACE_CHAR);
			opponent.theGame.theBoard.setMarkCount(opponent.theGame.theBoard.getMarkCount()-1);
			return false;
		}
		else
			return false;
	}
}
