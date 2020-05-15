package ai;
import java.util.List;

import domain.State;
import domain.Move;
import domain.Position;

public class ResearchPVS {
	HeuristicFunction heuristic= new HeuristicTablut();
	
	public int pvSearch(State state, int alpha, int beta, int depth ) {
		int score = 0;
		
		if( depth == 0 ) return quiesce(state, alpha, beta );
		
		boolean bSearchPv = true;
		for (Move m : state.getPossibleMoves())  { 
			State newState = state;
			Position[] eaten = newState.applyMove(m);
			
			if ( bSearchPv ) {
				score = -pvSearch(newState, -beta, -alpha, depth - 1);
			} 
			else {
				score = -pvSearch(newState, -alpha-1, -alpha, depth - 1);
				if ( score > alpha ) // in fail-soft ... && score < beta ) is common
					score = -pvSearch(newState, -beta, -alpha, depth - 1); // re-search
			}
			newState.undoMove(m, eaten);
			if( score >= beta )
				return beta;   // fail-hard beta-cutoff
			if( score > alpha ) {
				alpha = score; // alpha acts like max in MiniMax
				bSearchPv = false;  // *1)
			}
		}
		return alpha; // fail-hard
	}
	
	private int quiesce(State state, int alpha, int beta ) {
	    int stand_pat = heuristic.getStateValue(state);
	    if( stand_pat >= beta )
	        return beta;
	    if( alpha < stand_pat )
	        alpha = stand_pat;

	    for(Move m : state.getPossibleMoves()) {
	        State childState = state;
	        Position[] eaten = childState.applyMove(m);
	        int score = -quiesce(childState, -beta, -alpha );
	        childState.undoMove(m, eaten);

	        if( score >= beta )
	            return beta;
	        if( score > alpha )
	           alpha = score;
	    }
	    return alpha;
	}

}

