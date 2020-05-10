package ai;
import domain.State;

public interface HeuristicFunction {
	
	public int getStateValue(State state);
}
