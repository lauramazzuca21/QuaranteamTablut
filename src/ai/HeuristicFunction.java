package ai;
import domain.State;

public interface HeuristicFunction {
	
	public double getStateValue(State state);
}
