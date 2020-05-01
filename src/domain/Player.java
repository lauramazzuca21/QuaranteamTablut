package domain;

public abstract class Player {
	
	private String id;
	private String kind;
	
	public Player(String id, String kind) {
		this.id = id;
		this.kind = kind;
	}
	
	public String getId() {
		return id;
	}

	public String getKind() {
		return kind;
	}

	public abstract Move getNextMove(State newState);
}
