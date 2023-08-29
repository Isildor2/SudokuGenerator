package generator;

public class Sudoku{
	/*
	 * sudoku_board saves all cells of the game
	 * 
	 * difficulty is 0.00 to 1.00 for hardest
	 * 
	 * TO DO
	 * title can be custom set for specific games
	 * 
	 * time is saved in seconds or maybe 1/10 or 1/100 of seconds
	 * 
	 * Game can be saved
	 * 
	 * Game can be loaded
	 */
	private int[][] sudoku_board = new int[9][9];
	private double difficulty;
	private String title;
	private int time;
	
	public Sudoku() {
		for (int i=0;i<9;i++) {
			for (int ii=0;ii<9;ii++) {
				this.sudoku_board[i][ii]=0;
			}
		}
	}
	public boolean line_contains_number(int number, int line) {
		for (int i=0;i<9;i++) {
			if (this.sudoku_board[line][i]==number) {
				return true;
			}
		}
		return false;
	}
	public boolean column_contains_number(int number, int column) {
		for (int i=0;i<9;i++) {
			if (this.sudoku_board[i][column]==number) {
				return true;
			}
		}
		return false;
	}
	public boolean box_contains_number(int number, int line, int column) {
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
				if (sudoku_board[line+i][column+j]==number) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean is_board_full() {
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				if (sudoku_board[i][j]==0) {
					return false;
				}
			}
		}
		return true;
	}
	public void setNumber(int line, int column, int number) {
		this.sudoku_board[line][column]=number;
	}
	public int getNumber(int line, int column) {
		return this.sudoku_board[line][column];
	}
	public double getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}