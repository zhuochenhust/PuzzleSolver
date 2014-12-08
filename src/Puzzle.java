import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;


public class Puzzle {
	
	public static final int DEGREE90 = 90;
	public static final int DEGREE180 = 180;
	public static final int DEGREE270 = 270;
	
	public static final int ReflectionOnX = 1;
	public static final int ReflectionOnY = 0;
	
	public static final int[] ROTATION = {DEGREE90, DEGREE180, DEGREE270};
	public static final int[] REFLECTION = {ReflectionOnX, ReflectionOnY};
	
	public int[][] solution;
	public ArrayList<int[][]> solutions = new ArrayList<int[][]>();
	public int solutionSize;
	
	ToMatrix mat;
	
	// Puzzle's head in doubled link
	Node head = new Node(-1);
	Solver solver;
	
	private PuzzleBoard board;
	private Tile[] tiles;
	private ArrayList<Tile> tilesList;
	
	public Tile[] originalTiles;
	Tile maxTile;
	int tileNumber; // the number of originial tiles
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
		this.tilesList = toTiles.getTiles();
		tilesList.remove(toTiles.getMaxTile());
		tileNumber = tilesList.size();
		originalTiles = new Tile[tileNumber];
		for(int i=0; i< tileNumber; i++){
			tilesList.get(i).standize();
			tilesList.get(i).setId(i);
			originalTiles[i] = tilesList.get(i);
			
		}
		//Get the biggest tile as board
		// Assign the tile as max tile
		maxTile = toTiles.getMaxTile();
		maxTile.standize();
		board = new PuzzleBoard(0, maxTile);
		
		
	}

	public void solver(boolean enabledOneSolution, boolean enabledRotation, boolean enabledReflection, IMonitor monitor){
		// Initilize the class variable
		this.initilization();
			
		ArrayList<Tile> transformedTileList = new ArrayList<Tile>();
		ArrayList<Tile> uniqueTransformedTileList = new ArrayList<Tile>();
		ArrayList<Tile> AllTransformedTileList = new ArrayList<Tile>();
		ArrayList<Tile> roatedTileList = new ArrayList<Tile>();

		
//		boolean enabledRotation = true;
//		boolean enabledReflection = true;
		
//		boolean enabledRotation = false;
//		boolean enabledReflection = false;

//		boolean enabledRotation = true;
//		boolean enabledReflection = false;
//		boolean enabledRotation = false;
//		boolean enabledReflection = true;
		System.out.println("Rotation: " + enabledRotation + " Reflection: " + enabledReflection);
        
		long startTime = System.currentTimeMillis();
		
		for(int i=0; i < tileNumber; i++){
			// Standize tiles in the list and set id
		
			if(enabledRotation){
			  for(int rotateDirection: ROTATION){
				 if (! tilesList.get(i).equals(tilesList.get(i).rotateTile(rotateDirection))){
				  transformedTileList.add(tilesList.get(i).rotateTile(rotateDirection));
				  roatedTileList.add(tilesList.get(i).rotateTile(rotateDirection));
				 }
			  }
			}
			if(enabledReflection){
			 if(roatedTileList != null){
				for (Tile roatedTile : roatedTileList){
					  for(int reflectDirection: REFLECTION){
							 if (! roatedTile.equals(roatedTile.reflectTile(reflectDirection))
									 && ! tilesList.get(i).equals(roatedTile.reflectTile(reflectDirection))){
								 transformedTileList.add(roatedTile.reflectTile(reflectDirection));
							  } 
							}	
					
				}
			 }	
			
				  for(int reflectDirection: REFLECTION){
				 if (! tilesList.get(i).equals(tilesList.get(i).reflectTile(reflectDirection))){
					  transformedTileList.add(tilesList.get(i).reflectTile(reflectDirection));
				  } 
				}			
			}
			
			int len = transformedTileList.size();
			for(int j=0; j < len; j++){
				if(! this.contains(uniqueTransformedTileList, transformedTileList.get(j))){
					uniqueTransformedTileList.add(transformedTileList.get(j));
					AllTransformedTileList.add(transformedTileList.get(j).copy());
				}	
			 }
			//clear the tile list on last iteration
			transformedTileList.clear();
			uniqueTransformedTileList.clear();
			roatedTileList.clear();
		}			
		
		for (Tile oneTile: AllTransformedTileList){
				tilesList.add(oneTile);
		}
		
		tiles = new Tile[tilesList.size()];	
		for(int i=0; i < tilesList.size(); i++){			
			tiles[i] = tilesList.get(i);
		}
		
		System.out.println("Tile Number:" + tilesList.size());
		// tiles[0].printTile();
		
		
		// Set head's id as -1
		Node lastColumnNode = new Node(-1);
		// get Matrix
		mat = new ToMatrix(board, tiles, tileNumber);
		
		
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
						rowNode.setColumnNode(columnNode);
						flag =false;
	                   //set upRowNode					
						upRowNode = rowNode;
						count =1;
					} else {
						rowNode = new Node(columnNode.getColumnId());
						rowNode.setRowId(r);
						upRowNode.setDownNode(rowNode);
						rowNode.setUpNode(upRowNode);	
						rowNode.setColumnNode(columnNode);
						//update upRowNode
						upRowNode = rowNode;
						count++;
					}
			     }		
		     }
			 columnNode.setNodeNumber(count);	
			 //System.out.println("Count: " + count);
		}
		//Print out Matrix
		//mat.printMatrix();
		
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
		
//		System.out.println("Print unchanged list: ");
//		printNet(head);
//		Node column = head.getRight();
		solver =  new Solver(head, monitor, board, mat, this);
		this.transformSolution();
		
//		for(int j=0; j < solutionSize; j++){
//		   OnProgress(IMonitor.NEW_SOL);
//		}
		//solver.printSolution();
//		System.out.println("Print list after covering the first column: ");
//		printNet(head);
//		solver.uncover(column);
//		System.out.println("Print list after uncovering the first column: ");
//		printNet(head);
		this.totalTime = System.currentTimeMillis() - startTime;
	
	}
	
	
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
	
	public boolean contains(ArrayList<Tile> oneRotatedTileList, Tile rotatedTile){
		for(Tile tile: oneRotatedTileList){
			if(tile.equals(rotatedTile)){
				return true;
			}
		}
		return false;
	}
	
	public Tile[] getTiles() {
//		Tile[] originalTiles = new Tile[this.tilesList.size()];
//		for(int i=0; i< this.tilesList.size(); i++){
//			originalTiles[i] = tilesList.get(i);
//		}
		return this.originalTiles;
	}
	public PuzzleBoard getBoard(){
		return this.board;
	}
	public Tile getMaxTile(){
		return this.maxTile;
	}
	
	// transform the solution from boolean array to readable board
	public void transformSolution(){
        this.solutionSize = this.solver.solutions.size();
		
		for(ArrayList<Node> oneSolution: this.solver.solutions ){		
			solution = new int[board.height()][board.width()];
			for(int i=0; i< oneSolution.size();i++){
				solution = putTileRowInBoard(i , mat.matrix[oneSolution.get(i).rowId],solution);
			}
			solutions.add(solution);
		}
	}
	
	// return the solutions
	public ArrayList<int[][]> getSolutions(){
		return this.solutions;
	}
	// put one Tile row into the board for drawing
	public int[][] putTileRowInBoard(int id, boolean[] row, int[][] drawBoard ){
		int count = 0; 
		for(int i=0; i<board.height; i++) {
			for(int j=0;  j< board.width; j++) {
				if(board.board[i][j].c != Tile.BLANK && row[count + tileNumber ]){
					drawBoard[i][j] = id;
					count++;
				}
			}
		}	
		return drawBoard;	
	}
	
	long totalTime = 0;
	long deadEndTime = 0;

	public long getTotalTime() {
		return totalTime;
	}
	

	
	public void initilization(){
		solutions.clear();
		solutionSize=0;
		// Puzzle's head in doubled link
		head = new Node(-1);
		tilesList.clear();
		
		for(Tile tile: originalTiles){
			tilesList.add(tile);
		}
	
	}
   
}
