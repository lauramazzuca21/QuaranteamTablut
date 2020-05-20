package domain;

import com.google.gson.JsonObject;

public class Move implements JsonSerializable {

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

	@Override
	public String toJson() {
		//Formato: 0 colonna, 1 riga
		String to = Integer.toString(this.getFinalY()) + Integer.toString(this.getFinalX());
		String from = Integer.toString(this.getStartY()) + Integer.toString(this.getStartX());
		JsonObject obj = new JsonObject();
		obj.addProperty("from",from);
		obj.addProperty("to",to);
		return obj.toString();
	}
	
	@Override
	public JsonObject toJsonObject() {
		//Formato: 0 colonna, 1 riga
		String from = getBox(this.getStartX(), this.getStartY());
		String to = getBox(this.getFinalX(), this.getFinalY());	
		JsonObject obj = new JsonObject();
		obj.addProperty("from",from);
		obj.addProperty("to",to);
		return obj;
	}
	
	public String getBox(int row, int column) {
		String ret;
		char col = (char) (column + 97);
		ret = col + "" + (row + 1);
		return ret;
	}

	@Override
	public void fromJson(String jsonString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fromJson(JsonObject jsonObj) {
		// TODO Auto-generated method stub
		
	}
	
}
