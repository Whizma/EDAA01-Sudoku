package sudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuTests {
	private SudokuSolver solver;
	private final int[][] testBoard = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
			{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
			{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

	private final int[][] unsolvableBoard = { { 1, 2, 3, 0, 0, 0, 0, 0, 0 }, { 4, 5, 6, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 7, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	@BeforeEach
	void setUp() throws Exception {
		this.solver = new Solver();
	}

	@AfterEach
	void tearDown() throws Exception {
		this.solver = null;
	}

	/**
	 * Testar tomt bräde
	 */

	@Test
	void testEmpty() {
		assertTrue(this.solver.solve());
	}

	/**
	 * Testar olagliga inputs
	 */

	@Test
	void testInvalidInput() {
		assertThrows(IllegalArgumentException.class, () -> this.solver.add(3, 3, -1), "Should throw error");
		assertThrows(IllegalArgumentException.class, () -> this.solver.add(3, 3, 10), "Should throw error");
	}

	/**
	 * Testar att lösa testfall 5
	 */

	@Test
	void testPredefinedBoard() {
		this.solver.setMatrix(this.testBoard);
		assertTrue(this.solver.solve());
	}

	/**
	 * Testar att lösa det olösliga i testfall 3
	 */

	@Test
	void testUnsolvableBoard() {
		this.solver.setMatrix(unsolvableBoard);
		assertFalse(this.solver.solve());
		solver.add(2, 3, 0);
		assertTrue(this.solver.solve());
	}

	/**
	 * Testar olösbar bräde om felaktigt row
	 */

	@Test
	void testUnsolvableRow() {
		this.solver.add(0, 0, 5);
		this.solver.add(0, 3, 5);

		assertFalse(this.solver.solve());

		this.solver.add(0, 3, 0);

		assertTrue(this.solver.solve());

	}

	/**
	 * Testar olösbar bräde om felaktigt kolumn
	 */

	@Test
	void testUnsolvableColumn() {
		this.solver.add(0, 0, 5);
		this.solver.add(1, 0, 5);

		assertFalse(this.solver.solve());

		this.solver.add(1, 0, 0);

		assertTrue(this.solver.solve());
	}

	/**
	 * Testar olösbar bräde om felaktigt grid
	 */

	@Test
	void testUnsolvableGrid() {
		this.solver.add(0, 0, 5);
		this.solver.add(1, 1, 5);

		assertFalse(this.solver.solve());

		this.solver.add(0, 0, 0);

		assertTrue(this.solver.solve());
	}

	/**
	 * Testar get och add
	 */

	@Test
	void testGetAddNumber() {
		assertEquals(this.solver.get(0, 0), 0);
		assertThrows(IllegalArgumentException.class, () -> this.solver.get(10, 10), "Should throw error");

		this.solver.add(0, 0, 1);

		assertThrows(IllegalArgumentException.class, () -> this.solver.add(0, 0, 12), "Should throw error");
		assertEquals(this.solver.get(0, 0), 1);

	}

	/**
	 * Testar remove funktionen
	 */

	@Test
	void testRemove() {
		this.solver.add(0, 0, 5);
		assertEquals(this.solver.get(0, 0), 5);

		this.solver.remove(0, 0);
		assertEquals(this.solver.get(0, 0), 0);

		assertThrows(IllegalArgumentException.class, () -> this.solver.remove(10, 10), "Should throw error");
	}

	/**
	 * Testar isValid
	 */

	@Test
	void testIsValid() {
		this.solver.add(0, 0, 1);
		this.solver.add(4, 1, 1);

		assertTrue(this.solver.solve());
		assertTrue(this.solver.isValid());
	}

	/**
	 * Testar clear
	 */

	@Test
	void testClear() {
		int dim = 9;
		solver.add(0, 0, 5);
		solver.add(1, 0, 5);
		assertFalse(this.solver.solve());
		solver.clear();
		assertTrue(this.solver.solve());
		this.solver.clear();
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				assertEquals(this.solver.get(r, c), 0);
			}
		}
	}

	/**
	 * Testar setMatrix
	 */

	@Test
	void testSetMatrix() {
		this.solver.setMatrix(testBoard);
		assertEquals(this.solver.get(2, 0), 1);
		assertEquals(this.solver.get(0, 2), 8);
	}

	/**
	 * Testar getMatrix
	 */

	@Test
	void testGetMatrix() {
		int[][] empty = this.solver.getMatrix();

		int dim = 9;

		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				assertEquals(empty[r][c], 0);
			}
		}

		// Testar första raden efter lösning
		this.solver.solve();
		int[][] solved = this.solver.getMatrix();

		for (int i = 0; i < dim; i++) {
			assertEquals(solved[0][i], i + 1);
		}
	}
}