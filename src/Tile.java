
import java.util.HashSet;

public class Tile {
	public static final char BLANK = ' ';
	protected int id = -1;
	protected HashSet<Cell> cells = new HashSet<Cell>();

	protected int left = 0;
	protected int top = 0;
	protected int right = 0;
	protected int bottom = 0;
	
	public void setId(int id){
		this.id = id;
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

	private char[][] getData() {
		char[][] data = new char[height()][width()];
		for (int i = data.length; i-- > 0;)
			for (int j = data[i].length; j-- > 0;)
				data[i][j] = BLANK;
		for (Cell p : cells) {
			data[p.y - top][p.x - left] = p.c;
		}
		return data;
	}

	public void print() {
		char[][] data = getData();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j]);
			}
			System.out.println();
		}
	}

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

	public int compareTo (Tile o) {
		if (o == null)
			return 1;
		return this.cells.size() - o.cells.size();
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
    }
}
