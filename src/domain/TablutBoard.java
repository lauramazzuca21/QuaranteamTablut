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
	
	public Pair<Integer, Integer> getPawnCount() {

			return new Pair<Integer, Integer>(whitePawns, blackPawns);
	}
	
	@Override
	public int getPawnCount(Pawn pawnType) {

		if (pawnType == Pawn.BLACK)
			return blackPawns;
		
		if (pawnType == Pawn.WHITE)
			return whitePawns;
		
		return -1;
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
	
    public Pair<Integer, Integer> getKingQuadrantPieces() {

        int whitePawnsOnflow = 0;
        int blackPawnsOnFlow = 0;
        
        if (getKingPosition().getX() < 4) {
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 9; j++) {
                    if (getPawn(i, j).equals(Pawn.WHITE))
                        whitePawnsOnflow++;
                    else if (getPawn(i, j).equals(Pawn.BLACK))
                        blackPawnsOnFlow++;
                }
        } else if (getKingPosition().getX() > 4) {
            for (int i = 5; i < 9; i++)
                for (int j = 0; j < 9; j++) {
                    if (getPawn(i, j).equals(Pawn.WHITE))
                        whitePawnsOnflow++;
                    else if (getPawn(i, j).equals(Pawn.BLACK))
                        blackPawnsOnFlow++;
                }

        }
        if (getKingPosition().getY() < 4) {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 4; j++) {
                    if (getPawn(i, j).equals(Pawn.WHITE))
                        whitePawnsOnflow++;
                    else if (getPawn(i, j).equals(Pawn.BLACK))
                        blackPawnsOnFlow++;
                }
        } else if (getKingPosition().getY() > 4) {
            for (int i = 0; i < 9; i++)
                for (int j = 5; j < 9; j++) {
                    if (getPawn(i, j).equals(Pawn.WHITE))
                        whitePawnsOnflow++;
                    else if (getPawn(i, j).equals(Pawn.BLACK))
                        blackPawnsOnFlow++;
                }

        }
        return new Pair<Integer, Integer>(whitePawnsOnflow, blackPawnsOnFlow);
    }

	
	public boolean isKingInDanger(State state) {

		Position kingpos = getKingPosition();
		
		int possibileCattura = countKingSurrounded();

		if (kingpos.getX() == 4) {
			if (kingpos.getY() == 4 && possibileCattura == 3) return true; // trono
			else if (kingpos.getY() == 3 && possibileCattura == 2) return true; //adiacente al trono
			else if (kingpos.getY() == 5 && possibileCattura == 2) return true;
		}//adiacente al trono
		else if (kingpos.getY() == 4) {
			if (kingpos.getX() == 3 && possibileCattura == 2) return true;//adiacente al trono
			else if (kingpos.getX() == 5 && possibileCattura == 2) return true;//adiacente al trono
		} else if (kingpos.getX() == 2 && kingpos.getY() == 4) return true; //accampamento
		else if (kingpos.getX() == 4 && kingpos.getY() == 2) return true;//accampamento
		else if (kingpos.getX() == 6 && kingpos.getY() == 4) return true;//accampamento
		else if (kingpos.getX() == 4 && kingpos.getY() == 6) return true;//accampamento

		else if (possibileCattura == 1) return true;//altro

		else if ((kingpos.getX() == 5 || kingpos.getX() == 3) && (kingpos.getY() == 1 || kingpos.getY() == 7)) return true;
		else if ((kingpos.getX() == 1 || kingpos.getX() == 7) && (kingpos.getY() == 5 || kingpos.getY() == 3)) return true;

		return false;

	}

	
	public boolean isKingReadyToWin(State state) {
		Position kingpos = getKingPosition();
		
		int x, y;
		//se il percorso in orizzontale � libero e non c'� alcun accampamento
		for (x = kingpos.getX()  + 1; x < getDimX(); x++)
			if (!getPawn(x, kingpos.getY()).equals(Pawn.EMPTY) || (getTile(x, kingpos.getY()) == Tile.CAMP)) break;
		
		if (x == getDimX()) return true;
		
		for (x = kingpos.getX() - 1; x >= 0; x--)
			if (!getPawn(x, kingpos.getY()).equals(Pawn.EMPTY) || ((getTile(x, kingpos.getY())==Tile.CAMP))) break;
		if (x < 0) return true;
		
		//se il percorso in verticale � libero e non c'� alcun accampamento
		for (y = kingpos.getY() + 1; y < getDimY(); y++)
			if (!getPawn(kingpos.getX(), y).equals(Pawn.EMPTY) || (getTile(kingpos.getX(), y)==Tile.CAMP)) break;
		
		if (y == getDimX()) return true;
		
		for (y = kingpos.getY() - 1; y >= 0; y--)
			if (!getPawn(kingpos.getX(), y).equals(Pawn.EMPTY) || (getTile(kingpos.getX(), y)==Tile.CAMP)) break;
		if (y < 0) return true;
		
		//altrimenti non � pronto a vincere	
		return false;
	}
	
}