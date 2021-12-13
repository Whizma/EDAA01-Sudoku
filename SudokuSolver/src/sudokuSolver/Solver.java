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
		if(r == 9) {
			return true;
		}
		int newRow = 0;
		int newCol = 0;
		
		if(c < 8) {
			newCol = c +1;
			newRow = r;
		} else {
			newCol = 0;
			newRow = r+1;
		}
		
		if(get(r, c) == 0) {
			for(int i = 1; i < 10; i++) {
				if(isValid(r, c, i)) {
					add(r, c, i);
					
					if(solve(newRow, newCol)) {
						return true;
					} else {
						add(r, c, 0);
					}
				}
				
				
			}
			return false;
			
		}
		
		return isValid(r, c, get(r,c)) && solve(newRow, newCol);
		
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
		checkArgs(row);
		checkArgs(col);
		checkArgs(digit);
		board[row][col] = digit;

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
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				if(isValid(r, c, get(r, c))) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isValid(int c, int r, int digit) {
		checkArgs(c);
		checkArgs(r);
		checkArgs(digit);
		int temp = get(r, c);
		add(r, c, 0);
		
		boolean res = checkRow(r, digit) && checkColumn(c, digit) && checkGrid(r, c ,digit);
		add(r, c, digit);
		
		return res;
	}

	private boolean checkRow(int r, int digit) {
		for (int n : board[r]) {
			if (digit == n) {
				return false;
			}
		}
		return true;
	}

	private boolean checkColumn(int c, int digit) {
		for (int i = 0; i < 9; i++) {
			if (board[i][c] == digit) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkGrid(int c, int r, int digit) {
		
		int startRow = (r/3) * 3;
		int startCol = (r/3) * 3;
		
		for(int i = startRow; i < startRow +3; i++) {
			for(int j = startCol; j < startCol+3; j++) {
				if(board[i][j] == digit) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void clear() { // S�tter alla v�rden i matrisen till 0
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
		checkArgs(m);
		board = m;

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
	private void checkArgs(int digit) {
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException();
		}
	}

	/*
	 * Return true if valid
	 */
	private void checkArgs(int[][] m) {
		if (m.length == dim - 1 || m[0].length == dim - 1) { // kollar dimensionerna f�r matrisen
			for (int i = 0; i < dim - 1; i++) {
				for (int j = 0; i < dim - 1; j++) {
					if (m[i][j] < 0 || m[i][j] > 9) { // Kollar alla v�rden i m s� att ingen �r 0 <= eller > 9
						throw new IllegalArgumentException();
					}
				}
			}
		}

	}

}
