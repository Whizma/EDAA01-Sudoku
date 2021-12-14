package sudokuSolver;

public class Main {

	public static void main(String[] args) {
		Solver solve = new Solver();
		/*	int[][] matrix = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		solve.setMatrix(matrix);
		System.out.println(solve.solve()); */
		int[][] emptyMatrix = new int[9][9];
		solve.setMatrix(emptyMatrix);
		System.out.println(solve.isValid());
		solve.solve();
		System.out.println(solve.isValid());
		System.out.println(solve.print());

//		Solver solve = new Solver();
//		int[][] matrix = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
//				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
//				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
//				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
//		solve.setMatrix(matrix);
//		System.out.println(solve.solve()); */
//		int[][] emptyMatrix = new int[9][9];
//		solve.setMatrix(emptyMatrix);
//		solve.solve();
//		System.out.print(solve.print());
		
//		SudokuSolver solver = new Solver();
		
//		new SudokuView(solver);
	}

}
