package domain;

import enums.Turn;

public abstract class State {

	private Board board;
	private Turn turnOf;
	
	public State(Board board, Turn turnOf) {
		this.board = board;
		this.turnOf = turnOf;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Turn getTurnOf() {
		return turnOf;
	}

	public void setTurnOf(Turn turnOf) {
		this.turnOf = turnOf;
	}
	
	
}
