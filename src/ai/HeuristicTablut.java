package ai;

import domain.Pair;
import domain.State;
import domain.TablutBoard;
import enums.GameState;
import enums.Pawn;
import enums.PlayerKind;

public class HeuristicTablut implements HeuristicFunction{

	/**
	 * funzione euristica:
	 * - alti valori per i bianchi
	 * - bassi valori per i neri
	 *
	 * @param state
	 * stato da valutare
	 * @return un valore di stima dello stato
	 */

	//da adattare una volta finalizzato PVS
	private static final int WEIGTH1 = 500;
	private static final int WEIGTH2 = 800;
	private static final int WEIGTH3 = 40;
	private static final int WEIGTH4 = 20;
	private static final int WEIGTH5 = 1;
	private static final int WEIGTH6 = 1;
	private static final int WEIGTH7 = 15;
	private static final int WEIGTH8 = 5;
	private static final int[][] position_weight = {
	        {   0, 1000, 1000,   0,    0,   0, 1000, 1000,    0},
	        {1000,  300,  200, 100,    0, 100,  200,  300, 1000},
	        {1000,  200,  200, 200,  200, 200,  200,  200, 1000},
	        {   0,  100,  200,   0,    0,   0,  200,  100,    0},
	        {   0,    0,  200,   0,-1000,   0,  200,    0,    0},
	        {   0,  100,  200,   0,    0,   0,  200,  100,    0},
	        {1000,  300,  200, 200,  200, 200,  200,  300, 1000},
	        {1000,  100,  200, 100,    0, 100,  200,  100, 1000},
	        {   0, 1000, 1000,   0,    0,   0, 1000, 1000,    0},
	};

	
	public int getStateValue(State state) {		
		TablutBoard tb = (TablutBoard) state.getBoard();
		
		int result = 0;
		
		if (state.getGameState() == GameState.WIN) 
		{
			result = 10000;
		}
		else if (state.getGameState() == GameState.LOSE)
		{
			result = -10000;
		}
		else if (state.getGameState() == GameState.DRAW)
			result = 5000;
		else {
			if (tb.isKingReadyToWin(state))
				result = result + WEIGTH1;
				
			if (tb.isKingInDanger(state))
				result = result - WEIGTH2;
			
			// valutazioni sul numero di pezzi
			result = result + (tb.getPawnCount(Pawn.WHITE) * WEIGTH3);
			result = result - (tb.getPawnCount(Pawn.BLACK) * WEIGTH4);
			
			
			
			//considerazioni sulle pedine nella zona del re
			Pair<Integer, Integer> pawnsInFlow = tb.getKingQuadrantPieces();
			result += pawnsInFlow.getFirst() * WEIGTH5;
			result -= pawnsInFlow.getSecond() * WEIGTH6;
			// considerazioni sulle pedine in diagonale rispetto al re
	//		result += tb.getPawnsOnKingDiagonal() * WEIGTH7;
	//		result += tb.getPawnsOnKingDiagonal2() * WEIGTH8;
		}
		return state.getTurnOf() == PlayerKind.WHITE ? result : -result;
	}	
	
}

