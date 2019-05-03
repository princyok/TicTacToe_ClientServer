package server.model;

import server.Labels;

public class Board implements Labels 
{
	//ATTRIBUTES.
	
	protected char [][] theBoardRep; //representative framework of the board: 2D array of chars.
	protected int markCount; //number of cells that have been played.
	
	//CTORS and INITIALIZER METHODS.
	
	/**
	 * Instantiates a Board object.
	 */
	public Board() 
	{
		markCount = 0;
		
		theBoardRep = new char[3][0];
		for (int i = 0; i < 3; i++) 
		{
			theBoardRep[i] = new char[3];
			for (int j = 0; j < 3; j++)
			{
				theBoardRep[i][j] = SPACE_CHAR;
			}
		}
	}
	
	//OPERATIONAL METHODS.
	
	public char getMark(int row, int col) 
	{
		return theBoardRep[row][col];
	}

	/**
	 * Checks whether the board is full, i.e. all the cells in the board have been played.
	 * @return a boolean answer of whether the board is full or not.
	 */
	public boolean isFull() 
	{
		boolean isFull = (markCount >= 9);
		return isFull;
	}

	/**
	 * Checks whether the xPlayer has won the game or not.
	 * @return a boolean answer of whether the xPlayer has won.
	 */
	public boolean xWins() 
	{
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Checks whether the oPlayer has won the game or not.
	 * @return a boolean answer of whether the oPlayer has won.
	 */
	public boolean oWins() 
	{
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * add a mark ("X" or "O") to the board. Does not check whether a mark has already been added.
	 * @param row row index of the cell the mark will be added to.
	 * @param col column index of the cell the mark will be added to.
	 * @param mark the mark to be added ("X" or "O").
	 */
	public void addMark(int row, int col, char mark) 
	{
			theBoardRep[row][col] = mark;
			markCount++;
	}
	
	/**
	 * Checks whether a cell has been played or not. 
	 * If the cell passed as argument is beyond the scope of the board,
	 * it will be recognized as not been played.
	 * @param row row index of the cell to be checked.
	 * @param col column index of the cell to be checked.
	 * @return a boolean answer of whether the cell has been played.
	 */
	public boolean isCellPlayed(int row, int col)
	{
		if(row>2||row<0)
		{
			return false;
		}
		else if (this.getMark(row, col)==LETTER_X ||
				this.getMark(row, col)==LETTER_O)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * clears the board.
	 */
	public void clear() 
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				theBoardRep[i][j] = SPACE_CHAR;
			}
		}
		markCount = 0;
	}

	//HELPER METHODS.
	
	/**
	 * Checks which letter is the winner.
	 * @param mark letter to be checked.
	 * @return an integer that represents the answer: 
	 * 1 signifies that the checked letter is the winner, 
	 * and 0 signifies they are not the winner.
	 */
	private int checkWinner(char mark) 
	{
		//Four ways to win: a whole column, a whole row, left-hand diagonal, right-hand diagonal.
		int row, col;
		int result = 0;
		
		//whole column.
		for (row = 0; result == 0 && row < 3; row++) 
		{
			int rowResult = 1;
			for (col = 0; rowResult == 1 && col < 3; col++)
			{
				if (theBoardRep[row][col] != mark)
				{
					rowResult = 0;
				}
			}
			if (rowResult != 0)
			{
				result = 1;
			}
		}
		
		//whole row.
		for (col = 0; result == 0 && col < 3; col++) 
		{
			int colResult = 1;
			for (row = 0; colResult != 0 && row < 3; row++)
			{
				if (theBoardRep[row][col] != mark)
				{
					colResult = 0;
				}
			}
			if (colResult != 0)
			{
				result = 1;
			}
		}

		//right-hand diagonal.
		if (result == 0) 
		{
			int rhDiagResult = 1;
			for (row = 0; rhDiagResult != 0 && row < 3; row++)
			{
				col=row;
				if (theBoardRep[row][col] != mark)
				{
					rhDiagResult = 0;
				}
			}
			if (rhDiagResult != 0)
			{
				result = 1;
			}
		}
		
		//left-hand diagonal.
		if (result == 0) 
		{
			int lhDiagResult = 1;
			for (row = 0; lhDiagResult != 0 && row < 3; row++)
			{
				col=2-row;
				if (theBoardRep[row][col] != mark)
				{
					lhDiagResult = 0;
				}
			}
			if (lhDiagResult != 0)
			{
				result = 1;
			}
		}
		return result;
	}

	//GETTERS and SETTERS.
	
	public int getMarkCount() 
	{
		return markCount;
	}

	public void setMarkCount(int markCount) 
	{
		this.markCount = markCount;
	}
}
