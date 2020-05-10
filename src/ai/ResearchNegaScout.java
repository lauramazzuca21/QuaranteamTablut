package ai;

import domain.State;

public class ResearchNegaScout {
/*
	int NegaScout ( State state, int alpha, int beta )
	{                     // compute minimax value of position p 
	   int b, t, i;
	   if ( d == maxdepth )
	      return quiesce(p, alpha, beta);         //leaf node
	   determine successors p_1,...,p_w of p;
	   b = beta;
	   for ( i = 1; i <= w; i++ ) {
	      t = -NegaScout ( p_i, -b, -alpha );
	      if ( (t > a) && (t < beta) && (i > 1) )
	         t = -NegaScout ( p_i, -beta, -alpha ); // re-search 
	      alpha = max( alpha, t );
	      if ( alpha >= beta )
	         return alpha;                           // cut-off 
	      b = alpha + 1;                  // set new null window 
	   }
	   return alpha;
	}

	int Quiesce( int alpha, int beta ) {
	    int stand_pat = Evaluate();
	    if( stand_pat >= beta )
	        return beta;
	    if( alpha < stand_pat )
	        alpha = stand_pat;

	    until( every_capture_has_been_examined )  {
	        MakeCapture();
	        score = -Quiesce( -beta, -alpha );
	        TakeBackMove();

	        if( score >= beta )
	            return beta;
	        if( score > alpha )
	           alpha = score;
	    }
	    return alpha;
	}
*/
}
