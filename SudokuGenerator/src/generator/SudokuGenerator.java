package generator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SudokuGenerator {
	private FieldFiller fieldfill=new FieldFiller();
	private Sudoku sudoku;
	private long time=System.currentTimeMillis();
	
	private JLabel[] optionlabels=new JLabel[7];
	private JLabel[][] cells=new JLabel[9][9];
	private JPanel contents = new JPanel();
	private JFrame frame=new JFrame("Sudoku");
	private int currentlyClicked;
	private boolean key_held_down;
	
	public static void main(String[] args) {
		SudokuGenerator sudokuGen=new SudokuGenerator();
		sudokuGen.start();
	}
	private void start() {
		sudoku=fieldfill.makeSudoku(0.55);
		UI ui=new UI();
		ui.setup(sudoku, optionlabels, cells, frame, contents);
		createLabelsAndListeners(ui);
		createImageLabelPanel();
		createOptionLabels(ui);
		createOptionsPanel(optionlabels);
	}
	private void createLabelsAndListeners(UI ui) {
		Font labelFont=new Font("Dialog", Font.BOLD, 40);
		for (int line = 0; line < 9; line++) {
            for (int column = 0; column < 9; column++) {
                JLabel label = new JLabel("",JLabel.CENTER);
                label.setPreferredSize(new Dimension(80, 80));
                label.setOpaque(true);
                label.setFont(labelFont);
                label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                if (sudoku.isNumberLocked(line*9+column)) {
                	label.setBackground(new Color(220,220,220));
                } else {
                	label.setBackground(Color.WHITE);
                }
                cells[line][column]=label;
               
                label.addMouseListener(new MouseAdapter() {
                	@Override
                	public void mouseClicked(MouseEvent click) {
                		for (int i=0;i<9;i++) {
                			for (int j=0;j<9;j++) {
	                			if (cells[i][j]==click.getSource()) {
	                				currentlyClicked=(i*9)+j;
	                				ui.render(sudoku, cells,currentlyClicked);
	                			}
                			}
                		}
                	}
                });
                
                frame.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (key_held_down==false&&!sudoku.isNumberLocked(currentlyClicked)) {
							sudoku.setNumber(currentlyClicked/9, currentlyClicked%9, placeNumber(sudoku,e));
							ui.render(sudoku,cells,currentlyClicked);
							if (sudoku.isBoardFull()) {
								finished();
							}
							key_held_down=true;
						} else {
							return;
						}
					}
					@Override
					public void keyReleased(KeyEvent e) {
						key_held_down=false;
					}        
					@Override
					public void keyTyped(KeyEvent e) {}
                });
            }
        }
	}
	private void createImageLabelPanel() {
		JPanel sudokuBoard=new JPanel(new GridLayout(3,3,5,5));
		sudokuBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
		sudokuBoard.setBackground(Color.BLACK);
		sudokuBoard.setOpaque(true);
		for (int x=0;x<3;x++) {
			for (int y=0;y<3;y++) {
				JPanel box=new JPanel(new GridLayout(3,3,3,3));
				box.setBackground(new Color(102,102,102));
				box.add(cells[0+x*3][0+y*3]);
				box.add(cells[0+x*3][1+y*3]);
				box.add(cells[0+x*3][2+y*3]);
				box.add(cells[1+x*3][0+y*3]);
				box.add(cells[1+x*3][1+y*3]);
				box.add(cells[1+x*3][2+y*3]);
				box.add(cells[2+x*3][0+y*3]);
				box.add(cells[2+x*3][1+y*3]);
				box.add(cells[2+x*3][2+y*3]);
				sudokuBoard.add(box);
			}
		}
		contents.add(sudokuBoard);
    }
	private void createOptionsPanel(JLabel optionlabels[]) {
		JPanel options=new JPanel(new GridLayout(7,1,0,9));
		options.setOpaque(false);
		for (int i=0;i<optionlabels.length;i++) {
			options.add(optionlabels[i]);
		}
		contents.add(options);
	}
	private void createOptionLabels(UI ui) {
		JLabel difficulty=new JLabel("Easy");
		setOptionLabelCharacteristics(difficulty);
		difficulty.setToolTipText("Click to change difficulty");
		difficulty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				if (difficulty.getText()=="Easy") {
					difficulty.setText("Medium");
				} else if (difficulty.getText()=="Medium") {
					difficulty.setText("Hard");
				} else if (difficulty.getText()=="Hard") {
					difficulty.setText("Expert");
				} else if (difficulty.getText()=="Expert") {
					difficulty.setText("Demon");
				} else if (difficulty.getText()=="Demon") {
					difficulty.setText("Easy");
				}
				if (difficulty.getText()!="Demon") {
					difficulty.setToolTipText("Click to change difficulty");
				} else {
					difficulty.setToolTipText("Current difficulty: Demon DO NOT RECOMMEND!");
				}
			}
		});
		optionlabels[1]=difficulty;	
		
		JLabel generate=new JLabel("New");
		setOptionLabelCharacteristics(generate);
		generate.setToolTipText("Generate a new sudoku at the chosen difficulty");
		generate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				sudoku=fieldfill.makeSudoku(getDifficultyFromLabel(difficulty.getText()));
				time=System.currentTimeMillis();
				ui.render(sudoku, cells,currentlyClicked);
			}
		});
		optionlabels[0]=generate;
		
		JLabel reset=new JLabel("Reset");
		setOptionLabelCharacteristics(reset);
		reset.setToolTipText("Clear all manually placed numbers");
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				sudoku.clear();
				time=System.currentTimeMillis();
				ui.render(sudoku,cells,currentlyClicked);
			}
		});
		optionlabels[2]=reset;

		JLabel toggle_background=new JLabel("<html>Toggle gray<br/>background</html>");
		setOptionLabelCharacteristics(toggle_background);
		toggle_background.setFont(new Font("SansSerif", Font.BOLD, 23));
		toggle_background.setToolTipText("Click to remove/add gray background behind numbers");
		toggle_background.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				ui.invertBackgroundGray();
				ui.render(sudoku, cells,currentlyClicked);
			}
		});
		optionlabels[3]=toggle_background;

		JLabel fillField=new JLabel("Fill field");
		setOptionLabelCharacteristics(fillField);
		fillField.setFont(new Font("SansSerif", Font.BOLD, 30));
		fillField.setToolTipText("Fill all cells with the correct numbers");
		fillField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				sudoku.fillBoard();
				ui.render(sudoku, cells,currentlyClicked);
			}
		});
		optionlabels[4]=fillField;
		
		JLabel hint=new JLabel("Hint");
		setOptionLabelCharacteristics(hint);
		hint.setFont(new Font("SansSerif", Font.BOLD, 30));
		hint.setToolTipText("Fill targeted cell with the correct number");
		hint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				sudoku.setNumber(currentlyClicked/9, currentlyClicked%9, sudoku.getCellSolution(currentlyClicked/9,currentlyClicked%9));
				ui.render(sudoku, cells,currentlyClicked);
			}
		});
		optionlabels[5]=hint;
		
		JLabel errorCheck=new JLabel("<html>Check<br/>entries</html>");
		setOptionLabelCharacteristics(errorCheck);
		errorCheck.setFont(new Font("SansSerif", Font.BOLD, 30));
		errorCheck.setToolTipText("Checks the board for mistakes");
		errorCheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				for (int i=0;i<9;i++) {
					for (int j=0;j<9;j++) {
						if (sudoku.getNumber(i, j)!=0&sudoku.getNumber(i, j)!=sudoku.getCellSolution(i, j)) {
							sudoku.setNumber(i, j, 0);
						}
					}
				}
				ui.render(sudoku, cells,currentlyClicked);
			}
		});
		optionlabels[6]=errorCheck;
	}
	private void setOptionLabelCharacteristics(JLabel label) {
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(160,100));
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		Font font = new Font("SansSerif", Font.BOLD, 30);
		label.setFont(font);
	}
	private double getDifficultyFromLabel(String difficulty) {
		Random difficultySetting=new Random();
		if (difficulty=="Demon") {
			return difficultySetting.nextDouble(0.80, 0.90);
		} else if (difficulty=="Expert") {
			return difficultySetting.nextDouble(0.70, 0.80);
		} else if (difficulty=="Hard") {
			return difficultySetting.nextDouble(0.60, 0.70);
		} else if (difficulty=="Medium") {
			return difficultySetting.nextDouble(0.50, 0.60);
		} else {
			return difficultySetting.nextDouble(0.40, 0.50);
		}
	}
	private void finished() {
		JFrame success=new JFrame();
		JLabel label=new JLabel("Finished "+optionlabels[1].getText().toLowerCase()+" sudoku in "+(System.currentTimeMillis()-time)/1000+"s");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		label.setFont(new Font("SansSerif", Font.ITALIC, 40));
		label.setPreferredSize(new Dimension(frame.getWidth(), 150));
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		success.add(label);
		success.pack();
		success.setLocation(frame.getLocationOnScreen());
		success.setVisible(true);
	}
	private int placeNumber(Sudoku sudoku, KeyEvent press) {
		switch(press.getKeyCode()) {
		case KeyEvent.VK_1:
			return 1;
		case KeyEvent.VK_2:
			return 2;
		case KeyEvent.VK_3:
			return 3;
		case KeyEvent.VK_4:
			return 4;
		case KeyEvent.VK_5:
			return 5;
		case KeyEvent.VK_6:
			return 6;
		case KeyEvent.VK_7:
			return 7;
		case KeyEvent.VK_8:
			return 8;
		case KeyEvent.VK_9:
			return 9;
		case KeyEvent.VK_BACK_SPACE:
			return 0;
		default:
			return sudoku.getNumber(currentlyClicked/9, currentlyClicked%9);
		}
	}
}