package domain;

import enums.Loader;
import enums.Pawn;
import enums.Tile;
import utils.BoardLoader;

public class TablutBoard extends Board {
	
	public static int DIM = 9;
	
	private Position kingPosition = new Position(4, 4);
	
	private int whitePawns = 8;
	private int blackPawns = 16;
	
	public int getWhitePawnNumber() {
		return whitePawns;
	}
	
	public int getBlackPawnNumber() {
		return blackPawns;
	}
	
	public Position getKingPosition() {
		return kingPosition;
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
	public Position[] applyMove(Move m) {
		Position[] eaten = getEatenPawns(m);
		
		if(eaten != null)
		{
			for (Position p : eaten)
			{
				if (p != null) removePawn(p);
			}
		}
		
		if (getPawn(m.getStarting()) == Pawn.KING)
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
	
	public Position[] getEatenPawns(Move move) {
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
		int kingSurrounded = countKingSurrounded();
		if ( (getTile(kingPosition) == Tile.CASTLE && kingSurrounded == 4)
				|| (isKingAdiacentToCastle() && kingSurrounded == 3)
				|| kingSurrounded == 2 && (surroundedOnX(kingPosition) || surroundedOnY(kingPosition))
				|| surroundedAdiacentToCitadel(kingPosition))
		{
			return true;
		}
		
		return false;
	}
	
	private boolean surroundedAdiacentToCitadel(Position position) {
		Position[] neighborsH = position.getHorizontalNeighbors(DIM, DIM);
		Position[] neighborsV = position.getVerticalNeighbors(DIM, DIM);
		
		return (getPawn(neighborsH[0]) == Pawn.BLACK && getTile(neighborsH[1]) == Tile.CAMP)
				|| (getPawn(neighborsH[1]) == Pawn.BLACK && getTile(neighborsH[0]) == Tile.CAMP)
				|| (getPawn(neighborsV[0]) == Pawn.BLACK && getTile(neighborsV[1]) == Tile.CAMP)
				|| (getPawn(neighborsV[1]) == Pawn.BLACK && getTile(neighborsV[0]) == Tile.CAMP);
	}
	
	private boolean surroundedOnX(Position position) {
		Pawn type = getPawn(position);
		int surround = 0;
		for (Position p : position.getHorizontalNeighbors(DIM, DIM))
		{
			Pawn onSide = getPawn(p);
			if ( (onSide == Pawn.BLACK && type == Pawn.WHITE) || (onSide == Pawn.WHITE && type == Pawn.BLACK)) {
				surround++;
			}
		}
		
		return surround == 2;
	}
	
	private boolean surroundedOnY(Position position) {
		Pawn type = getPawn(position);
		int surround = 0;
		for (Position p : position.getVerticalNeighbors(DIM, DIM))
		{
			Pawn onSide = getPawn(p);
			if ( (onSide == Pawn.BLACK && type == Pawn.WHITE) || (onSide == Pawn.WHITE && type == Pawn.BLACK)) {
				surround++;
			}
		}
		
		return surround == 2;
	}

	public boolean isKingOnEscapeTile() {
		if (getTile(kingPosition) == Tile.ESCAPE)
		{
			return true;
		}
		return false;
	}
	
	public int countKingSurrounded() {
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

	private Position[] getWhiteEatenPawns(Move move)
	{
		Position[] eatenPawns = new Position[4];
		Position endingPosition = move.getEnding();		
		
		int idx = 0;
		
		if (getPawn(endingPosition.getNextPositionX(DIM)) == Pawn.BLACK 
				&& (getPawn(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Pawn.WHITE
					|| (getTile(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Tile.CAMP
						&& getTile(endingPosition.getNextPositionX(DIM)) == Tile.EMPTY)
					|| getPawn(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Pawn.KING) )
		{
			eatenPawns[idx] = endingPosition.getNextPositionX(DIM);
			idx++;
		}
		
		if (getPawn(endingPosition.getPreviousPositionX()) == Pawn.BLACK 
				&& (getPawn(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Pawn.WHITE
				|| (getTile(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Tile.CAMP
						&& getTile(endingPosition.getPreviousPositionX()) == Tile.EMPTY)
				|| getPawn(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Pawn.KING) )
		{
			eatenPawns[idx] = endingPosition.getPreviousPositionX();
			idx++;
		}
		
		if (getPawn(endingPosition.getNextPositionY(DIM)) == Pawn.BLACK 
				&& (getPawn(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Pawn.WHITE 
					|| (getTile(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Tile.CAMP
						&& getTile(endingPosition.getNextPositionY(DIM)) == Tile.EMPTY)
				|| getPawn(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Pawn.KING) )
		{
			eatenPawns[idx] = endingPosition.getNextPositionY(DIM);
			idx++;
		}
		
		if (getPawn(endingPosition.getPreviousPositionY()) == Pawn.BLACK 
				&& (getPawn(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Pawn.WHITE 
						|| (getTile(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Tile.CAMP
						&& getTile(endingPosition.getPreviousPositionY()) == Tile.EMPTY)
				|| getPawn(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Pawn.KING) )
		{
			eatenPawns[idx] = endingPosition.getPreviousPositionY();
			idx++;
		}
		
		return idx == 0 ? null : eatenPawns;
	}
	
	
	private Position[] getBlackEatenPawns(Move move) {
		Position[] eatenPawns = new Position[4];
		Position endingPosition = move.getEnding();

		int idx = 0;
		
		if (getPawn(endingPosition.getNextPositionX(DIM)) == Pawn.WHITE 
				&& (getPawn(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Pawn.BLACK 
						|| (getTile(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Tile.CAMP
						&& getTile(endingPosition.getNextPositionX(DIM)) == Tile.EMPTY)
				|| getTile(endingPosition.getNextPositionX(DIM).getNextPositionX(DIM)) == Tile.CASTLE) )
		{
			eatenPawns[idx] = endingPosition.getNextPositionX(DIM);
			idx++;
		}
		
		if (getPawn(endingPosition.getPreviousPositionX()) == Pawn.WHITE 
				&& (getPawn(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Pawn.BLACK 
						|| (getTile(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Tile.CAMP
						&& getTile(endingPosition.getPreviousPositionX()) == Tile.EMPTY)
				|| getTile(endingPosition.getPreviousPositionX().getPreviousPositionX()) == Tile.CASTLE) )
		{
			eatenPawns[idx] = endingPosition.getPreviousPositionX();
			idx++;
		}
		
		if (getPawn(endingPosition.getNextPositionY(DIM)) == Pawn.WHITE 
				&& (getPawn(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Pawn.BLACK 
						|| (getTile(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Tile.CAMP
						&& getTile(endingPosition.getNextPositionY(DIM)) == Tile.EMPTY)
				|| getTile(endingPosition.getNextPositionY(DIM).getNextPositionY(DIM)) == Tile.CASTLE) )
		{
			eatenPawns[idx] = endingPosition.getNextPositionY(DIM);
			idx++;
		}
		
		if (getPawn(endingPosition.getPreviousPositionY()) == Pawn.WHITE 
				&& (getPawn(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Pawn.BLACK 
					|| (getTile(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Tile.CAMP
					&& getTile(endingPosition.getPreviousPositionY()) == Tile.EMPTY)
				|| getTile(endingPosition.getPreviousPositionY().getPreviousPositionY()) == Tile.CASTLE) )
		{
			eatenPawns[idx] = endingPosition.getPreviousPositionY();
			idx++;
		}
		
		return idx == 0 ? null : eatenPawns;
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
		StringBuilder builder = new StringBuilder();
		builder.append("\t0\t1\t2\t3\t4\t5\t6\t7\t8\n");
		
		for (int x = 0; x < 9; x++)
		{
			builder.append(x);
			builder.append("\t");
			for (int y = 0; y < 9; y++)
			{
				builder.append(getPawn(x, y));
				builder.append("\t");
			}
			builder.append("\n");
		}
				
		
		
		return builder.toString();
		
	}

	@Override
	public void undoMove(Move m, Position[] eaten) {
		
		Pawn pawnType = getPawn(m.getEnding());
		
		super.removePawn(m.getEnding());
		
		getPawnBoard()[m.getStartX()][m.getStartY()] = pawnType;
		
		if (pawnType == Pawn.KING)
			kingPosition = m.getStarting();
		
		if( eaten == null)
				return;
		
		for (int i = 0; i < eaten.length; i++)
		{
			if (eaten[i] == null)
				break;
			Pawn eatenPawnType = pawnType == Pawn.WHITE || pawnType == Pawn.KING ? Pawn.BLACK : Pawn.WHITE;
			getPawnBoard()[eaten[i].getX()][eaten[i].getY()] = eatenPawnType;
			if (eatenPawnType == Pawn.BLACK) blackPawns++;
			else whitePawns++;
		}
	}
	
}