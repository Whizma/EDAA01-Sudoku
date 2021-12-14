package sudokuSolver;

public interface SudokuSolver {
	/**
	 * Sets the number nbr in box r, c.
	 * 
	 * @param r
	 *            The row
	 * @param c
	 *            The column
	 * @param nbr
	 *            The number to insert in box r, c
	 * @throws IllegalArgumentException        
	 *             if r or c is outside [0..getDimension()-1] or
	 *             number is outside [1..9] 
	 */
	public void add(int r, int c, int nbr);
	
	/**
	 * Returns the number in box r,c. If the box i empty 0 is returned.
	 * 
	 * @param r
	 *            The row
	 * @param c
	 *            The column
	 * @param number
	 *            The number to insert in r, c
	 * @return the number in box r,c or 0 if the box is empty.
	 * @throws IllegalArgumentException
	 *             if r or c is outside [0..getDimension()-1]
	 */
	public int get(int r, int c);
	
	/**
	 * Empties the provided box by setting the value to zero
	 * @param r
	 * Row of box
	 * @param c
	 * Column of box
	 * 
	 * @throws
	 * IllegalArgumentException 
	 * 				if r or c is outside [0..getDimension()-1]
	 */
	public void remove(int r, int c);
	

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 */
	public boolean isValid();
		
	/**
	 * Attempts to solve the sudoku recursively
	 * @return true if sudoku was solved
	 */
	public boolean solve();
		
	/**
	 * Empties all boxes by setting them to zero
	 */
	public void clear();
		
	/**
	 * Returns the numbers in the grid. An empty box i represented
	 * by the value 0.
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