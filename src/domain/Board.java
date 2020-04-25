package domain;
import utils.Pawn;
import utils.Tile;

public abstract class Board {
	
	private Pawn[][] pawnBoard;
	private Tile[][] tileBoard;
	
	public Board(int dimX, int dimY) {
		pawnBoard = new Pawn[dimX][dimY];
		tileBoard = new Tile[dimX][dimY];

	}

}
