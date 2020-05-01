package domain;

public abstract class Game {
	
	protected Player player;
	protected State state;
	
	public Game(Player player) {
		this.player = player;
	}
	
	public abstract void loop(State newState);
	
}
