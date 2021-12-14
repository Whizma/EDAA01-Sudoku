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

	public SudokuView(SudokuSolver s) {
		this.solver = s;
		this.fields = new JTextField[9][9];

		SwingUtilities.invokeLater(() -> createWindow("Sudoko", 900, 900));
	}

	/**
	 * Skapar vyn och alla hjälpmetoder
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
		 * Lyssnar efter knapptryck
		 */
		solveBtn.addActionListener((e) -> {
			// Om lösning, visa den
			if (solver.solve()) {
				this.rebuildBoard();
				JOptionPane.showMessageDialog(pane, "The sudoku has been solved");
			} else {
				JOptionPane.showMessageDialog(pane, "The sudoku could not be solved");
			}
		});

		/**
		 * Rensar boardet på värden
		 */
		clearBtn.addActionListener(e -> {
			this.clearBoard();
		});

		// Lägg till allt i vyn

		JPanel controlsPanel = new JPanel();

		controlsPanel.add(solveBtn, BorderLayout.EAST);
		controlsPanel.add(clearBtn, BorderLayout.EAST);

		pane.add(sudokuPanel, BorderLayout.NORTH);
		pane.add(controlsPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Hjälpmetod för att bygga om brädet
	 */

	private void rebuildBoard() {
		this.buildBoard(false, false);
	}

	/**
	 * Hjälpmetod för att rensa brädet
	 */

	private void clearBoard() {
		this.buildBoard(false, true);
	}

	/**
	 * Bygger eller bygger om brädet med nya värden
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
	 * Hjälpmetod för att sätta rätt värden till attributen i JTextfields
	 */

	private void setFieldAttributes(JTextField field, int r, int c) {
		Color bgColor = this.squareBackground(r, c);

		field.setBackground(bgColor);

		field.setPreferredSize(new Dimension(50, 50));
		field.setHorizontalAlignment(JTextField.CENTER);

		field.addFocusListener(new FocusListener() {

			// Färg vid hover
			@Override
			public void focusGained(FocusEvent e) {
				field.setBackground(SudokuColors.HOVER);

			}

			// Sätt värdet och ändra tillbaka färgen
			@Override
			public void focusLost(FocusEvent e) {
				field.setBackground(bgColor);

				String t = field.getText();

				// Catchblocket fångar IllegalArgumentException från solver.add och
				// NumberFormatException från Integer.parseInt.
				// Om invalid, ändra tillbaka till 0
				try {
					int nbr = Integer.parseInt(t);

					// Ändrar tillbaka värdet till 0 och gömmer det för användaren
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
	 * Bakgrundsfärger för GUI
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