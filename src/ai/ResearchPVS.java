package ai;
import domain.Game;
import domain.State;
import enums.PlayerKind;
import domain.Move;

public class ResearchPVS {
	HeuristicFunction heuristic= new HeuristicTablut();
	
	public int pvSearch(State state, int alpha, int beta, int depth ) {
		int score = 0;
		
		if( depth == 0 ) return quiesce(state, alpha, beta );
		
		boolean bSearchPv = true;
		for (Move m : state.getPossibleMoves(state.getTurnOf()))  { //da creare
			State newState = state;
			newState.getBoard().applyMove(m);
			
			if ( bSearchPv ) {
				score = -pvSearch(newState, -beta, -alpha, depth - 1);
			} 
			else {
				score = -pvSearch(newState, -alpha-1, -alpha, depth - 1);
				if ( score > alpha ) // in fail-soft ... && score < beta ) is common
					score = -pvSearch(newState, -beta, -alpha, depth - 1); // re-search
			}
			
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

	    for(Move m : state.getPossibleMoves(state.getTurnOf())) {
	        State childState = state;
	        childState.getBoard().applyMove(m);
	        int score = -quiesce(childState, -beta, -alpha );

	        if( score >= beta )
	            return beta;
	        if( score > alpha )
	           alpha = score;
	    }
	    return alpha;
	}

}

