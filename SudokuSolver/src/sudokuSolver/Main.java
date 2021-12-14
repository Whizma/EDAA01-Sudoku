package sudokuSolver;

public class Main {

	public static void main(String[] args) {
		SudokuSolver solver = new Solver();
		new SudokuView(solver);
	}

}