package sudokuSolver;

public class Solver implements SudokuSolver {

	private int[][] board;
	private int dim = 9;

	public Solver() {
		this.board = new int[dim][dim];
	}

	@Override
	public boolean solve() {
		// TODO Auto-generated method stub
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
		if(row < 0 || row > 9) {
			throw new IllegalArgumentException();
		}
		if(col < 0 || col > 9) {
			throw new IllegalArgumentException();
		}
		if(digit < 0 || digit > 9) {
			throw new IllegalArgumentException();
		}
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

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
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
		if (m.length == dim - 1 || m[0].length == dim - 1) { // Kollar dimensionerna för m
			for (int i = 0; i < dim - 1; i++) {
				for (int j = 0; i < dim - 1; j++) {
					if (m[i][j] < 0 || m[i][j] > 9) { // Kollar alla värden i m så att ingen är 0 < eller > 9
						throw new IllegalArgumentException();
					}
				}
			}
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

}
