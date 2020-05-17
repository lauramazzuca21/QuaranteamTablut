package ai;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;
import java.util.function.Function;

import domain.Move;
import domain.Pair;
import domain.Position;
import domain.State;
import enums.GameState;
import enums.PlayerKind;

public class IterativeDeepeningSearch implements ResearchAlgorithm {

	private int minDepth;
	private int maxDepth;
	
	private int currentMaxDepth;
	private Move currentBestMove;
	
	private TreeMap<Integer, Move> mapMoves;
	private TreeMap<Integer, Move> tempMoves;
	long now;
	HeuristicFunction h;
		
	private Function<Pair<Long, Long>, Boolean> limit;
	
	
	public IterativeDeepeningSearch(int minDepth, int maxDepth, Function<Pair<Long, Long>, Boolean> limit) {
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
		this.limit = limit;
		h = new HeuristicTablut();
		mapMoves = new TreeMap<Integer, Move>();
		tempMoves = new TreeMap<Integer, Move>();
	}
	
	@Override
	public Move getNextMove(State newState) {
		if (newState.getTurnOf() == PlayerKind.WHITE) {
			mapMoves = new TreeMap<Integer, Move>(Collections.reverseOrder());
			tempMoves = new TreeMap<Integer, Move>(Collections.reverseOrder());
		}
		
		Collection<Move> possibleMoves = newState.getPossibleMoves();
		now = System.currentTimeMillis();
		for (int currentDepth = minDepth; currentDepth <= maxDepth && !limit.apply(new Pair<Long, Long>(System.currentTimeMillis(), now)); currentDepth+=2) {
			currentMaxDepth = currentDepth;
					
			if(newState.getTurnOf().equals(PlayerKind.WHITE)) {	//MAX player
				MaxValue(currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, newState, possibleMoves);
			}
			else if(newState.getTurnOf().equals(PlayerKind.BLACK)) {	//MIN player
				MinValue(currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, newState, possibleMoves);
			}
			
			if (!tempMoves.isEmpty())
			{
				mapMoves.putAll(tempMoves);
			}
			
			currentBestMove = mapMoves.get(mapMoves.firstKey());
		
			possibleMoves = mapMoves.values();
		}
		System.out.println("depth reached: " + currentMaxDepth);
		
		return currentBestMove;
	}
	
	private int MaxValue(int depth, int alpha, int beta, State state, Collection<Move> firstLevelMoves) {
		//all'interuzione si ritorna un valore 
		if (cutoff(depth, state)) {
			//System.out.println("[MAX] CUTOFF" );
			return h.getStateValue(state) - depth;
		}
		Integer v = null;
		
		v = Integer.MIN_VALUE;
		
		//System.out.println("[MAX] Depth: " + depth );
		//System.out.println("[MAX] Alpha: " + alpha );
		//System.out.println("[MAX] Beta: " + beta );
		
		Collection<Move> moves = depth == currentMaxDepth ? firstLevelMoves : state.getPossibleMoves(PlayerKind.WHITE);
		//System.out.println("[MAX] Possible Moves: " + moves.size() );
		for (Move m : moves) {
			Integer tmp = null;
			Position[] eaten = state.applyMove(m);
			
//			if ((tmp = TranspositionTable.getInstance().getValue(state.toString(), depth)) == null) {
				tmp = MinValue(depth - 1, alpha, beta, state, null);
//			}
			
			state.undoMove(m, eaten);
			
			//tmp strettamente maggiore di v altirmenti inserisce nella mappa anche i valori infiniti
			if(this.currentMaxDepth == depth && tmp > v) {
				if(currentMaxDepth == minDepth)
					this.mapMoves.put(tmp, m);	
				else
					this.tempMoves.put(tmp, m);	
				//ci si salva in mappa le coppie <valore, mossa> del primo livello di profondità
			}


			v = Math.max(v, tmp);
			if (v >= beta) {
				//System.out.println("[MAX] Return Value: " + v );
				//System.out.println("[MAX] Time elapsed: " + (System.currentTimeMillis() - now) );
//				TranspositionTable.getInstance().add(state.toString(), depth, v);
				return v;
			}

			alpha = Math.max(alpha, v);
		}
		
		//System.out.println("[MAX] Return Value: " + v );
		//System.out.println("[MAX] Time elapsed: " + (System.currentTimeMillis() - now) );
//		TranspositionTable.getInstance().add(state.toString(), depth, v);

		return v;
	

	}

	private int MinValue(int depth, int alpha, int beta, State state, Collection<Move> firstLevelMoves) {
		//all'interuzione si ritorna un valore 
		if (cutoff(depth, state)) {
			//System.out.println("[MIN] CUTOFF" );
			return h.getStateValue(state) + depth;
		}
		int v = Integer.MAX_VALUE;

		//System.out.println("[MIN] Depth: " + depth );
		//System.out.println("[MIN] Alpha: " + alpha );
		//System.out.println("[MIN] Beta: " + beta );
		
		Collection<Move> moves = depth == currentMaxDepth ? firstLevelMoves : state.getPossibleMoves(PlayerKind.BLACK);
		
		//System.out.println("[MIN] Possible Moves: " + moves.size() );
		for (Move m : moves) {											
			Integer tmp = null;
			Position[] eaten = state.applyMove(m);
//			if ((tmp = TranspositionTable.getInstance().getValue(state.toString(), depth)) == null) {		
				tmp=MaxValue(depth - 1, alpha, beta, state, null);
//			} 
			
			state.undoMove(m, eaten);
			
			if(this.currentMaxDepth==depth && tmp < v) {
				if(currentMaxDepth == minDepth)
					this.mapMoves.put(tmp, m);	
				else
					this.tempMoves.put(tmp, m);	
				//ci si salva in mappa le coppie <valore, mossa> del primo livello di profondità
			}
			
			v = Math.min(v,tmp);
			if (v <= alpha) {
				//System.out.println("[MIN] Return Value: " + v );
				//System.out.println("[MIN] Time elapsed: " + (System.currentTimeMillis() - now) );
//				TranspositionTable.getInstance().add(state.toString(), depth, v);
				return v;
			}
			beta = Math.min(beta, v);
		}
		//System.out.println("[MIN] Return Value: " + v );
		//System.out.println("[MIN] Time elapsed: " + (System.currentTimeMillis() - now) );
//		TranspositionTable.getInstance().add(state.toString(), depth, v);
		return v;

	}
	
	private boolean cutoff(int depth, State state) {
		//ci si blocca quando si raggiunge una certa profondità o si è in un nodo
		//foglia -> quindi si è determinato una vittoria o sconfitta o pareggio
			return depth <= 0 
					|| (state.getGameState() != GameState.PLAYING)
					|| limit.apply(new Pair<Long, Long>(System.currentTimeMillis(), now))
					;
		}

}
