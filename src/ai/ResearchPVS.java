package ai;
import domain.Game;
import domain.State;
import domain.Move;

public class ResearchPVS {
	
	public int pvSearch( int alpha, int beta, int depth ) {
		int score = 0;
		
		if( depth == 0 ) return quiesce( alpha, beta );
			boolean bSearchPv = true;
		for (Move m : State.getAllLegalMoves())  { //da creare
			make
			if ( bSearchPv ) {
				score = -pvSearch(-beta, -alpha, depth - 1);
			} 
			else {
				score = -pvSearch(-alpha-1, -alpha, depth - 1);
				if ( score > alpha ) // in fail-soft ... && score < beta ) is common
					score = -pvSearch(-beta, -alpha, depth - 1); // re-search
			}
			unmake
			if( score >= beta )
				return beta;   // fail-hard beta-cutoff
			if( score > alpha ) {
				alpha = score; // alpha acts like max in MiniMax
				bSearchPv = false;  // *1)
			}
		}
		return alpha; // fail-hard
	}
	
	private int quiesce( int alpha, int beta ) {
	    int stand_pat = Evaluate();
	    if( stand_pat >= beta )
	        return beta;
	    if( alpha < stand_pat )
	        alpha = stand_pat;

	    until( every_capture_has_been_examined )  {
	        MakeCapture();
	        score = -quiesce( -beta, -alpha );
	        TakeBackMove();

	        if( score >= beta )
	            return beta;
	        if( score > alpha )
	           alpha = score;
	    }
	    return alpha;
	}
	
}
