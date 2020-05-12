package domain;

import java.util.ArrayList;
import java.util.List;

import enums.Loader;
import enums.Pawn;
import enums.Tile;
import utils.BoardLoader;

public class TablutBoard extends Board {
	
	public static int DIM = 9;
	
	private Position kingPosition = new Position(4, 4);
	
	private int whitePawns = 8;
	private int blackPawns = 16;
	
	public int getWhitePawns() {
		return whitePawns;
	}
	
	public int getBlackPawns() {
		return blackPawns;
	}

	public TablutBoard(Loader boardLoader, String source) {
		super(DIM, DIM);
		
		initializeBoard(boardLoader, source);
	}

	public TablutBoard(Pawn[][] pawnBoard, Tile[][] tileBoard) {
		super(pawnBoard, tileBoard);
	}

	private void initializeBoard(Loader boardLoader, String source) {

		BoardLoader loader = BoardLoader.BoardLoaderFactory(boardLoader, source);
		this.setPawnBoard(loader.getPawnBoardSetup());
		this.setTileBoard(loader.getTileBoardSetup());
	}
	
	@Override
	public List<Position> applyMove(Move m) {
		List<Position> eaten = getEatenPawns(m);
		
		if(eaten != null)
		{
			for (Position p : eaten)
			{
				removePawn(p);
			}
		}
		
		if (getPawn(m.getStarting().getX(), m.getStarting().getY()) == Pawn.KING)
			kingPosition = m.getEnding();
		
		super.applyMove(m);
		
		return eaten;
	}
	
	@Override
	public void removePawn(Position position) {
		
		switch(getPawn(position))
		{
			case WHITE:
				whitePawns--;
				break;
			case BLACK:
				blackPawns--;
				break;
			default:
				break;
		}
		
		super.removePawn(position);
	}
	
	public List<Position> getEatenPawns(Move move) {
		Pawn moving = getPawnBoard()[move.getStartX()][move.getStartY()];
		
		switch(moving)
		{
		case WHITE:
		case KING:
			return getWhiteEatenPawns(move);
		case BLACK:
			return getBlackEatenPawns(move);
		default:
			break;
		}
		
		return null;
	}
	
	public boolean isKingCaptured() {
		
		if ( (getTile(kingPosition) == Tile.CASTLE && countKingSurrounded() == 4)
				|| (isKingAdiacentToCastle() && countKingSurrounded() == 3)
				|| countKingSurrounded() == 2)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isKingOnEscapeTile() {
		if (getTile(kingPosition) == Tile.ESCAPE)
		{
			return true;
		}
		return false;
	}
	
	private int countKingSurrounded() {
		int result = 0;
		
		for(Position p : kingPosition.getOrthogonalNeighbors(DIM, DIM))
		{
			if (getPawn(p) == Pawn.BLACK)
			{
				result++;
			}
		}
		
		return result;
	}
	
	private boolean isKingAdiacentToCastle() {
		for(Position p : kingPosition.getOrthogonalNeighbors(DIM, DIM))
		{
			if (getTile(p) == Tile.CASTLE)
			{
				return true;
			}
		}
		
		return false;
	}

	private List<Position> getWhiteEatenPawns(Move move)
	{
		List<Position> eatenPawns = new ArrayList<Position>();
		Position endingPosition = move.getEnding();		
		
		if (getPawn(endingPosition.getNextPositionX(DIM)) == Pawn.BLACK 
				&& (getPawn(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Pawn.WHITE 
				|| getPawn(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Pawn.KING) )
		{
			eatenPawns.add(endingPosition.getNextPositionX(DIM));
		}
		
		if (getPawn(endingPosition.getPreviousPositionX()) == Pawn.BLACK 
				&& (getPawn(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Pawn.WHITE 
				|| getPawn(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Pawn.KING) )
		{
			eatenPawns.add(endingPosition.getPreviousPositionX());
		}
		
		if (getPawn(endingPosition.getNextPositionY(DIM)) == Pawn.BLACK 
				&& (getPawn(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Pawn.WHITE 
				|| getPawn(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Pawn.KING) )
		{
			eatenPawns.add(endingPosition.getNextPositionY(DIM));
		}
		
		if (getPawn(endingPosition.getPreviousPositionY()) == Pawn.BLACK 
				&& (getPawn(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Pawn.WHITE 
				|| getPawn(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Pawn.KING) )
		{
			eatenPawns.add(endingPosition.getPreviousPositionY());
		}
		
		return eatenPawns;
	}
	
	
	private List<Position> getBlackEatenPawns(Move move) {
		List<Position> eatenPawns = new ArrayList<Position>();
		Position endingPosition = move.getEnding();

		if (getPawn(endingPosition.getNextPositionX(DIM)) == Pawn.WHITE 
				&& (getPawn(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Pawn.BLACK 
				|| getTile(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Tile.CAMP
				|| getTile(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Tile.CASTLE) )
		{
			eatenPawns.add(endingPosition.getNextPositionX(DIM));
		}
		
		if (getPawn(endingPosition.getPreviousPositionX()) == Pawn.WHITE 
				&& (getPawn(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Pawn.BLACK 
				|| getTile(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Tile.CAMP
				|| getTile(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Tile.CASTLE) )
		{
			eatenPawns.add(endingPosition.getPreviousPositionX());
		}
		
		if (getPawn(endingPosition.getNextPositionY(DIM)) == Pawn.WHITE 
				&& (getPawn(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Pawn.BLACK 
				|| getTile(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Tile.CAMP
				|| getTile(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Tile.CASTLE) )
		{
			eatenPawns.add(endingPosition.getNextPositionY(DIM));
		}
		
		if (getPawn(endingPosition.getPreviousPositionY()) == Pawn.WHITE 
				&& (getPawn(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Pawn.BLACK 
				|| getTile(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Tile.CAMP
				|| getTile(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Tile.CASTLE) )
		{
			eatenPawns.add(endingPosition.getPreviousPositionY());
		}
		
		return eatenPawns;
	}
	
	public int getPawnCount(Pawn tipo) {
		switch (tipo) {
		case WHITE:
			return whitePawns;
		case BLACK:
			return blackPawns;
		default:
			return -1;
		}
	}
	
	public String toString() {
		String result = "\t0\t1\t2\t3\t4\t5\t6\t7\t8\n";
		
		for (int x = 0; x < 9; x++)
		{
			result += x + "\t";
			for (int y = 0; y < 9; y++)
			{
				result += getPawn(x, y) + "\t";
			}
			result+= "\n";
		}
				
		
		
		return result;
		
	}
	
}