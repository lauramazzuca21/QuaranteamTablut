package ai;
import domain.Board;

public interface HeuristicFunction {
	
	public double getStateValue(Board state);
}
