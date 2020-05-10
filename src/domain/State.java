package domain;

import java.util.List;

import enums.GameState;
import enums.PlayerKind;

public abstract class State {

	private Board board;
	private PlayerKind turnOf;
	private PlayerKind myKind;
	
	public State(Board board, PlayerKind turnOf, PlayerKind myKind) {
		this.board = board;
		this.turnOf = turnOf;
		this.myKind = myKind;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public PlayerKind getTurnOf() {
		return turnOf;
	}

	public void setTurnOf(PlayerKind turnOf) {
		this.turnOf = turnOf;
	}
	
	public GameState getGameState() {
		return currentState;
	}
	
	public PlayerKind getMyKind() {
		return myKind;
	}
	
	public void setGameState(GameState currentState) {
		this.currentState = currentState;
	}
	
	public abstract boolean hasWon(PlayerKind playerKind);

	public abstract void applyMove(Move nextMove);

	public abstract List<Move> getPossibleMoves();
	public abstract List<Move> getPossibleMoves(PlayerKind playerKind);

	
}
