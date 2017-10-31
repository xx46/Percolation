//NetID: xx46

public class PercolationUF implements IPercolate {
	public IUnionFind myFinder;
	public boolean[][] myGrid;
	public int myOpenSites;
	private final int VTOP;
	private final int VBOTTOM;
	
	public PercolationUF(int size, IUnionFind finder) {
		finder.initialize(size*size+2);
		this.myFinder = finder;	
		boolean[][] grid = new boolean[size][size];
		this.myGrid = grid;
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++)
				myGrid[i][j]=false;
		}
		VTOP = size*size;
		VBOTTOM = size*size+1;
		myOpenSites = 0;
	}

	protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
	
	public int getIndex(int row, int col) {
		//if (row<0 || row>=myGrid.length || col<0 ||col>=myGrid[0].length){
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		int result = myGrid.length*row+col;
		return result;
	}

	@Override
	public void open(int row, int col) {
		//if (row<0 || row>=myGrid.length || col<0 ||col>=myGrid[0].length)
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		else {
			if (myGrid[row][col] == false) {
				myGrid[row][col] = true;
				myOpenSites += 1;
				updateOnOpen(row,col);
			}
		}
	}

	@Override
	public boolean isOpen(int row, int col) {
		//if (row<0 || row>=myGrid.length || col<0 ||col>=myGrid[0].length)
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		//if (row<0 || row>=myGrid.length || col<0 ||col>=myGrid[0].length)
			//throw new IndexOutOfBoundsException("Index is out of bounds.");
		return myFinder.connected(VTOP, getIndex(row,col));
	}

	@Override
	public int numberOfOpenSites() {
		return myOpenSites;
	}

	@Override
	public boolean percolates() {
		if (myFinder.connected(VTOP, VBOTTOM)) {
			return true;
		}
		return false;
	}


	public void updateOnOpen(int row, int col) {
		//if (row<0 || row>=myGrid.length || col<0 ||col>=myGrid[0].length){
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		int myIndex = 0;
		if (row>=0 && row<myGrid.length) {
			myIndex = getIndex(row,col);
		}
		
		if (row == myGrid.length-1){
				myFinder.union(VBOTTOM, getIndex(row,col));
			}
		
		if (row ==0) {
			myFinder.union(VTOP, getIndex(row,col));
		}
		
		//if (row+1 < 0 || row+1 >= myGrid.length || col<0 ||col>=myGrid[0].length){
		if (! inBounds(row+1,col)) {
			if (isOpen(row+1, col)){
				myFinder.union(myIndex, getIndex(row+1,col));
			}
		}
		
		//if (row-1 < 0 || row-1 >= myGrid.length || col<0 ||col>=myGrid[0].length){
		if (! inBounds(row-1,col)) {
			if (isOpen(row-1, col)){
				myFinder.union(myIndex, getIndex(row-1,col));
			}
		}
		
		//if (row < 0 || row >= myGrid.length || col+1 < 0 ||col+1 >= myGrid[0].length){
		if (! inBounds(row,col+1)) {
			if (isOpen(row, col+1)){
				myFinder.union(myIndex, getIndex(row,col+1));
			}
		}
		
		//if (row < 0 || row >= myGrid.length || col-1 < 0 ||col-1 >= myGrid[0].length){
		if (! inBounds(row,col-1)) {
			if (isOpen(row, col-1)){
				myFinder.union(myIndex, getIndex(row,col-1));
			}
		}

	}

}
