package domain;

import java.util.ArrayList;
import java.util.List;

import enums.GameState;
import enums.Pawn;
import enums.PlayerKind;
import enums.Tile;

public class TablutState extends State {

	private ArrayList<String> boardHistory;
	
	public TablutState(Board board, PlayerKind myKind) {
		super(board, myKind);
		boardHistory = new ArrayList<String>();
		boardHistory.add(board.toString());
		this.setGameState(GameState.PLAYING);
		this.setTurnOf(PlayerKind.WHITE);
	}
	
	@Override
	public List<Move> getPossibleMoves(PlayerKind playerKind) {
		List<Move> result = new ArrayList<Move>();
		Board board = getBoard();
		int dimX = board.getDimX();
		int dimY = board.getDimY();
		
		for (int x = 0; x < dimX; x++)
		{
			for (int y = 0; y < dimY; y++)
			{
				//per evitare di avere due metodi che fanno la stessa cosa, controllo di chi devo controllare le mosse in questo if
				if(  (  playerKind == PlayerKind.WHITE 
						&& (board.getPawn(x, y) == Pawn.WHITE || board.getPawn(x, y) == Pawn.KING)
						)
						|| (playerKind == PlayerKind.BLACK && board.getPawn(x, y) == Pawn.BLACK)   )
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
		
		
//		return result;
		return removeRepeatingConfigurationMoves(result);
	}
	
	private List<Move> removeRepeatingConfigurationMoves(List<Move> moves) {
		
		List<Move> result = new ArrayList<Move>();
		
		for (Move m : moves) {
			Board temp = this.getBoard().clone();
			
			temp.applyMove(m);
			
			if (!checkRepeatingBoardConfiguration(temp))
			{
				result.add(m);
			}
		}
		return result;
	}
	
	public boolean checkRepeatingBoardConfiguration(Board current) {
		
		for (String b : boardHistory)
		{
			if (b.equals(current.toString()))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void applyMove(Move nextMove) {
		getBoard().applyMove(nextMove);

		updateGameState();
		
		boardHistory.add(this.getBoard().toString());
		this.setTurnOf(getTurnOf() == PlayerKind.WHITE ? PlayerKind.BLACK : PlayerKind.WHITE);
		
	}

	private void updateGameState() {
		TablutBoard tb = (TablutBoard) getBoard();
		boolean kingCaptured = tb.isKingCaptured();
		boolean kingEscaped = tb.isKingOnEscapeTile();
		
		if ( (kingCaptured && this.getMyKind() == PlayerKind.BLACK)
			|| (kingEscaped && this.getMyKind() == PlayerKind.WHITE) )
		{
			this.setGameState(GameState.WIN);
		}
		else if ( (kingCaptured && this.getMyKind() == PlayerKind.WHITE)
				|| (kingEscaped && this.getMyKind() == PlayerKind.BLACK) 
				|| getPossibleMoves(getMyKind()).size() == 0)
		{
			this.setGameState(GameState.LOSE);
		}
		else if (checkRepeatingBoardConfiguration(tb))
		{
			this.setGameState(GameState.DRAW);
		}
		
	}

	@Override
	public List<Move> getPossibleMoves() {
		return getPossibleMoves(getMyKind());
	}

	@Override
	public boolean hasWon(PlayerKind playerKind) {
		switch(playerKind) {
		case WHITE:
			if ( (this.getGameState() == GameState.WIN && this.getMyKind() == PlayerKind.WHITE)
					|| (this.getGameState() == GameState.LOSE && this.getMyKind() == PlayerKind.BLACK) )
			{
				return true;
			}
			return false;
		case BLACK:
			if ( (this.getGameState() == GameState.WIN && this.getMyKind() == PlayerKind.BLACK)
					|| (this.getGameState() == GameState.LOSE && this.getMyKind() == PlayerKind.WHITE) )
			{
				return true;
			}
			return false;
		}
		
		
		return false;
	}
	
	public String toString() {
		return getBoard().toString();
	}

	@Override
	public void undoMove(Move nextMove) {
		
	}
}
