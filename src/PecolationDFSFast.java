//NetID: xx46

public class PecolationDFSFast {
	public class PercolationDFSFast extends PercolationDFS{
	/**Initialize a grid so that all cells are blocked.
	 * @param n is the size of the simulated (square) grid
	 */
	public PercolationDFSFast(int n) {
		super(n);
	}

	@Override
	public void updateOnOpen(int row, int col) {
		if (inBounds(row,col) && isFull(row,col)) {
			dfs(row+1,col);
			dfs(row-1,col);
			dfs(row,col+1);
			dfs(row,col-1);
			myGrid[row][col] = FULL;
		}
		if (inBounds(row+1,col) && isFull(row+1,col)) {
			dfs(row+1,col);
			dfs(row-1,col);
			dfs(row,col+1);
			dfs(row,col-1);
			myGrid[row][col] = FULL;
		}
		if (inBounds(row-1,col) && isFull(row-1,col)) {
			dfs(row+1,col);
			dfs(row-1,col);
			dfs(row,col+1);
			dfs(row,col-1);
			myGrid[row][col] = FULL;
		}
		if (inBounds(row,col+1) && isFull(row,col+1)) {
			dfs(row+1,col);
			dfs(row-1,col);
			dfs(row,col+1);
			dfs(row,col-1);
			myGrid[row][col] = FULL;
		}
		if (inBounds(row,col-1) && isFull(row,col-1)) {
			dfs(row+1,col);
			dfs(row-1,col);
			dfs(row,col+1);
			dfs(row,col-1);
			myGrid[row][col] = FULL;
		}
	}
	
	@Override
	public void open(int row, int col) {
		if (!inBounds(row, col)) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		super.open(row,col);
	}

	@Override
	public boolean isOpen(int row, int col) {
		if (!inBounds(row, col)) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		return super.isOpen(row,col);
	}

	@Override
	public boolean isFull(int row, int col) {
		if (!inBounds(row, col)) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		return super.isFull(row, col);
	}
  }
}
