package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import enums.GameState;
import enums.Pawn;
import enums.PlayerKind;
import enums.Tile;

public class TablutState extends State {

	private Stack<String> boardHistory;
	String currentBoard;
		
	public TablutState(Board board) {
		super(board, PlayerKind.WHITE);
		setCurrentBoard();
		boardHistory = new Stack<String>();
		this.setGameState(GameState.PLAYING);
	}
	
	@Override
	public List<Move> getPossibleMoves(PlayerKind playerKind) {
		List<Move> result = new ArrayList<Move>();
		TablutBoard board = (TablutBoard)getBoard();
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
//					boolean stopPrevHorizontal = false;
//					boolean stopPrevVertical = false;
//					boolean stopPostHorizontal = false;
//					boolean stopPostVertical = false;
					Position current = new Position(x, y);
					
					for (int prevX = x-1; prevX >= 0; prevX--) {
						//celle precedenti
						//in orizzontale...
						if ( board.getPawn(prevX, y) == Pawn.EMPTY 
							&&( (board.getBlackPawnsInCentralCitadels().containsKey(current)
									&& !board.getBlackPawnsInCentralCitadels().get(current)) 
								|| board.getTile(prevX, y) != Tile.CAMP )
							&& board.getTile(prevX, y) != Tile.CASTLE )
						{
							result.add(new Move(x, y, prevX, y));
						}
						else break;
					}
					
					for (int prevY = y-1; prevY >= 0; prevY--) {
						if (board.getPawn(x, prevY) == Pawn.EMPTY 
							&&( (board.getBlackPawnsInCentralCitadels().containsKey(current)
									&& !board.getBlackPawnsInCentralCitadels().get(current)) 
								|| board.getTile(x, prevY) != Tile.CAMP )
							&& board.getTile(x, prevY) != Tile.CASTLE )
						{
							result.add(new Move(x, y, x, prevY));
						}
						else break;
					}
					
					for (int postX = x+1; postX < dimX; postX++) {
						if (board.getPawn(postX, y) == Pawn.EMPTY 
								&&( (board.getBlackPawnsInCentralCitadels().containsKey(current)
										&& !board.getBlackPawnsInCentralCitadels().get(current)) 
									|| board.getTile(postX, y) != Tile.CAMP )
								&& board.getTile(postX, y) != Tile.CASTLE )
						{
							result.add(new Move(x, y, postX, y));
						}
						else break;
					}
					
					for (int postY = y+1; postY < dimY; postY++) {
						if (board.getPawn(x, postY) == Pawn.EMPTY 
								&&( (board.getBlackPawnsInCentralCitadels().containsKey(current)
										&& !board.getBlackPawnsInCentralCitadels().get(current)) 
									|| board.getTile(x, postY) != Tile.CAMP )
								&& board.getTile(x, postY) != Tile.CASTLE )
						{
							result.add(new Move(x, y, x, postY));
						}
						else break;
					}
					
					
					//controllo mosse possibili in un unico ciclo
//					for (int prevX = x-1, prevY = y-1, postX = x+1, postY = y+1;
//							!stopPrevHorizontal || !stopPrevVertical
//							|| !stopPostHorizontal || !stopPostVertical; 
//							prevX--, prevY--, postX++, postY++)
//					{
//						
//						//celle precedenti
//							//in orizzontale...
//						if (prevX >= 0 
//								&& !stopPrevHorizontal
//								&& board.getPawn(prevX, y) == Pawn.EMPTY 
//								&& board.getTile(prevX, y) != Tile.CAMP 
//								&& board.getTile(prevX, y) != Tile.CASTLE )
//						{
//							result.add(new Move(x, y, prevX, y));
//						}
//						else stopPrevHorizontal = true;
//							//...e in verticale
//						
//						if (prevY >= 0
//								&& !stopPrevVertical
//								&&  board.getPawn(x, prevY) == Pawn.EMPTY 
//								&& board.getTile(x, prevY) != Tile.CAMP 
//								&& board.getTile(x, prevY) != Tile.CASTLE )
//						{
//							result.add(new Move(x, y, x, prevY));
//						}
//						else stopPrevVertical = true;
//						
//						//celle successive
//							//in orizzontale...
//						if (postX < dimX 
//								&& !stopPostHorizontal
//								&&  board.getPawn(postX, y) == Pawn.EMPTY 
//								&& board.getTile(postX, y) != Tile.CAMP 
//								&& board.getTile(postX, y) != Tile.CASTLE )
//						{
//							result.add(new Move(x, y, postX, y));
//						}
//						else stopPostHorizontal = true;
//											
//							//...e in verticale
//						
//						if (postY < dimY
//								&& !stopPostVertical
//								&&  board.getPawn(x, postY) == Pawn.EMPTY 
//								&& board.getTile(x, postY) != Tile.CAMP 
//								&& board.getTile(x, postY) != Tile.CASTLE )
//						{
//							result.add(new Move(x, y, x, postY));
//						}
//						else stopPostVertical = true;
//					
//					}// for					
				}//if WHITE or KING
			}//for y
			
		}//for x
		
		return result;
	}
	
	public boolean checkRepeatingBoardConfiguration(String current) {
		boardHistory.trimToSize();
		for (int i = 0; i< boardHistory.size(); i++)
		{
			if (boardHistory.get(i).equals(current))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Position[] applyMove(Move nextMove) {
	
		Position[] eaten = getBoard().applyMove(nextMove);
		boardHistory.push(currentBoard);
		setCurrentBoard();
		
		updateGameState();
				
		this.setTurnOf(getTurnOf() == PlayerKind.WHITE ? PlayerKind.BLACK : PlayerKind.WHITE);
		return eaten;
		
	}

	private void updateGameState() {
		TablutBoard tb = (TablutBoard) getBoard();
		boolean kingCaptured = tb.isKingCaptured();
		boolean kingEscaped = tb.isKingOnEscapeTile();
		
		if ( (kingCaptured && this.getTurnOf() == PlayerKind.BLACK)
			|| (kingEscaped && this.getTurnOf() == PlayerKind.WHITE) )
		{
			this.setGameState(GameState.WIN);
		}
		else if ( (kingCaptured && this.getTurnOf() == PlayerKind.WHITE)
				|| (kingEscaped && this.getTurnOf() == PlayerKind.BLACK) 
				|| getPossibleMoves(getTurnOf()).size() == 0)
		{
			this.setGameState(GameState.LOSE);
		}
		else if (checkRepeatingBoardConfiguration(currentBoard))
		{
			this.setGameState(GameState.DRAW);
		}
			
	}

	@Override
	public List<Move> getPossibleMoves() {
		return getPossibleMoves(getTurnOf());
	}

	@Override
	public boolean hasWon(PlayerKind playerKind) {
		switch(playerKind) {
		case WHITE:
			if ( (this.getGameState() == GameState.WIN && this.getTurnOf() == PlayerKind.BLACK)
					|| (this.getGameState() == GameState.LOSE && this.getTurnOf() == PlayerKind.WHITE) )
			{
				return true;
			}
			return false;
		case BLACK:
			if ( (this.getGameState() == GameState.WIN && this.getTurnOf() == PlayerKind.WHITE)
					|| (this.getGameState() == GameState.LOSE && this.getTurnOf() == PlayerKind.BLACK) )
			{
				return true;
			}
			return false;
		}
		
		
		return false;
	}
	
	public String toString() {
		return currentBoard;
	}

	private void setCurrentBoard() {
		currentBoard = this.getTurnOf() + "\n" + this.getBoard().toString();
	}
	
	@Override
	public void undoMove(Move nextMove, Position[] eaten) {
		
		boardHistory.pop();
		getBoard().undoMove(nextMove, eaten);
				
		this.setGameState(GameState.PLAYING);
		
		this.setTurnOf(getTurnOf() == PlayerKind.WHITE ? PlayerKind.BLACK : PlayerKind.WHITE);
		setCurrentBoard();
			
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void fromJson(String jsonString) {
		// 		EMPTY("O"), WHITE("W"), BLACK("B"), THRONE("T"), KING("K");
		JsonObject obj = new JsonParser().parse(jsonString).getAsJsonObject();
		switch(obj.get("turn").toString()) {
			case "W": {
				this.setTurnOf(PlayerKind.WHITE);
			}
			case "B": {
				this.setTurnOf(PlayerKind.BLACK);
			}
			case "BW": {
				if (getTurnOf()==PlayerKind.WHITE)
					this.setGameState(GameState.LOSE);
				if (getTurnOf()==PlayerKind.BLACK)
					this.setGameState(GameState.WIN);
			}
			case "WW": {
				if (getTurnOf()==PlayerKind.WHITE)
					this.setGameState(GameState.WIN);
				if (getTurnOf()==PlayerKind.BLACK)
					this.setGameState(GameState.LOSE);
			}			
			case "D": {
				this.setGameState(GameState.DRAW);
			}
		
		}
		this.getBoard().fromJson(obj);
	}

	@Override
	public void fromJson(JsonObject jsonObj) {
		// TODO Auto-generated method stub
		
	}
}
