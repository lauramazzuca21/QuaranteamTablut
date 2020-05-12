package domain;
import java.util.Arrays;
import java.util.List;

import enums.Pawn;
import enums.Tile;

public abstract class Board implements Cloneable {
	
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
	
	public Board(Pawn[][] pawnBoard, Tile[][] tileBoard) {
		this.pawnBoard = pawnBoard;
		this.tileBoard = tileBoard;
		
		this.dimX = tileBoard.length;
		this.dimY = tileBoard[0].length;

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
	
	public List<Position> applyMove(Move move) {
		Pawn toMove = pawnBoard[move.getStartX()][move.getStartY()];
		
		removePawn(move.getStarting());
		
		pawnBoard[move.getFinalX()][move.getFinalY()] = toMove;
		
		return null;
	}
	
	public Pawn getPawn(int x, int y) {
		return pawnBoard[x][y];
	}
	
	public Tile getTile(int x, int y)
	{
		return tileBoard[x][y];
	}
	
	public Pawn getPawn(Position position) {
		if (position == null)
			return Pawn.EMPTY;
		return pawnBoard[position.getX()][position.getY()];
	}
	
	public Tile getTile(Position position) {
		if (position == null)
			return Tile.EMPTY;
		return tileBoard[position.getX()][position.getY()];
	}
	
	public Board deepCopy() {
		return this.clone();
	}

	@Override
	protected Board clone() {	
		Board result = null;
		
		try {
			result = (Board) super.clone();
			Pawn[][] pawnBoard = new Pawn[dimX][dimY];
			 for (int i = 0; i < getPawnBoard().length; i++) {
				 pawnBoard[i] = Arrays.copyOf(getPawnBoard()[i], getPawnBoard()[i].length);
			    }
			 result.setPawnBoard(pawnBoard);
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public abstract int getPawnCount(Pawn pawnType);

	public void undoMove(Move m, List<Position> eaten) {
		
		Pawn pawnType = getPawn(m.getEnding());
		
		removePawn(m.getEnding());
		
		pawnBoard[m.getStartX()][m.getStartY()] = pawnType;
		
		if( eaten == null)
				return;
		
		for (Position p : eaten)
		{
			pawnBoard[p.getX()][p.getY()] = pawnType == Pawn.WHITE || pawnType == Pawn.KING ? Pawn.BLACK : Pawn.WHITE;
		}
	}
	
}
