package sudokuSolver;

public class Main {
	
	// Initializes a solver and a view
	public static void main(String[] args) {
		SudokuSolver solver = new Solver();
		
		new SudokuView(solver);
	}


}