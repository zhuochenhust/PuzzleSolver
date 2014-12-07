public class PuzzleBoard extends Tile {
	Cell[][] board;
	boolean[][] boardstatus;
	private int size;
	private int width;
	private int height;

	public PuzzleBoard (int id, Tile tile) {
		this.id = id;
		this.size = tile.size();
		this.width = tile.width();
		this.height = tile.height();
		// height would be Y axis, width would be X axis
		// Row would Y axis, column would be X axis
		this.boardstatus = new boolean[tile.height()][tile.width()];
		board = new Cell[tile.height()][tile.width()];
		
		// put cells from tile into array board
		for (Cell c: tile.cells) {
			board[c.getY()- tile.getTop()][c.getX() - tile.getLeft()] = c;		
		}
		
		// put empty space with a empty blank in array board
		for(int i=0; i < height; i++){
			for(int j=0; j< width; j++){
				if (board[i][j] ==null){
					board[i][j] = new Cell(i, j , BLANK);
				}
			}
		}
		
	}

	public boolean putPiece(Tile tile, int row, int col) {

		
		for(Cell c: tile.cells) {
			if (!(this.checkColIndex(col  + c.x) && this.checkRowIndex(row + c.y))){
				return false;
			}
          if(checkBoundary(c.y + row, c.x + col)){
        	// print out the place where we put the cell
        	 // System.out.println("New row:" +(c.y + row) + "New column: " + (c.x + col));
			if( c.c != board[c.y + row][c.x + col].c){ // Char don't match, return false
				return false;
			} else if (boardstatus[c.y + row][c.x + col]){ // board has been occupied, return false
			    return false;	
			} else{                    
				boardstatus[c.y + row][c.x + col] = true;
			}  
          }
		}
		
		return true;
	}

	public boolean checkBoundary(int row, int column){
		if(0 <= row && row < this.height && 0 <= column && column < this.width){
			return true;
		}
		System.out.println("The index is out of boundary for board!");
		return false;
	}
	
	public boolean checkRowIndex(int row){
		if ((row >= height) || (row < 0)){
			//System.out.println("Illegal Row Number. Row should be height > row >=0");
			return false;
		}
		return true;		
	}
	
	public boolean checkColIndex(int col){
		if ((col >= width) || (col < 0)){
			//System.out.println("Illegal Row Number. Row should be width > col >=0");
			return false;
		}
		return true;		
	}
	
	public boolean[] getRow(int len, int id){
		boolean[] row =  new boolean[len + size];
		int count = 0; 
		for(int i=0; i<height; i++) {
			for(int j=0;  j< width; j++) {
				if(board[i][j].c != BLANK){
					row[count + len ] = boardstatus[i][j];
					count++;
				}
			}
		}	
		row[id] = true;
		return row;		
	}

	public void removeTile(Tile tile, int row, int col) {
		
		for(Cell c: tile.cells) {   
			if (this.checkRowIndex(row +c.y) && this.checkColIndex(col + c.x)){
				boardstatus[c.y + row][c.x + col] = false; 
			}
		}
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public int size(){
		return this.size;
	}

}
