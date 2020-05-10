package domain;

public class Move {

	private Position starting;
	private Position ending;
	
	public int getStartX() {
		return starting.getX();
	}
	
	public void setStartX(int startX) {
		this.starting.setX(startX);
	}
	
	public Move(Position starting, Position ending) {
		super();
		this.starting = starting;
		this.ending = ending;
	}

	public Position getStarting() {
		return starting;
	}

	public void setStarting(Position starting) {
		this.starting = starting;
	}

	public Position getEnding() {
		return ending;
	}

	public void setEnding(Position ending) {
		this.ending = ending;
	}

	public int getStartY() {
		return starting.getY();
	}
	
	public void setStartY(int startY) {
		this.starting.setY(startY);
	}
	
	public int getFinalX() {
		return ending.getX();
	}
	
	public void setFinalX(int finalX) {
		this.ending.setX(finalX);
	}
	
	public int getFinalY() {
		return ending.getY();
	}
	
	public void setFinalY(int finalY) {
		this.ending.setX(finalY);
	}
	
	public void setStartingPosition(int x, int y) {
		this.starting.setX(x);
		this.starting.setY(y);
	}
	
	public void setFinalPosition(int x, int y) {
		this.ending.setX(x);
		this.ending.setY(y);
	}

	public Move(int startX, int startY, int finalX, int finalY) {
		super();
		this.starting = new Position(startX, startY);
		this.ending = new Position(finalX, finalY);
	}
	
	public String toString() {
		return "move from " + starting.getX() + ", " + starting.getY() + " to " + getEnding().getX() + ", " + getEnding().getY();
	}
	
}
