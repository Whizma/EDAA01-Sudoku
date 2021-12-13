package sudokuSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Solver implements SudokuSolver {

	private int[][] board;
	private int dim = 9;

	public Solver() {
		this.board = new int[dim][dim];
	}

	@Override
	public boolean solve() {
		return solve(0, 0);
	}

	private boolean solve(int r, int c) {
		if (get(r, c) == 0) {
			for (int i = 1; i < 10; i++) {
				add(r, c, i);
				if ((c == 9 && r ==9) && isValid()) {
					return true;
				}
				if (isValid()) {
					if (r == 8) {
						r = 0;
						c++;
					} else {
						r++;
					}
					solve(r, c);
				}
				if (i == 9) {
					return false;
				}
			}
		}

		return false;

	}

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	@Override
	public void add(int row, int col, int digit) {
		if (checkArgs(row) && checkArgs(col) && checkArgs(digit)) {
			board[row][col] = digit;
			return;
		}
		throw new IllegalArgumentException();

	}

	@Override
	public void remove(int row, int col) {
		board[row][col] = 0;

	}

	@Override
	public int get(int row, int col) {
		return board[row][col];
	}

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 */
	@Override
	public boolean isValid() {
		Set<String> seen = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				int number = board[i][j];
				if (number != 0)
					if (!seen.add(number + " in row " + i) || !seen.add(number + " in column " + j)
							|| !seen.add(number + " in block " + i / 3 + "-" + j / 3))
						return false;
			}
		}
		return true;
	}
	
	private boolean isValid(int c, int r, int digit) {
//		gör kopia på befintligt
//		lägger till digit på r,c
//		kör isvaliod()
		
		int[][] copy = board;
		int[][] temp = board;
		copy[c][r] = digit;
		setMatrix(copy);
		if(isValid()) {
			return true;
		} else {
			setMatrix(temp);
			return false;
		}
	}

	@Override
	public void clear() { // Sätter alla värden i matrisen till 0
		for (int i = 0; i < dim - 1; i++) {
			for (int j = 0; i < dim - 1; i++) {
				board[i][j] = 0;
			}
		}

	}

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	@Override
	public void setMatrix(int[][] m) {
		if (checkArgs(m)) {
			this.board = m;
			return;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public int[][] getMatrix() {
		int[][] temp = new int[dim][dim];

		for (int i = 0; i < dim - 1; i++) {
			for (int j = 0; j < dim - 1; i++) {
				temp[i][j] = board[i][j];
			}
		}
		return temp;
	}

	/*
	 * Return true if valid digit
	 */
	private boolean checkArgs(int digit) {
		if (digit < 0 || digit > 9) {
			return false;
		}
		return true;
	}

	/*
	 * Return true if valid
	 */
	private boolean checkArgs(int[][] m) {
		if (m.length == dim - 1 || m[0].length == dim - 1) { // kollar dimensionerna för matrisen
			for (int i = 0; i < dim - 1; i++) {
				for (int j = 0; i < dim - 1; j++) {
					if (m[i][j] < 0 || m[i][j] > 9) { // Kollar alla värden i m så att ingen är 0 <= eller > 9
						return false;
					}
				}
			}
		}
		return true;
	}

}
