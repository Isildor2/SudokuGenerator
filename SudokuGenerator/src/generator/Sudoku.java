package generator;

import java.util.ArrayList;

public class Sudoku{
	
	private int[][] sudokuBoard = new int[9][9];
	private int[][] solution =new int[9][9];
	private ArrayList<Integer> lockedCells=new ArrayList<Integer>();
	
	public boolean lineContainsNumber(int number, int line) {
		for (int i=0;i<9;i++) {
			if (this.sudokuBoard[line][i]==number) {
				return true;
			}
		}
		return false;
	}
	public boolean columnContainsNumber(int number, int column) {
		for (int i=0;i<9;i++) {
			if (this.sudokuBoard[i][column]==number) {
				return true;
			}
		}
		return false;
	}
	public boolean boxContainsNumber(int number, int line, int column) {
		if (line<3) {
			line=0;
		} else if (line<6&line>2) {
			line=3;
		} else {
			line=6;
		}
		if (column<3) {
			column=0;
		} else if (column<6&column>2) {
			column=3;
		} else {
			column=6;
		}
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				if (sudokuBoard[line+i][column+j]==number) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean isBoardFull() {
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				if (sudokuBoard[i][j]==0) {
					return false;
				}
			}
		}
		return true;
	}
	public void addLockedNumber(int cell) {
		this.lockedCells.add(cell);
	}
	public boolean isNumberLocked(int cell) {
		if (lockedCells.contains(cell)) {
			return true;
		}
		return false;
	}
	public void clear() {
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				if (sudokuBoard[i][j]!=0&!isNumberLocked(i*9+j)) {
					sudokuBoard[i][j]=0;
				}
			}
		}
	}
	public void fillSolution() {
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				solution[i][j]=sudokuBoard[i][j];
			}
		}
	}
	public void fillBoard() {
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				sudokuBoard[i][j]=solution[i][j];
			}
		}
	}
	public int getCellSolution(int x,int y) {
		return this.solution[x][y];
	}
	public void setNumber(int line, int column, int number) {
		this.sudokuBoard[line][column]=number;
	}
	public int getNumber(int line, int column) {
		return this.sudokuBoard[line][column];
	}
}