package ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Move;
import domain.Position;
import domain.State;
import enums.GameState;
import enums.PlayerKind;

public class ResearchAlphaBeta {

		
	private Map<Integer, Move> mapMoves;
	private int maxDepth;
	private HeuristicFunction h;
	
	/**
	 * algoritmo minmax con potature alpha-beta a profondità limitata
	 * 
	 * @params maxDepth
	 * 					livelli di profondità da esplorare
	 * @params ts
	 * 				rappresentazione dello stato
	 * @return
	 * 			ritorna la migliore azione 
	 */
	public Move AlphaBetaSearch(HeuristicFunction h, int  maxDepth, State ts) {

		this.h = h;
		mapMoves = new HashMap<Integer, Move>();
		this.maxDepth = maxDepth;
		
		if(ts.getTurnOf().equals(PlayerKind.WHITE)) {	//MAX player
			int v = MaxValue(maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, ts);
			return mapMoves.get(v);	//si recupera l'azione con il valore v più alto
		}
		else if(ts.getTurnOf().equals(PlayerKind.BLACK)) {	//MIN player
			int v = MinValue(maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, ts);
			return mapMoves.get(v);	//si recupera l'azione con il valore v più basso
		}			
			return null;
	}
 
		 
		
		
		/**
	 * funzione di valutazione del valore massimo di AlphaBetaSearch
	 * 
	 * @param depth
	 * @param alpha
	 * @param beta
	 * @param state
	 * @return
	 */
	
	//@Matteo manca il confronto sul percorso minimo , forse la mappa non basta , per ora provo a usare depth in cutoff
	private int MaxValue(int depth, int alpha, int beta, State state) {
		//all'interuzione si ritorna un valore 
		if (cutoff(depth, state)) {
			return h.getStateValue(state) - depth;
		}
		int tmp;
		int v = Integer.MIN_VALUE;

		List<Move> moves = state.getPossibleMoves(PlayerKind.WHITE);

		for (Move m : moves) {											//= per ogni coppia <azione, stato>
			//create childstate
			List<Position> eaten = state.applyMove(m);
			

			tmp=MinValue(depth - 1, alpha, beta, state);
			
			state.undoMove(m, eaten);
			
				//tmp strettamente maggiore di v altirmenti inserisce nella mappa anche i valori infiniti
			if(this.maxDepth == depth && tmp > v) {
				this.mapMoves.put(tmp, m);					
				//ci si salva in mappa le coppie <valore, mossa> del primo livello di profondità
			}


			v = Math.max(v, tmp);
			if (v >= beta) {
				return v;
			}

			alpha = Math.max(alpha, v);
		}
		return v;
	

	}

	/**
	 * funzione di valutazione del valore minimo di AlphaBetaSearch
	 * 
	 * @param depth
	 * @param alpha
	 * @param beta
	 * @param state
	 * @return
	 */
	private int MinValue(int depth, int alpha, int beta, State state) {
		//all'interuzione si ritorna un valore 
		if (cutoff(depth, state)) {
			return h.getStateValue(state) + depth;
		}
		int tmp;
		int v = Integer.MAX_VALUE;

		
		
		List<Move> moves = state.getPossibleMoves(PlayerKind.BLACK);
		
		for (Move m : moves) {											
			//create childstate
//			State childState = state.deepCopy();
			List<Position> eaten = state.applyMove(m);
			
			tmp=MaxValue(depth - 1, alpha, beta, state);
			
			state.undoMove(m, eaten);
			
			if(this.maxDepth==depth && tmp < v) {
				this.mapMoves.put(tmp, m);	
				//ci si salva in mappa le coppie <valore, mossa> del primo livello di profondità
			}
			
			v = Math.min(v,tmp);
			if (v <= alpha) {
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;

	}
	
	/**
	 * funzione per fermarsi nella ricerca in profondità di AlphaBetaSearch
	 * 
	 * @param depth
	 * @param state
	 * @return
	 */
	private boolean cutoff(int depth, State state) {
		//ci si blocca quando si raggiunge una certa profondità o si è in un nodo
		//foglia -> quindi si è determinato una vittoria o sconfitta o pareggio
			return depth <= 0 || (state.getGameState() != GameState.PLAYING);
		}
	
}
