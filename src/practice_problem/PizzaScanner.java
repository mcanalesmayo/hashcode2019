package practice_problem;

public class PizzaScanner implements Cloneable {
	
	private final boolean[][] cells;
	private final int rows;
	private final int cols;
	private int row;
	private int col;
	
	public PizzaScanner(final int rows, final int cols) {
		this.rows = rows;
		this.cols = cols;
		this.cells = new boolean[rows][cols];
		this.row = 0;
		this.col = 0;
	}
	
	public int[] nextUnusedPos() {
		for (int i = row; i < rows; i++) {
			for (int j = col; j < cols; j++ ) {
				if (!cells[i][j]) {
					int[] res = {row, col};
					if (j + 1 == cols) {
						row++;
						col = 0;
					} else {
						col++;
					}
					return res;
				}
			}
		}

		return null;
	}
	
	public void markAsNotUsed(final int r0, final int c0, final int r1, final int c1) {
		for (int i = r0, j = c0; i <= r1 && j <= c1; i++, j++) {
			cells[i][j] = false;
		}
	}
	
	public void markAsUsed(final int r0, final int c0, final int r1, final int c1) {
		for (int i = r0, j = c0; i <= r1 && j <= c1; i++, j++) {
			cells[i][j] = true;
		}
	}
	
	public boolean isAlreadyUsed(final int r, final int c) {
		return cells[r][c];
	}
	
	public boolean isAlreadyUsed(final int r0, final int c0, final int r1, final int c1) {
		for (int i = r0, j = c0; i <= r1 && j <= c1; i++, j++) {
			if (cells[i][j]) {
				return true;
			}
		}
		
		return false;
	}
	
	public PizzaScanner clone() {
		return new PizzaScanner(rows, cols);
	}
}
