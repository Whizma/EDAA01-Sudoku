package sudokuSolver;

public class Solver implements SudokuSolver {

	/**
	 * Constants
	 */
	
	private final static int EMPTY = 0;
	private final static int DEFAULT_SIZE = 9;
	private final static int[][] TEST_BOARD = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
			{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
			{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

	/**
	 * Attribute
	 */
	
	private int[][] board;

	public Solver() {
		// Overloading with default 9x9 grid size
		this(DEFAULT_SIZE);
	}

	public Solver(int size) {

		// Uncomment line 22 and comment out lines 24 - 31 to use the test board
		// this.board = TEST_BOARD;

		this.board = new int[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = EMPTY;
			}
		}
	}

	@Override
	public void setNumber(int r, int c, int nbr) {
		checkArgs(r, c, nbr);

		this.board[r][c] = nbr;

	}

	@Override
	public int getNumber(int r, int c) {
		checkArgs(r, c);

		return this.board[r][c];
	}

	@Override
	public void clearNumber(int r, int c) {
		checkArgs(r, c);

		this.board[r][c] = EMPTY;

	}

	@Override
	public boolean isValid(int r, int c, int nbr) {
		checkArgs(r, c, nbr);

		// Temporarily remove value so it doesnt clash in checks
		int v = this.board[r][c];
		this.board[r][c] = EMPTY;

		// Check if nbr is valid in all ways
		boolean result = this.checkRow(r, nbr) && this.checkColumn(c, nbr) && this.checkGrid(r, c, nbr);

		// Place it back after checks
		this.board[r][c] = v;

		return result;
	}
	
	/**
	 * Checks if nbr is valid in the row
	 * @param r
	 * The row to check
	 * @param c
	 * The column to check
	 * @param nbr
	 * The number to verify
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
	 * @param r
	 * The row to check
	 * @param c
	 * The column to check
	 * @param nbr
	 * The number to verify
	 * @return true if valid
	 */

	private boolean checkColumn(int c, int nbr) {

		// Loop all values in column c
		for (int i = 0; i < this.getDimension(); i++) {

			// If nbr exists anywhere else in the column, it can't be solved
			if (this.board[i][c] == nbr) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Checks if nbr is valid in the grid
	 * @param r
	 * The row to check
	 * @param c
	 * The column to check
	 * @param nbr
	 * The number to verify
	 * @return true if valid
	 */

	private boolean checkGrid(int r, int c, int nbr) {

		// Calculate grid size, ex. 3 for 9x9, 4 for 16x16 etc.
		int gSize = (int) Math.sqrt(this.board.length);

		// Get first row and column of NxN grid
		int startRow = (r / gSize) * gSize;
		int startCol = (c / gSize) * gSize;

		// Loop through all rows and columns of grid
		for (int i = startRow; i < startRow + gSize; i++) {
			for (int j = startCol; j < startCol + gSize; j++) {
				// If nbr exists anywhere else in the grid, it can't be solved
				if (this.board[i][j] == nbr) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isAllValid() {
		// Loop through all columns and rows and call isValid on every one
		for (int r = 0; r < this.getDimension(); r++) {
			for (int c = 0; c < this.getDimension(); c++) {
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
	 * @param r 
	 * The row that is being tested
	 * @param c
	 * The column that is being tested
	 * @return true if the sudoku is solved
	 */
	private boolean solve(int r, int c) {

		int dim = this.getDimension();

		// If at last row, start returning true
		if (r == dim) {
			return true;
		}

		// Initialize new row and new column variables
		int nR, nC;

		// Check if on last column of board
		if (c < dim - 1) {
			// If not, go to next column
			nC = c + 1;
			nR = r;
		} else {
			// If so, restart at next row
			nC = 0;
			nR = r + 1;
		}

		// Check if value is not set
		if (this.board[r][c] == EMPTY) {

			// Loop through values [1, 2, ..., dim];
			for (int i = 1; i < dim + 1; i++) {

				// Check if value can be placed in board
				if (this.isValid(r, c, i)) {

					// Place value to go to next one
					this.board[r][c] = i;

					// If next one also can be solved, return true
<<<<<<< HEAD
					if (this.solve(nR, nC)) {
=======
					if (solve(nextRow, nextColumn)) {
>>>>>>> branch 'main' of https://gitlab.com/ErikMalmgren/sudokuprojekt.git
						return true;
					}
<<<<<<< HEAD

					// Else, set it back to Empty
					this.board[r][c] = EMPTY;
=======
					else {
						// Else, set it back to Empty
						add(r,c,0);
						return false;
					}
>>>>>>> branch 'main' of https://gitlab.com/ErikMalmgren/sudokuprojekt.git
				}
			}
<<<<<<< HEAD

=======
>>>>>>> branch 'main' of https://gitlab.com/ErikMalmgren/sudokuprojekt.git
			return false;
		}

		// If value is set, return if its valid and the next one can be solved
<<<<<<< HEAD
		return this.isValid(r, c, this.board[r][c]) && this.solve(nR, nC);
=======
		//return isValid(r, c, board[r][c]) && solve(nR, nC);
		return isValid(r,c, get(r,c)) && solve(nextRow, nextColumn);

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
>>>>>>> branch 'main' of https://gitlab.com/ErikMalmgren/sudokuprojekt.git

	}

	@Override
<<<<<<< HEAD
	public void clear() {
		// Loop through all values and set them to EMPTY
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = EMPTY;
=======
	public void remove(int row, int col) {
		board[row][col] = 0;

	}

	@Override
	public int get(int row, int col) {
		checkArgs(row);
		checkArgs(col);
		return board[row][col];
	}
	
	public boolean isValid() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (!isValid(r, c, get(r, c))) {
					return false;
				}
>>>>>>> branch 'main' of https://gitlab.com/ErikMalmgren/sudokuprojekt.git
			}
		}
<<<<<<< HEAD
=======
		return true;
	}

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 */
	private boolean isValid(int c, int r, int digit) {
		checkArgs(c);
		checkArgs(r);
		checkArgs(digit);
		int temp = get(r, c);
		add(r, c, 0);
		boolean res = checkRow(r, digit) && checkColumn(c, digit) && checkGrid(r, c, digit);
		if (digit == 0) {
			res = true;
		}
		add(r, c, temp);

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

		int startRow = (r / 3) * 3;
		int startCol = (c / 3) * 3;

		for (int i = startRow; i < startRow + 3; i++) {
			for (int j = startCol; j < startCol + 3; j++) {
				if (board[i][j] == digit) {
					return false;
				}
			}
		}
		return true;
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
		checkArgs(m);
		board = m;
>>>>>>> branch 'main' of https://gitlab.com/ErikMalmgren/sudokuprojekt.git

	}

	@Override
	public int[][] getMatrix() {
		return this.copyArray(this.board);
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		this.board = this.copyArray(nbrs);

	}

	@Override
	public int getDimension() {
		return this.board.length;
	}

	/**
	 * Helper method to check that all arguments are within the dimension
	 * 
	 * @param args Integers to compare to dimension
	 */
	private void checkArgs(int... args) {
		int dim = this.getDimension();

		for (int a : args) {
			if (a > dim || a < 0) {
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