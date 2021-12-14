package sudokuSolver;

public class Solver implements SudokuSolver {

	private int[][] board;

	public Solver() {
		board = new int[9][9];
	}

	@Override
	public void add(int r, int c, int nbr) {
		checkArgs(r, c, nbr);

		this.board[r][c] = nbr;

	}

	@Override
	public int get(int r, int c) {
		checkArgs(r, c);

		return this.board[r][c];
	}

	@Override
	public void remove(int r, int c) {
		checkArgs(r, c);

		this.board[r][c] = 0;

	}

	private boolean isValid(int r, int c, int nbr) {
		checkArgs(r, c, nbr);

		// Temporarily remove value so it doesn't clash in checks
		int v = this.board[r][c];
		this.board[r][c] = 0;

		// Check if nbr is valid in all ways
		boolean result = this.checkRow(r, nbr) && this.checkColumn(c, nbr) && this.checkGrid(r, c, nbr);

		// Place it back after checks
		this.board[r][c] = v;

		return result;
	}

	/**
	 * Checks if nbr is valid in the row
	 * 
	 * @param r   The row to check
	 * @param c   The column to check
	 * @param nbr The number to verify
	 * @return true if valid
	 */

	private boolean checkRow(int r, int nbr) {

		// Loop all values in row r
		for (int n : this.board[r]) {
			// If nbr exists anywhere else in the row, it can't be solved
			if (nbr == n) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if nbr is valid in the column
	 * 
	 * @param r   The row to check
	 * @param c   The column to check
	 * @param nbr The number to verify
	 * @return true if valid
	 */

	private boolean checkColumn(int c, int nbr) {

		// Loop all values in column c
		for (int i = 0; i < 9; i++) {

			// If nbr exists anywhere else in the column, it can't be solved
			if (this.board[i][c] == nbr) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if nbr is valid in the grid
	 * 
	 * @param r   The row to check
	 * @param c   The column to check
	 * @param nbr The number to verify
	 * @return true if valid
	 */

	private boolean checkGrid(int r, int c, int nbr) {

		// Get first row and column of grid
		int startRow = (r / 3) * 3;
		int startCol = (c / 3) * 3;

		// Loop through all rows and columns of grid
		for (int i = startRow; i < startRow + 3; i++) {
			for (int j = startCol; j < startCol + 3; j++) {
				// If nbr exists anywhere else in the grid, it can't be solved
				if (this.board[i][j] == nbr) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isValid() {
		// Loop through all columns and rows and call isValid on every one
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (!isValid(r, c, this.board[r][c])) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean solve() {
		return solve(0, 0);
	}

	/**
	 * Private helper method to recursivley try to solve the sudoku
	 * 
	 * @param r The row that is being tested
	 * @param c The column that is being tested
	 * @return true if the sudoku is solved
	 */
	private boolean solve(int r, int c) {

		// If at last row, start returning true
		if (r == 9) {
			return true;
		}

		// Initialize new row and new column variables
		int nR, nC;

		// Check if on last column of board
		if (c < 9 - 1) {
			// If not, go to next column
			nC = c + 1;
			nR = r;
		} else {
			// If so, restart at next row
			nC = 0;
			nR = r + 1;
		}

		// Check if value is not set
		if (this.board[r][c] == 0) {

			// Loop through values [1, 2, ..., dim];
			for (int i = 1; i < 9 + 1; i++) {

				// Check if value can be placed in board
				if (this.isValid(r, c, i)) {

					// Place value to go to next one
					this.board[r][c] = i;

					// If next one also can be solved, return true
					if (this.solve(nR, nC)) {
						return true;
					}

					// Else, set it back to Empty
					this.board[r][c] = 0;
				}
			}

			return false;
		}

		// If value is set, return if its valid and the next one can be solved
		return this.isValid(r, c, this.board[r][c]) && this.solve(nR, nC);

	}

	@Override
	public void clear() {
		// Loop through all values and set them to EMPTY
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = 0;
			}
		}

	}

	@Override
	public int[][] getMatrix() {
		return this.copyArray(this.board);
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		this.board = this.copyArray(nbrs);

	}

	/**
	 * Helper method to check that all arguments are within the dimension
	 * 
	 * @param args Integers to compare to dimension
	 */
	private void checkArgs(int... args) {

		for (int a : args) {
			if (a > 9 || a < 0) {
				throw new IllegalArgumentException();
			}

		}
	}

	/**
	 * Copies a 2-dimensional array
	 * 
	 * @param arr The array to copy
	 * @return A new copy of the provided array
	 */

	private int[][] copyArray(int[][] arr) {
		int l = arr.length;
		int[][] temp = new int[l][l];

		for (int i = 0; i < l; i++) {
			for (int j = 0; j < l; j++) {
				temp[i][j] = arr[i][j];
			}
		}
		return temp;
	}

}