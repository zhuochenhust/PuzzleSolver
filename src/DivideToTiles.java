import java.util.ArrayList;


public class DivideToTiles {

	private char[][] inputArray;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private Tile maxTile = null;
	private Tile minTile = null;

	public DivideToTiles (ArrayList<char[]> inList ) {
	    this.inputArray = new char[inList.size()][];
		for (int i = inList.size()-1; i >= 0; i-- )
		 inputArray[i] = inList.get(i);
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public Tile getMaxTile() {
		return maxTile;
	}

	public Tile getMinTile() {
		return minTile;
	}

	public void parse() {
		int i, j;
		Tile tile;
		for (j = 0; j < inputArray.length; j++) {
			for (i = 0; i < inputArray[j].length; i++) {
				if (inputArray[j][i] != Tile.BLANK) {
					// detect & explore raw Tile and clear the points using ' '
					tile = new Tile();
					explore(inputArray, i, j, tile);
					tiles.add(tile);
					if (maxTile == null) {
						maxTile = tile;
						minTile = tile;
					} else if (tile.compareTo(maxTile) > 0) {
						maxTile = tile;
					} else if (tile.compareTo(minTile) < 0) {
						minTile = tile;
					}
				}
			}
		}
	}

	private void explore(char[][] inputArray, int x, int y, Tile tile) {
		tile.addPoint(x, y, inputArray[y][x]);
		inputArray[y][x] = Tile.BLANK;

		if (withinBound(inputArray, x - 1, y - 1) && inputArray[y - 1][x - 1] != Tile.BLANK) {
			explore(inputArray, x - 1, y - 1, tile);
		}
		if (withinBound(inputArray, x - 1, y) && inputArray[y][x - 1] != Tile.BLANK) {
			explore(inputArray, x - 1, y, tile);
		}
		if (withinBound(inputArray, x - 1, y + 1) && inputArray[y + 1][x - 1] != Tile.BLANK) {
			explore(inputArray, x - 1, y + 1, tile);
		}

		if (withinBound(inputArray, x, y - 1) && inputArray[y - 1][x] != Tile.BLANK) {
			explore(inputArray, x, y - 1, tile);
		}
		if (withinBound(inputArray, x, y + 1) && inputArray[y + 1][x] != Tile.BLANK) {
			explore(inputArray, x, y + 1, tile);
		}

		if (withinBound(inputArray, x + 1, y - 1) && inputArray[y - 1][x + 1] != Tile.BLANK) {
			explore(inputArray, x + 1, y - 1, tile);
		}
		if (withinBound(inputArray, x + 1, y) && inputArray[y][x + 1] != Tile.BLANK) {
			explore(inputArray, x + 1, y, tile);
		}
		if (withinBound(inputArray, x + 1, y + 1) && inputArray[y + 1][x + 1] != Tile.BLANK) {
			explore(inputArray, x + 1, y + 1, tile);
		}
	}

	private boolean withinBound(char[][] inputArray, int x, int y) {
		return y >= 0 && y < inputArray.length && x >= 0 && x < inputArray[y].length;
	}
}
