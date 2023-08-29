package generator;

import java.util.ArrayList;
import java.util.Collections;

public class Fieldfiller {

	public Fieldfiller() {
		UI ui=new UI();
		ui.setup(fill_field_with_numbers());
	}
	//makes a new Sudoku board
	Sudoku fill_field_with_numbers() {
		Sudoku sudoku = new Sudoku();
		start_seed(sudoku);
		solveBoard(sudoku);
		return sudoku;
	}
    //recursively fill board to get full sudoku after adding start seed
	private boolean solveBoard(Sudoku sudoku) {
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                if(sudoku.getNumber(row, column) == 0) {
                    for(int numberToTry = 1; numberToTry <= 9; numberToTry++) {
                        if(isValidPlacement(sudoku, numberToTry, row, column)) {
                            sudoku.setNumber(row, column, numberToTry);;
                            if(solveBoard(sudoku)) {
                                return true;
                            } else {
                                sudoku.setNumber(row, column, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    //checks all placement constrictions
    private boolean isValidPlacement(Sudoku sudoku, int number, int row, int column) {
        return !sudoku.line_contains_number(number, row) && !sudoku.column_contains_number(number, column) && !sudoku.box_contains_number(number, row, column);
    }
    //fill box top left, center and bottom right with 1-9 in random order
    //creates 3*9! starting options
	private void start_seed(Sudoku sudoku) {
		ArrayList<Integer> numbers=new ArrayList<Integer>();
		for (int i=1;i<10;i++) {
			numbers.add(i);
		}
		int j=0;
		while (j<9) {
			Collections.shuffle(numbers);
			sudoku.setNumber(0+j, 0+j, numbers.get(0));
			sudoku.setNumber(0+j, 1+j, numbers.get(1));
			sudoku.setNumber(0+j, 2+j, numbers.get(2));
			sudoku.setNumber(1+j, 0+j, numbers.get(3));
			sudoku.setNumber(1+j, 1+j, numbers.get(4));
			sudoku.setNumber(1+j, 2+j, numbers.get(5));
			sudoku.setNumber(2+j, 0+j, numbers.get(6));
			sudoku.setNumber(2+j, 1+j, numbers.get(7));
			sudoku.setNumber(2+j, 2+j, numbers.get(8));
			j=j+3;
		}
	}
}