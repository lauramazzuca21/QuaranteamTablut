package domain;
import enums.Turn;

public abstract class Player {
	
	private String id;
	private Turn turn;
	
	public Player(String id, Turn Turn) {
		this.id = id;
		this.turn = turn;
	}
	
	public String getId() {
		return id;
	}

	public Turn getTurn() {
		return turn;
	}

	public abstract Move getNextMove(State newState);
}
