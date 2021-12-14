package sudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuTests {
	private SudokuSolver sudoku;
	private final int[][] testBoard = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
			{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
			{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

	@BeforeEach
	void setUp() throws Exception {
		// Create empty Sudoku
		this.sudoku = new Solver();
	}

	@AfterEach
	void tearDown() throws Exception {
		this.sudoku = null;
	}

	/**
	 * Tests with an empty board
	 */

	@Test
	void testEmpty() {
		assertTrue(this.sudoku.solve());
	}

	/**
	 * Tests with the predefined board that was given in the instructions
	 */

	@Test
	void testPredefinedBoard() {
		this.sudoku.setMatrix(this.testBoard);

		assertTrue(this.sudoku.solve());
	}

	/**
	 * Tests with an unsolvable board, row is invalid
	 */

	@Test
	void testUnsolvableRow() {
		this.sudoku.add(0, 0, 1);
		this.sudoku.add(0, 3, 1);

		assertFalse(this.sudoku.solve());

		this.sudoku.add(0, 3, 0);

		assertTrue(this.sudoku.solve());
	}

	/**
	 * Tests with an unsolvable board, column is invalid
	 */

	@Test
	void testUnsolvableColumn() {
		this.sudoku.add(0, 0, 1);
		this.sudoku.add(1, 0, 1);

		assertFalse(this.sudoku.solve());

		this.sudoku.add(1, 0, 0);

		assertTrue(this.sudoku.solve());
	}

	/**
	 * Tests with an unsolvable board, grid is invalid
	 */

	@Test
	void testUnsolvableGrid() {
		this.sudoku.add(0, 0, 1);
		this.sudoku.add(0, 1, 1);

		assertFalse(this.sudoku.solve());

		this.sudoku.add(0, 1, 0);

		assertTrue(this.sudoku.solve());
	}

	/**
	 * Tests the public getNumber & setNumber methods
	 */

	@Test
	void testGetSetNumber() {
		assertEquals(this.sudoku.get(0, 0), 0);
		assertThrows(IllegalArgumentException.class, () -> this.sudoku.get(10, 10), "Should throw error");

		this.sudoku.add(0, 0, 1);
		
		assertThrows(IllegalArgumentException.class, () -> this.sudoku.add(0, 0, 12), "Should throw error");
		assertEquals(this.sudoku.get(0, 0), 1);

	}
	
	/**
	 * Tests the public method clearNumber
	 */

	@Test
	void testClearNumber() {
		this.sudoku.add(0, 0, 1);
		assertEquals(this.sudoku.get(0, 0), 1);

		this.sudoku.remove(0, 0);
		assertEquals(this.sudoku.get(0, 0), 0);
		
		assertThrows(IllegalArgumentException.class, () -> this.sudoku.remove(10, 10), "Should throw error");
	}
	
	/**
	 * Tests the public methods isValid and isAllValid
	 */
	
	@Test
	void testIsAllValid() {
		this.sudoku.add(0, 0, 1);
		this.sudoku.add(4, 1, 1);
		
//		assertTrue(this.sudoku.isValid(0, 0, 1), "IsValid is not correct"); Vi kan inte kontrollera enstaka positioner?
		
		assertTrue(this.sudoku.solve());
		assertTrue(this.sudoku.isValid());
		
//		assertThrows(IllegalArgumentException.class, () -> this.sudoku.isValid(10, 10, 10), "Should throw error"); samma som ovan
	}
	
	/**
	 * Tests the public method clearAll
	 */
	
	@Test
	void testClearAll() {
		int dim = 9;
		
		assertTrue(this.sudoku.solve());
		
		this.sudoku.clear();
		
		
		for(int r = 0; r < dim; r++) {
			for(int c = 0; c < dim; c++) {
				assertEquals(this.sudoku.get(r, c), 0);
			}
		}
	}
	
	/**
	 * Tests the public method setMatrix
	 */
	
	@Test
	void testSetMatrix() {
		this.sudoku.setMatrix(testBoard);
		assertEquals(this.sudoku.get(2, 0), 1);
		assertEquals(this.sudoku.get(0, 2), 8);
	}
	
	/**
	 * Tests the public method getMatrix
	 */
	
	@Test
	void testGetMatrix() {
		int[][] empty = this.sudoku.getMatrix();
		
		int dim = 9;
		
		for(int r = 0; r < dim; r++) {
			for(int c = 0; c < dim; c++) {
				assertEquals(empty[r][c], 0);
			}
		}
		
		// Test first row once solved
		this.sudoku.solve();
		int[][] solved = this.sudoku.getMatrix();
		
		for(int i = 0; i < dim; i++) {
			assertEquals(solved[0][i], i + 1);
		}
	}
	
	/**
	 * Tests the public method getDimension
	 */
	
	@Test
	void testDimension() {
//		assertEquals(this.sudoku.getDimension(), 9); Denna blir 9 = 9
		
//		final int dim = 16; för 16*16 bräde
//		SudokuSolver newDimSudoku = new Solver(dim);
//		assertEquals(newDimSudoku.getDimension(), dim);
	}

}