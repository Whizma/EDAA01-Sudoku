package sudokuSolver;

public interface SudokuSolver {
	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param r     The row
	 * @param c     The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	public void add(int r, int c, int digit);

	/**
	 * Returns the number in box r,c. If the box i empty 0 is returned.
	 * 
	 * @param r The row
	 * @param c The column
	 * @return the number in box r,c or 0 if the box is empty.
	 * @throws IllegalArgumentException if r or c is outside [0..9]
	 */
	public int get(int r, int c);

	/**
	 * Empties the provided box by setting the value to zero
	 * 
	 * @param r Row of box
	 * @param c Column of box
	 * 
	 * @throws IllegalArgumentException if r or c is outside [0..9]
	 */
	public void remove(int r, int c);

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 * 
	 * @return true if postions are valid
	 */
	public boolean isValid();

	/**
	 * Attempts to solve the sudoku
	 * 
	 * @return true if sudoku was solved
	 */
	public boolean solve();

	/**
	 * Empties all boxes by setting them to zero
	 */
	public void clear();

	/**
	 * Returns the numbers in the sudoku. An empty box i represented by the value 0.
	 * 
	 * @return the numbers in the grid
	 */
	public int[][] getMatrix();

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	public void setMatrix(int[][] m);

}