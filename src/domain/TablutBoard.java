package domain;

import java.util.LinkedList;
import java.util.List;

import enums.Loader;
import enums.Pawn;
import enums.Tile;
import utils.BoardLoader;

public class TablutBoard extends Board {
	
	public static int DIM = 9;
	
	private Position kingPosition;

	public TablutBoard(Loader boardLoader, String source) {
		super(DIM, DIM);
		
		initializeBoard(boardLoader, source);
	}

	private void initializeBoard(Loader boardLoader, String source) {

		BoardLoader loader = BoardLoader.BoardLoaderFactory(boardLoader, source);
		this.setPawnBoard(loader.getPawnBoardSetup());
		this.setTileBoard(loader.getTileBoardSetup());
		kingPosition = new Position(4, 4);
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
				|| (kingAdiacentToCastle() && countKingSurrounded() == 3)
				|| countKingSurrounded() == 2)
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
	
	private boolean kingAdiacentToCastle() {
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
		List<Position> eatenPawns = new LinkedList<Position>();
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
		List<Position> eatenPawns = new LinkedList<Position>();
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
	
	//più efficente se non si conta lungo il bordo? quindi un 7x7 invece di un 9x9
	public int getPawnCount(Pawn tipo) {
		int xAxis = getDimX();
		int yAxis = getDimY();
		Pawn p;
		int pCounter=0;
		
		for(int i = 0; i<xAxis; i++) {
			for(int j = 0; j<yAxis;j++) {
				p=getPawn(xAxis, yAxis);
				if(p== tipo) {
					pCounter++;
				}
			}
		}
		
		return pCounter;
	}
	
}