
import java.util.HashSet;
import java.util.ArrayList;

public class Tile {
	public static final char BLANK = ' ';
	
	public static final int DEGREE90 = 90;
	public static final int DEGREE180 = 180;
	public static final int DEGREE270 = 270;
	
	public static final int RlectionOnX = 1;
	public static final int RlectionOnY = 0;
	
	protected int id = -1;
	protected HashSet<Cell> cells = new HashSet<Cell>();
	
	protected ArrayList<Cell> arrayCells = new  ArrayList<Cell>();

	protected int left = 0;
	protected int top = 0;
	protected int right = 0;
	protected int bottom = 0;
	
	public void setId(int id){
		this.id = id;
	}
	   
	public int getId(){
		return this.id;
	}
	
	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getRight() {
		return right;
	}

	public int getBottom() {
		return bottom;
	}

	public int width() {
		return right - left + 1;
	}

	public int height() {
		return bottom - top + 1;
	}

	public int size() {
		return cells.size();
	}

	public void addPoint(int x, int y, char ch) {
		if (cells.isEmpty()) {
			left = right = x;
			top = bottom = y;
		} else {
			if (x < left)
				left = x;
			else if (x > right)
				right = x;
			if (y < top)
				top = y;
			else if (y > bottom)
				bottom = y;
		}
		cells.add(new Cell(x, y, ch));
   }

	public char[][] getCharBoard() {
		char[][] data = new char[height()][width()];
		for (int i = data.length; i-- > 0;)
			for (int j = data[i].length; j-- > 0;)
				data[i][j] = BLANK;
//		for (Cell p : cells) {
//			data[p.y - top][p.x - left] = p.c;
//		}
		for (Cell p : cells) {
			data[p.y][p.x] = p.c;
		}
		return data;
	}
	
	public int[][] getColorBoard() {
		int[][] colorBoard = new int[height()][width()];
		char[][] charBoard = this.getCharBoard();
		for (int i = colorBoard.length; i-- > 0;){
			for (int j = colorBoard[i].length; j-- > 0;)
				if(charBoard[i][j] != BLANK) {
					// let color board assign with the color id
					colorBoard[i][j] = this.id + 1;
				}
		}
		
		return colorBoard;
	}
//
//	public void print() {
//		char[][] data = getData();
//		for (int i = 0; i < data.length; i++) {
//			for (int j = 0; j < data[i].length; j++) {
//				System.out.print(data[i][j]);
//			}
//			System.out.println();
//		}
//	}

//	@Override
//	public String toString() {
//		char[][] data = getData();
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < data.length; i++) {
//			for (int j = 0; j < data[i].length; j++) {
//				sb.append(data[i][j]);
//			}
//			sb.append(Piece.NEWLINE);
//		}
//		return sb.toString();
//	}
	public int compareTo(Tile tile) {
		if (tile == null)
			return 1;
		return this.cells.size() - tile.cells.size();
	}
	
	public boolean equals(Tile tile) {
		if (tile == null)
			return false;
		int count =0;
		for(Cell c : tile.cells){
			for(Cell otherC: this.cells){
				if(c.equals(otherC)){
					count++;
				}		
			}
		}
		if(count == tile.cells.size()){
			return true;
		}
		return false;
	}
	

    public void printTile (){
	    System.out.println("Print Out one piece of tile: ");
		   for (Cell c: cells){
			     c.printCell();	  
		}
	}
    public void standize(){
    	for(Cell c: cells){
    		c.x = c.x - getLeft();
    		c.y = c.y - getTop();
    	}	
    	// get ArrayList Points
    	this.getArrayListCells();
    }
    
    public Tile rotateTile( int degree){
    	Tile rotatedTile = new Tile(); 
    	
    	switch (degree) {
    		case DEGREE90: 
    			for(Cell cell: this.cells){
        		rotatedTile.addPoint(-cell.y, cell.x, cell.c);
        	}
    			break;
    		case DEGREE180:
    			for(Cell cell: this.cells){
            		rotatedTile.addPoint(-cell.x, -cell.y, cell.c);
            	}
    			break;
    		case DEGREE270:
    			for(Cell cell: this.cells){
            		rotatedTile.addPoint(cell.y, -cell.x, cell.c);
            	}
    			break;
    		default: 
    			System.out.println("Illegal Degree Parameter!!");    			
    	}
    	 	
    	rotatedTile.standize();
    	rotatedTile.setId(this.id);
    	return rotatedTile;
    }
    
    public Tile reflectTile( int direction){
    	Tile reflectedTile = new Tile(); 
    	
    	switch (direction) {
    		case RlectionOnX: 
    			for(Cell cell: this.cells){
    				reflectedTile.addPoint(cell.x, -cell.y, cell.c);
        	}
    			break;
    		case RlectionOnY:
    			for(Cell cell: this.cells){
    				reflectedTile.addPoint(-cell.x, cell.y, cell.c);
            	}
    			break;
    		default: 
    			System.out.println("Illegal Rlection Parameter!!");    			
    	}
    	 	
    	reflectedTile.standize();
    	reflectedTile.setId(this.id);
    	return reflectedTile;
    }
    
    public void getArrayListCells(){
		for(Cell cell: this.cells){
    		arrayCells.add(cell);
    	}
    }
    
    public Tile copy(){
    	Tile copiedTile = new Tile();
    	copiedTile.id = this.id;
    	copiedTile.cells = this.cells;
    	
    	copiedTile.arrayCells = this.arrayCells;

    	copiedTile.left = this.left;
    	copiedTile.top = this.top;
    	copiedTile.right = this.right;
    	copiedTile.bottom = this.bottom;
    	
    	return copiedTile;
    	
    }
}
