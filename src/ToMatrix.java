import java.util.ArrayList;

public class ToMatrix {
	boolean[][] matrix;
	int rowNumber = 0;
	int columnNumber = 0;
	int matrixRowNumber =0;
	int matrixColumnNumber = 0;
	public ToMatrix(PuzzleBoard board, Tile[] tiles){
		ArrayList<boolean[]> rawMatrix = new ArrayList<boolean[]>();
		this.columnNumber = board.size() + tiles.length;
		boolean[] row = new boolean[this.columnNumber];
		
		for(int k=0; k < tiles.length; k++){
			for(int r=0; r < board.height(); r++){
				for(int c=0; c < board.width(); c++){
					//put tile on the board and test whether it is legal or not.
					if(board.putPiece(tiles[k],r,c)){
						row = board.getRow(tiles.length, tiles[k].id);
						rawMatrix.add(row);
						this.rowNumber ++;					
					}
					// remove tile record on the board
					board.removeTile(tiles[k],r,c);	
				}
			}
		}
		this.matrixRowNumber = rawMatrix.size();
		this.matrixColumnNumber = rawMatrix.get(0).length;
		this.matrix = new boolean[matrixRowNumber][matrixColumnNumber]; 
		for (int i=0; i< matrixRowNumber; i++){
			for (int j=0; j < matrixColumnNumber;j++){
				matrix[i][j] = rawMatrix.get(i)[j];
			}
		}
		
//		System.out.println(rawMatrix.get(0));
//		System.out.println(rawMatrix.get(1));
//		System.out.println(rawMatrix.size());
		
	}
	
	public void printMatrix(){	
		for (int i=0; i< matrixRowNumber ; i++){
			System.out.println("");
			for (int j=0; j < matrixColumnNumber;j++){
				System.out.print(" " + (matrix[i][j]?1:0));
			}
		}
		System.out.println();
	}

}
