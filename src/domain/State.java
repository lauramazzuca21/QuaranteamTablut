package domain;

public abstract class State {

	private Board board;
	private String turnOf;
	
	public State(Board board, String turnOf) {
		this.board = board;
		this.turnOf = turnOf;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getTurnOf() {
		return turnOf;
	}

	public void setTurnOf(String turnOf) {
		this.turnOf = turnOf;
	}
	
	
}
