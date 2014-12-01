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
		
		ToMatrix matrix = new ToMatrix(board, tiles);
	}

}
