package ai;
import domain.Move;
import domain.State;

public interface ResearchAlgorithm {
	
	public Move getNextMove(State newState);
	
}
