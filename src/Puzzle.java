import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Puzzle {
	
	
	
	private PuzzleBoard board;
	private Tile[] tiles;
	public void parse(String path) throws IOException {
		parse(new FileInputStream(new File(path)));
		
	}
	
	public void parse(InputStream inStream) throws IOException {
		ArrayList<char[]> fullInput = new ArrayList<char[]>();

		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
		try {
			String line = null;
			while ((line = in.readLine()) != null) {
				fullInput.add(line.toCharArray());
				System.out.println(line.toCharArray());
			}
			
		} finally {
			in.close();
		}
		
		DivideToTiles toTiles = new DivideToTiles(fullInput);
		toTiles.parse();
		//Get the biggest tile in the board and then add as target
		ArrayList<Tile> tilesList = toTiles.getTiles();
		tilesList.remove(toTiles.getMaxTile());
		//add it as board
		board = new PuzzleBoard(0, toTiles.getMaxTile());
		tiles = new Tile[tilesList.size()];	
		for(int i=0; i < tilesList.size(); i++){
			tilesList.get(i).standize();
			tilesList.get(i).setId(i);
			tiles[i] = tilesList.get(i);
		}
		tiles[0].printTile();
		// Set head's id as -1
		ColumnNode head = new ColumnNode(-1);
		ToMatrix mat = new ToMatrix(board, tiles);
		for(int c = 0; c < mat.matrixColumnNumber; c++){
			ColumnNode columnNode = new ColumnNode(c);
			if (c==0){
				head.setRightNode(columnNode);
				columnNode.setLeftNode(head);
			}
			boolean flag = true;
			RowNode rowNode;
			RowNode upRowNode = new RowNode(columnNode.getId());
			int count =0;
			
			for(int r = 0; r< mat.matrixRowNumber; r++){
				//only let the first 1 in a column, execute the statements inside if
				if(mat.matrix[r][c]){
					if(flag){
						rowNode= new RowNode(columnNode.getId());
						columnNode.setDownNode(rowNode);
						rowNode.setUpNode(columnNode);	
						flag =false;
	                   //set upRowNode					
						upRowNode = rowNode;
						count =1;
					} else {
						rowNode = new RowNode(columnNode.getId());
						upRowNode.setDownNode(rowNode);
						rowNode.setUpNode(upRowNode);	
						//update upRowNode
						upRowNode = rowNode;
						count++;
					}
			     }		
		     }
			 columnNode.setNodeNumber(count);	
			 System.out.println("Count: " + count);
		}
		
		//mat.printMatrix();
		int leftRowNumber = 0;
		int leftColumnNumber = 0;
		int rightRowNumber = 0;
		int rightColumnNumber = 0;
		boolean flag = true;
		for(int r = 0; r< mat.matrixRowNumber; r++){
		   for(int c = 0; c < mat.matrixColumnNumber; c++){
			   if(mat.matrix[r][c]){
				   if(flag){
					   leftRowNumber = r;
					   leftColumnNumber = c;	   
				   } else{
					   rightRowNumber = r;
					   rightColumnNumber = c;	
					   
					   
					   leftRowNumber = rightRowNumber;
					   leftColumnNumber = rightColumnNumber;	
					   
				   }
					   
				   }
			}
		}
		
		
	}
//	
	public Node getRowNode(int r, int c, ColumnNode head){
		// get the column node
		Node columnNode = getNode(c, head, RIGHT);
		// based on column node, get the node with the right row number
		Node node =  getNode(r, columnNode, DOWN);	
		
		return node;
		
	}
//	direction
    private static final int DOWN = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    
    
    
    // when n =1, return the next node of head
	public Node getNode(int n, Node head, int direction){
		int i = 0;
		while(i < n){
		   if (direction == DOWN){
			   head = head.getDown();
		   } else if (direction == UP){
			   head = head.getUp();
		   } else if (direction == RIGHT){
			   head = head.getRight();
		   } else if (direction == LEFT){
			   head = head.getLeft();
		   }	   
		   i++;
		}
		return head;
	}

}
