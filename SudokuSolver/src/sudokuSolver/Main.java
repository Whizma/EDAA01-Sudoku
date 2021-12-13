package sudokuSolver;

public class Main {

	public static void main(String[] args) {
		Solver solve = new Solver();
		int[][] emptyMatrix = new int[9][9];
		solve.setMatrix(emptyMatrix);
		solve.add(0, 0, 5);
		solve.add(1, 0, 5);
		System.out.println(solve.isValid());
	}

}
