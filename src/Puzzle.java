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
		
		// tiles[0].printTile();
		
		
		// Set head's id as -1
		Node head = new Node(-1);
//		Node leftColumnNode;
//		Node rightColumnNode;
		Node lastColumnNode = new Node(-1);
		ToMatrix mat = new ToMatrix(board, tiles);
		for(int c = 0; c < mat.matrixColumnNumber; c++){
			Node columnNode = new Node(c);
			columnNode.setRowId(-1);
			if (c == 0){
				head.setRightNode(columnNode);
				columnNode.setLeftNode(head);
				lastColumnNode = columnNode;
			} else {
				lastColumnNode.setRightNode(columnNode);
				columnNode.setLeftNode(lastColumnNode);
				lastColumnNode = columnNode;
			}
			boolean flag = true;
			Node rowNode;
			Node upRowNode = new Node(columnNode.getColumnId());
			int count =0;
			
			for(int r = 0; r< mat.matrixRowNumber; r++){
				//only let the first 1 in a column, execute the statements inside if
				if(mat.matrix[r][c]){
					if(flag){
						rowNode= new Node(columnNode.getColumnId());
						rowNode.setRowId(r);
						columnNode.setDownNode(rowNode);
						rowNode.setUpNode(columnNode);	
						flag =false;
	                   //set upRowNode					
						upRowNode = rowNode;
						count =1;
					} else {
						rowNode = new Node(columnNode.getColumnId());
						rowNode.setRowId(r);
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
		
		mat.printMatrix();
		int leftRowNumber = 0;
		int leftColumnNumber = 0;
		int rightRowNumber = 0;
		int rightColumnNumber = 0;
		
		Node rightNode;
		Node leftNode;
		
		boolean flag = true;
		for(int r = 0; r< mat.matrixRowNumber; r++){
		   for(int c = 0; c < mat.matrixColumnNumber; c++){
			   if(mat.matrix[r][c]){
				   if(flag){
					   leftRowNumber = r;
					   leftColumnNumber = c;	
					   flag = false;
				   } else{
					   rightRowNumber = r;
					   rightColumnNumber = c;	
					   
					   //connect left and right node
					   leftNode = getNode(leftRowNumber,leftColumnNumber, head, mat.matrix);
					   rightNode = getNode(rightRowNumber,rightColumnNumber, head, mat.matrix);
					   
					   leftNode.setRightNode(rightNode);
					   rightNode.setLeftNode(leftNode);
					   
					   leftRowNumber = rightRowNumber;
					   leftColumnNumber = rightColumnNumber;	
					   
				   }
					   
				   }
			}
		    flag = true; // each iteration needs clear the old one.
 
		}
		
		printNet(head);
		
		
	}
//	
	public Node getNode(int r, int c, Node head, boolean[][] matrix){
		// get the column node
		Node columnNode = getColumnNode(c, head, RIGHT);
		int count=0;
		// based on column node, get the node with the right row number
		for (int i =0; i <= r; i++ ){
			if (matrix[i][c]){
				count++;
			}	
		}
		Node node = getRowNode(count, columnNode, DOWN);	
		
		return node;
		
	}
//	direction
    private static final int DOWN = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    
    
    
    // when n =1, return the next node of head
	public Node getRowNode(int n, Node head, int direction){
		int i = 0;
		while(i < n){
		   if (direction == DOWN){
			   head = head.getDown();
		   } else if (direction == UP){
			   head = head.getUp();
		   }	   
		   i++;
		}
		return head;
	}
	public Node getColumnNode(int n, Node head, int direction){
		int i = -1;
		while(i < n){
		   if (direction == RIGHT){
			   head = head.getRight();
		   } else if (direction == LEFT){
			   head = head.getLeft();
		   }	   
		   i++;
		}
		return head;
	}
	
	
	//test result to get the right connection among the row
	public void printNet(Node head){
		Node column = head.getRight();;
		Node row;
		while(column != null){
		   row = column.getDown();
		   while(row != null){
			        Node temprow = row;
					if (temprow.getLeft() == null){
						int count =1;
						while(temprow.getRight() != null ){
							temprow = temprow.getRight();
							count++;
						}
						System.out.println("Row Count:" + count);
					}
			      row = row.getDown();
				}
		   column = column.getRight();
		}
	}

}
