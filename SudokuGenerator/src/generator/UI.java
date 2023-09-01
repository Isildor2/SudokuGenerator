package generator;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI {
	private Color lockedCellGray=new Color(220,220,220);
	private boolean backgroundGray=true;

	public void setup(Sudoku sudoku, JLabel[] optionlabels, JLabel[][] cells, JFrame frame, JPanel contents) {
		contents.setBackground(Color.WHITE);
		contents.setOpaque(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(contents);
		render(sudoku,cells,0);
		frame.pack();
		frame.setVisible(true);
	}
	public void render(Sudoku sudoku, JLabel[][] cells,int currentlyClicked) {
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				if (sudoku.getNumber(i, j)!=0) {
					cells[i][j].setText(""+sudoku.getNumber(i, j));
				} else {
					cells[i][j].setText("");
				}
				if ((i*9)+j==currentlyClicked) {
					cells[i][j].setBackground(Color.YELLOW);
				} else {
					if (backgroundGray&sudoku.isNumberLocked((i*9+j))) {
						cells[i][j].setBackground(lockedCellGray);
					} else {
						cells[i][j].setBackground(Color.WHITE);
					}
				}
				cells[i][j].repaint();
			}
		}
	}
	public void invertBackgroundGray() {
		backgroundGray=!backgroundGray;
	}
}