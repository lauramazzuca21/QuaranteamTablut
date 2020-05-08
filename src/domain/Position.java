package domain;

import java.util.LinkedList;
import java.util.List;

public class Position {

	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Position getNextPositionX(int xBound) {
		if (x+1 >= xBound)
		{
			return null;
		}
		
		return new Position(x+1, y);
	}
	
	public Position getNextPositionY(int yBound) {
		if (y+1 >= yBound)
		{
			return null;
		}
		
		return new Position(x, y+1);
	}
	
	public Position getPreviousPositionX() {
		if (x-1 < 0)
		{
			return null;
		}
		
		return new Position(x-1, y);
	}
	
	public Position getPreviousPositionY() {
		if (y-1 < 0)
		{
			return null;
		}
		
		return new Position(x, y-1);
	}
	
	public List<Position> getOrthogonalNeighbors(int xBound, int yBound) {
		List<Position> positions = new LinkedList<Position>();
	
		if (getNextPositionX(xBound) != null) positions.add(getNextPositionX(xBound));
		if (getNextPositionY(yBound) != null) positions.add(getNextPositionY(yBound));
		if (getPreviousPositionX() != null) positions.add(getPreviousPositionX());
		if (getPreviousPositionY() != null) positions.add(getPreviousPositionY());
		
		return positions;
	}
	
	public List<Position> getHorizontalNeighbors(int xBound, int yBound) {
		List<Position> positions = new LinkedList<Position>();
	
		if (getNextPositionX(xBound) != null) positions.add(getNextPositionX(xBound));
		if (getPreviousPositionX() != null) positions.add(getPreviousPositionX());
		
		return positions;
	}
	
	public List<Position> getVerticalNeighbors(int xBound, int yBound) {
		List<Position> positions = new LinkedList<Position>();
	
		if (getNextPositionY(yBound) != null) positions.add(getNextPositionY(yBound));
		if (getPreviousPositionY() != null) positions.add(getPreviousPositionY());
		
		return positions;
	}
}
