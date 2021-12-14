package sudokuSolver;

public class Solver implements SudokuSolver {

	private int[][] board;

	public Solver() {
		board = new int[9][9];
	}

	@Override
	public void add(int r, int c, int nbr) {
		checkArgs(r, c, nbr);

		this.board[r][c] = nbr;

	}

	@Override
	public int get(int r, int c) {
		checkArgs(r, c);

		return this.board[r][c];
	}

	@Override
	public void remove(int r, int c) {
		checkArgs(r, c);

		this.board[r][c] = 0;

	}

	private boolean isValid(int r, int c, int nbr) { // Kollar en ruta om den �r valid
		checkArgs(r, c, nbr);

		// Tar bort siffran vi vill testa s� att den inte ger possitivt p� sig sj�lv
		int v = this.board[r][c];
		this.board[r][c] = 0;

		// Kolla alla riktningar
		boolean result = this.checkRow(r, nbr) && this.checkColumn(c, nbr) && this.checkGrid(r, c, nbr);

		// Placerar tillbaka siffran efter�t
		this.board[r][c] = v;

		return result;
	}

	private boolean checkRow(int r, int nbr) { // Kollar l�ngs raden om valid

		for (int n : this.board[r]) {
			if (nbr == n) {
				return false;
			}
		}

		return true;
	}

	private boolean checkColumn(int c, int nbr) { // Kollar l�ngs kolumnen om valid

		for (int i = 0; i < 9; i++) {
			if (this.board[i][c] == nbr) {
				return false;
			}
		}

		return true;
	}

	private boolean checkGrid(int r, int c, int nbr) { // kollar 3x3 griden om valid

		// Trick med int f�r att plocka ut de olika 3x3 gridsen
		int startRow = (r / 3) * 3;
		int startCol = (c / 3) * 3;

		// Loopa griden och kolla om siffran �r valid
		for (int i = startRow; i < startRow + 3; i++) {
			for (int j = startCol; j < startCol + 3; j++) {
				if (this.board[i][j] == nbr) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isValid() { // Kollar rad, kolumn och grid f�r varje position p� br�det
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (!isValid(r, c, this.board[r][c])) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean solve() {
		return solve(0, 0);
	}

	// Privat hj�lp metod f�r solve
	private boolean solve(int r, int c) {

		// B�rja returnera true om p� sista raden
		if (r == 9) {
			return true;
		}
		
		// Variabler f�r n�sta row och kolumn
		int nextRow, nextColumn;

		// Kolla om sista kolumn
		if (c < 9 - 1) {
			nextColumn = c + 1;
			nextRow = r;
		} else {
			// B�rja p� n�sta rad om sista kolumnen
			nextColumn = 0;
			nextRow = r + 1;
		}

		// Kolla om rutan �r tom
		if (this.board[r][c] == 0) {

			// Loopa alla v�rden [0..9]
			for (int i = 1; i < 9 + 1; i++) {

				// Kolla om v�rdet kan placeras
				if (this.isValid(r, c, i)) {

					// Placera v�rdet och g� vidare
					this.board[r][c] = i;

					// Kan n�sta ocks� placeras returna true
					if (this.solve(nextRow, nextColumn)) {
						return true;
					}

					// Annars s�tt tillbaka till 0
					this.board[r][c] = 0;
				}
			}

			return false;
		}

		// Om v�rdet sattes, returnera om den �r valid samt om n�sta kan l�sas
		return this.isValid(r, c, this.board[r][c]) && this.solve(nextRow, nextColumn);

	}

	@Override
	public void clear() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = 0;
			}
		}

	}

	@Override
	public int[][] getMatrix() {
		return this.copyArray(this.board);
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		this.board = this.copyArray(nbrs);

	}

	private void checkArgs(int... args) { // Smart s�tt att kolla int av olika former

		for (int a : args) {
			if (a > 9 || a < 0) {
				throw new IllegalArgumentException();
			}

		}
	}

	private int[][] copyArray(int[][] arr) { // Skapar en kopia av en array
		int l = arr.length;
		int[][] temp = new int[l][l];

		for (int i = 0; i < l; i++) {
			for (int j = 0; j < l; j++) {
				temp[i][j] = arr[i][j];
			}
		}
		return temp;
	}

}