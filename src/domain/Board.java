package domain;
import enums.Pawn;
import enums.Tile;

public abstract class Board {
	
	private Pawn[][] pawnBoard;
	private Tile[][] tileBoard;
	
	private int dimX;
	private int dimY;

	public Board(int dimX, int dimY) {
		pawnBoard = new Pawn[dimX][dimY];
		tileBoard = new Tile[dimX][dimY];
		
		this.dimX = dimX;
		this.dimY = dimY;

	}
	
	public int getDimX() {
		return dimX;
	}

	public int getDimY() {
		return dimY;
	}

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
	
	public void removePawn(int x, int y) {
		pawnBoard[x][y] = Pawn.EMPTY;
	}
	
	public void removePawn(Position position) {
		pawnBoard[position.getX()][position.getY()] = Pawn.EMPTY;
	}
	
	public void applyMove(Move move) {
		Pawn toMove = pawnBoard[move.getStartX()][move.getStartY()];
		
		removePawn(move.getStarting());
		
		pawnBoard[move.getFinalX()][move.getFinalY()] = toMove;
	}
	
	public Pawn getPawn(int x, int y) {
		return pawnBoard[x][y];
	}
	
	public Tile getTile(int x, int y)
	{
		return tileBoard[x][y];
	}
	
	public Pawn getPawn(Position position) {
		return pawnBoard[position.getX()][position.getY()];
	}
	
	public Tile getTile(Position position) {
		return tileBoard[position.getX()][position.getY()];
	}

	public abstract int getPawnCount(Pawn pawnType);

}
