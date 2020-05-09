package domain;

import java.util.ArrayList;
import java.util.List;

import enums.Pawn;
import enums.PlayerKind;
import enums.Tile;

public class TablutState extends State {

	public TablutState(TablutBoard board, PlayerKind turnOf, PlayerKind myKind) {
		super(board, turnOf, myKind);
	}

	@Override
	public List<Move> getPossibleMoves() {
		List<Move> result = new ArrayList<Move>();
		Board board = getBoard();
		int dimX = board.getDimX();
		int dimY = board.getDimY();
		
		for (int x = 0; x < dimX; x++)
		{
			for (int y = 0; y < dimY; y++)
			{
				//per evitare di avere due metodi che fanno la stessa cosa, controllo di chi devo controllare le mosse in questo if
				if(  (  getTurnOf() == PlayerKind.WHITE 
						&& (getBoard().getPawn(x, y) == Pawn.WHITE || getBoard().getPawn(x, y) == Pawn.KING)
						)
						|| (getTurnOf() == PlayerKind.BLACK && getBoard().getPawn(x, y) == Pawn.BLACK)   )
				{
					boolean stopPrevHorizontal = false;
					boolean stopPrevVertical = false;
					boolean stopPostHorizontal = false;
					boolean stopPostVertical = false;
					
					//controllo mosse possibili in un unico ciclo
					for (int prevX = x-1, prevY = y-1, postX = x+1, postY = y+1;
							!stopPrevHorizontal || !stopPrevVertical
							|| !stopPostHorizontal || !stopPostVertical; 
							prevX--, prevY--, postX++, postY++)
					{
						
						//celle precedenti
							//in orizzontale...
						if (prevX >= 0 
								&& board.getPawn(prevX, y) == Pawn.EMPTY 
								&& board.getTile(prevX, y) != Tile.CAMP 
								&& board.getTile(prevX, y) != Tile.CASTLE )
						{
							result.add(new Move(x, y, prevX, y));
						}
						else stopPrevHorizontal = true;
							//...e in verticale
						
						if (prevY >= 0
								&&  board.getPawn(x, prevY) == Pawn.EMPTY 
								&& board.getTile(x, prevY) != Tile.CAMP 
								&& board.getTile(x, prevY) != Tile.CASTLE )
						{
							result.add(new Move(x, y, x, prevY));
						}
						else stopPrevVertical = true;
						
						//celle successive
							//in orizzontale...
						if (postX < dimX 
								&&  board.getPawn(postX, y) == Pawn.EMPTY 
								&& board.getTile(postX, y) != Tile.CAMP 
								&& board.getTile(postX, y) != Tile.CASTLE )
						{
							result.add(new Move(x, y, postX, y));
						}
						else stopPostHorizontal = true;
											
							//...e in verticale
						
						if (postY < dimY
								&&  board.getPawn(x, postY) == Pawn.EMPTY 
								&& board.getTile(x, postY) != Tile.CAMP 
								&& board.getTile(x, postY) != Tile.CASTLE )
						{
							result.add(new Move(x, y, x, postY));
						}
						else stopPostVertical = true;
					}// for					
				}//if WHITE or KING
			}//for y
			
		}//for x
		return result;
	}
	
}
