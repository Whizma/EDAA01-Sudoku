package sudokuSolver;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SudokuView {

	private SudokuSolver solver;
	private JPanel sudokuPanel;
	private JTextField[][] fields;

	public SudokuView(SudokuSolver s) {
		this.solver = s;
		this.fields = new JTextField[9][9];

		SwingUtilities.invokeLater(() -> createWindow("Sudoku", 900, 900));
	}

	private void createWindow(String title, int width, int height) {

		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container pane = frame.getContentPane();

		this.sudokuPanel = new JPanel();

		sudokuPanel.setLayout(new GridLayout(9, 9));
		
		buildBoard(true,true);
		
		JButton solveBtn = new JButton("Solve");
		JButton clearBtn = new JButton("Clear");
		
		solveBtn.addActionListener((e) -> {
			if(solver.solve()) {
				rebuildBoard();
				JOptionPane.showMessageDialog(pane,  "The sudoku has been solved");
			} else {
				JOptionPane.showMessageDialog(pane, "The sudoku could not be solved");
			}
		});

	}
	
	private void rebuildBoard() {
		buildBoard(false, false);
	}
	
	private void clearBoard() {
		buildBoard(false, true);
	}

	private void buildBoard(boolean initBuild, boolean clear) {
		if (clear) {
			solver.clear();
		}

		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int nbr = solver.get(r, c);
				String val = nbr > 0 ? String.valueOf(nbr) : "";
				JTextField field = new JTextField();
				
				if(initBuild) {
					setFieldAttributes(field, r, c);
					sudokuPanel.add(field);
					fields[r][c] = field;
				}
				fields[r][c].setText(val);
			}
		}
	}
	
	private void setFieldAttributes(JTextField field, int r, int c) {
		Color bgColor = squareBackground(r, c);
		field.setBackground(bgColor);
		
		field.setPreferredSize(new Dimension(50, 50));
		field.setHorizontalAlignment(JTextField.CENTER);
		field.addFocusListener(new FocusListener() {

			// Set background on hover
			@Override
			public void focusGained(FocusEvent e) {
				field.setBackground(SudokuColors.HOVER);

			}
			
			// Set value and revert background on blur 
			@Override
			public void focusLost(FocusEvent e) {
				field.setBackground(bgColor);

				String t = field.getText();

				// Catch-block catches both IllegalArgumentException from solver.setNumber
				// as well as NumberFormatException from Integer.parseInt.
				// If invalid, set the number back to 0.
				try {
					int nbr = Integer.parseInt(t);

					// Simple way to set the number back to 0 and hide it from the view
					if (nbr <= 0) {
						throw new IllegalArgumentException();
					}

					solver.add(r, c, nbr);

				} catch (Exception err) {
					solver.add(r, c, 0);
					field.setText("");
				}

			}

		});
	}
	
	private Color squareBackground(int row, int col) {
		int gridRow = (row/3) * 3;
		int gridCol = (col/3) * 3;
		
		if(gridRow % 2 == 0 && gridCol % 2 == 0 || (gridRow == 3 && gridCol == 3)) {
			return SudokuColors.ACCENT;
		}
		
		return Color.white;
	}

}
