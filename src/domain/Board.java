package domain;
import enums.Pawn;
import enums.Tile;

public abstract class Board {
	
	private Pawn[][] pawnBoard;
	private Tile[][] tileBoard;
	
	public Pawn[][] getPawnBoard() {
		return pawnBoard;
	}

	protected void setPawnBoard(Pawn[][] pawnBoard) {
		this.pawnBoard = pawnBoard;
	}

	public Tile[][] getTileBoard() {
		return tileBoard;
	}

	protected void setTileBoard(Tile[][] tileBoard) {
		this.tileBoard = tileBoard;
	}

	public Board(int dimX, int dimY) {
		pawnBoard = new Pawn[dimX][dimY];
		tileBoard = new Tile[dimX][dimY];

	}
	
	public void removePawn(int x, int y) {
		pawnBoard[x][y] = Pawn.EMPTY;
	}

}
