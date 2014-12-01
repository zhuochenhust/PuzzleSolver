import java.util.ArrayList;

public class ToMatrix {
	boolean[][] matrix;
	public ToMatrix(PuzzleBoard board, Tile[] tiles){
		ArrayList<boolean[]> rawMatrix = new ArrayList<boolean[]>();
		boolean[] row = new boolean[board.size() + tiles.length];
		
		for(int k=0; k < tiles.length; k++){
			for(int r=0; r < board.height(); r++){
				for(int c=0; c < board.width(); c++){
					if(board.putPiece(tiles[k],r,c)){
						row = board.getRow(tiles.length, tiles[k].id);
						rawMatrix.add(row);					
					}
					board.removeTile(tiles[k],r,c);	
				}
			}
		}
		
		System.out.println(rawMatrix.get(0));
		System.out.println(rawMatrix.get(1));
		System.out.println(rawMatrix.size());
		
	}

}
