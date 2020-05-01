package domain;

public abstract class Game {
	
	private String player;
	
	public Game(String player) {
		this.player = player;
	}
	
	public abstract void loop();
	
}
