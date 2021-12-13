package sudokuSolver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author isak
 *
 */
class SolverTest {
	private Solver solver;
	private int[][] emptyMatrix;

	@BeforeEach
	void setUp() throws Exception {
		solver = new Solver();
	} 
	
	@AfterEach
	void tearDown() throws Exception {
		solver = null;
	}
	
	@Test
	void testEmptySolve() {
		this.emptyMatrix = new int[9][9];
		solver.setMatrix(emptyMatrix);
		assertTrue(solver.solve());
	}
	
	@Test
	void testEmptyIsValid() {
		emptyMatrix = new int[9][9];
		solver.setMatrix(emptyMatrix);
		solver.add(0,0,0);
		assertTrue(solver.isValid());
	}
	
	@Test
	void testIsValid() {
		
	}
}
