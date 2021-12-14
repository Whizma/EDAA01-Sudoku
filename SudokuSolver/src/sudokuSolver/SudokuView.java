package sudokuSolver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class SudokuView {
	

	private SudokuSolver solver;
	private JPanel sudokuPanel;
	private JTextField[][] fields;

	/**
	 * Constructor, initializes basic attributes
	 * 
	 * @param s SudokuSolver object
	 */

	public SudokuView(SudokuSolver s) {
		this.solver = s;
		this.fields = new JTextField[9][9];

		SwingUtilities.invokeLater(() -> createWindow("Sudoko", 900, 900));
	}

	/**
	 * Creates the view and initalizes all helper methods
	 * 
	 * @param title  Title of the Window
	 * @param width  Width of the window
	 * @param height Height of the window
	 */

	private void createWindow(String title, int width, int height) {


		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container pane = frame.getContentPane();

		this.sudokuPanel = new JPanel();

		sudokuPanel.setLayout(new GridLayout(9, 9));

		this.buildBoard(true, false);

		JButton solveBtn = new JButton("Solve");
		JButton clearBtn = new JButton("Clear");

		/**
		 * Listen to when solve button is pressed
		 */
		solveBtn.addActionListener((e) -> {
			// If solved, rebuild the board
			if (solver.solve()) {
				this.rebuildBoard();
				JOptionPane.showMessageDialog(pane, "The sudoku has been solved");
			} else {
				JOptionPane.showMessageDialog(pane, "The sudoku could not be solved");
			}
		});

		/**
		 * Clear all values of board
		 */
		clearBtn.addActionListener(e -> {
			this.clearBoard();
		});

		// Add everything to view

		JPanel controlsPanel = new JPanel();

		controlsPanel.add(solveBtn, BorderLayout.EAST);
		controlsPanel.add(clearBtn, BorderLayout.EAST);

		pane.add(sudokuPanel, BorderLayout.NORTH);
		pane.add(controlsPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Helper method to rebuild board
	 * 
	 */

	private void rebuildBoard() {
		this.buildBoard(false, false);
	}

	/**
	 * Helper method to clear board
	 * 
	 */

	private void clearBoard() {
		this.buildBoard(false, true);
	}

	/**
	 * Builds or rebuilds the board with the new values
	 * 
	 * @param initialBuild Defines whether or not this is the first build
	 * @param clear        If true, all values will be set to 0
	 */

	private void buildBoard(boolean initialBuild, boolean clear) {
		if (clear) {
			this.solver.clear();
		}

		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int nbr = this.solver.get(r, c);
				String val = nbr > 0 ? String.valueOf(nbr) : "";
				JTextField field = new JTextField();

				// If first build, set attributes and add them to the panel
				if (initialBuild) {
					this.setFieldAttributes(field, r, c);
					this.sudokuPanel.add(field);
					fields[r][c] = field;
				}

				fields[r][c].setText(val);
			}
		}
	}

	/**
	 * Helper method to set correct attributes to JTextFields
	 * 
	 * @param field The field to update
	 * @param r     The row of the value for the field
	 * @param c     The column of the value for the field
	 */

	private void setFieldAttributes(JTextField field, int r, int c) {
		Color bgColor = this.squareBackground(r, c);

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

	/**
	 * Determines what the background of the JTextField should be
	 * 
	 * @param row  The row of the current JTextField
	 * @param col  The column the current JTextField
	 * @return The background Color
	 */

	private Color squareBackground(int row, int col) {
		int gridRow = (row / 3) * 3;
		int gridCol = (col / 3) * 3;

		if (gridRow % 2 == 0 && gridCol % 2 == 0 || (gridRow == 3 && gridCol == 3)) {
			return SudokuColors.ACCENT;
		}

		return Color.white;
	}
}