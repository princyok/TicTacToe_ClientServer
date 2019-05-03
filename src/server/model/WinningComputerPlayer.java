package server.model;

import java.util.ArrayList;

public class WinningComputerPlayer extends BlockingComputerPlayer
{
	public WinningComputerPlayer(String name, char mark)
	{
		super(name,mark);
	}

	public ArrayList<Integer> play()
	{
		ArrayList<Integer> cellPlayed=new ArrayList<Integer>(2);

		if(theGame.theBoard.getMarkCount()<=1)
		{
			if(!theGame.theBoard.isCellPlayed(1,1)) //if the center cell is not yet played.
			{
				cellPlayed.add(1);
				cellPlayed.add(1);
				return cellPlayed;
			}
			else 
			{
				//play a corner cell.
				cellPlayed = playAnyCorner();
			}
		}
		else 
		{

			if (theGame.theBoard.xWins()==false && 
					theGame.theBoard.oWins()==false && 
					theGame.theBoard.isFull()==false)
			{
				for (int row = 0; row < 3; row++)
				{
					for (int col = 0; col < 3; col++)
					{
						if(this.testCellForWinning(row,col)==true)
						{
							theGame.theBoard.addMark(row,col, mark);
							cellPlayed.add(row);
							cellPlayed.add(col);

							return cellPlayed;
						}
					}
				}
				cellPlayed=super.play(); //won't run if the label thePlay is broken by the break statement.
			}
		}
		return cellPlayed;
	}
	private boolean testCellForWinning(int row, int col)
	{
		if (!theGame.theBoard.isCellPlayed(row, col))
		{
			theGame.theBoard.addMark(row, col, mark);
			theGame.theBoard.setMarkCount(opponent.theGame.theBoard.getMarkCount()-1);

			if (theGame.theBoard.xWins()==true || theGame.theBoard.oWins()==true)
			{
				theGame.theBoard.addMark(row, col, SPACE_CHAR);
				theGame.theBoard.setMarkCount(opponent.theGame.theBoard.getMarkCount()-1);
				return true;
			}
			else
				theGame.theBoard.addMark(row, col, SPACE_CHAR);
			theGame.theBoard.setMarkCount(opponent.theGame.theBoard.getMarkCount()-1);
			return false;
		}
		else
			return false;
	}

	private ArrayList<Integer> playAnyCorner()
	{
		ArrayList<Integer> cellPlayed=new ArrayList<Integer>(2);

		int [] corner = {0, 2};
		for(int row : corner)
		{
			for (int col: corner)
			{
				if (!theGame.theBoard.isCellPlayed(row,col))
				{
					theGame.theBoard.addMark(row,col, mark);
					cellPlayed.add(row);
					cellPlayed.add(col);
				}
			}
		}
		
		return cellPlayed;
	}
}
