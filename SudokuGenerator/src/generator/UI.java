package generator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI {
	private JFrame frame=new JFrame("Sudoku");
	private JPanel panel = new JPanel();
	private ImageIcon[] icons=new ImageIcon[10];
	private JLabel[][] cells=new JLabel[9][9];
	private int currently_clicked;
	private boolean key_held_down=false;
	
	void setup(Sudoku sudoku) {
		frame.setLocation(0,0);
		frame.setSize(820,830);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		make_labels_and_listeners(sudoku);
		createImageLabelPanel(sudoku);
		render(sudoku);
		frame.add(panel);
		frame.setVisible(true);
	}
	private void render(Sudoku sudoku) {
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				cells[i][j].setIcon(icons[sudoku.getNumber(i, j)]);
			}
		}
	}
	private void createImageLabelPanel(Sudoku sudoku) {
		for (int i=0;i<10;i++) {
			icons[i]=new ImageIcon(getClass().getResource(i+".png"));
		}
		GridLayout lay=new GridLayout(3, 3);
		lay.setHgap(5);
		lay.setVgap(5);
		panel.setBackground(Color.BLACK);
		panel.setOpaque(true);
		panel.setLayout(lay);
		for (int x_offset=0;x_offset<9;x_offset=x_offset+3) {
			for (int j_offset=0;j_offset<9;j_offset=j_offset+3) {
				JPanel box=new JPanel();
				GridLayout grid=new GridLayout(3,3);
				box.setLayout(grid);
				box.add(cells[0+x_offset][j_offset]);
				box.add(cells[1+x_offset][j_offset]);
				box.add(cells[2+x_offset][j_offset]);
				box.add(cells[0+x_offset][1+j_offset]);
				box.add(cells[1+x_offset][1+j_offset]);
				box.add(cells[2+x_offset][1+j_offset]);
				box.add(cells[0+x_offset][2+j_offset]);
				box.add(cells[1+x_offset][2+j_offset]);
				box.add(cells[2+x_offset][2+j_offset]);
				panel.add(box);
			}
		}
    }
	private void make_labels_and_listeners(Sudoku sudoku) {
		for (int line = 0; line < 9; line++) {
            for (int column = 0; column < 9; column++) {
                JLabel label = new JLabel(icons[0],JLabel.CENTER);
                label.setSize(80, 80);
                label.setOpaque(false);
                label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                cells[line][column]=label;
                label.addMouseListener(new MouseAdapter() {
                	@Override
                	public void mouseClicked(MouseEvent click) {
                		for (int i=0;i<9;i++) {
                			for (int j=0;j<9;j++) {
	                			if (cells[i][j]==click.getSource()) {
	                				currently_clicked=(i*9)+j;
	                			}
                			}
                		}
                	}
                });
                frame.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (key_held_down==false) {
							sudoku.setNumber(currently_clicked/9, currently_clicked%9, place_number(sudoku,e));
							cells[currently_clicked/9][currently_clicked%9].setIcon(icons[sudoku.getNumber(currently_clicked/9, currently_clicked%9)]);
							if (sudoku.is_board_full()) {
								System.out.println("you did it!");
							}
							key_held_down=true;
							return;
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
	//set number methods might be redundant
	private int place_number(Sudoku sudoku, KeyEvent press) {
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
		case KeyEvent.VK_DELETE:
			return 0;
		default:
			return sudoku.getNumber(currently_clicked/9, currently_clicked%9);
		}
	}
}