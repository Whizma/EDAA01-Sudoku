package sudokuSolver;

public class Main {
	
	// Initializes a solver and a view
	public static void main(String[] args) {
		int size = 9; 
		SudokuSolver solver = new Solver(size);
		
		new SudokuView(solver);
	}


}