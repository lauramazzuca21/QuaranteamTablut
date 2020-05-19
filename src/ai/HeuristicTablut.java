package ai;

import domain.Pair;
import domain.Position;
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
	private static final int WEIGTH2 = 1000;
	private static final int WEIGTH3 = 40;
	private static final int WEIGTH4 = 20;
	private static final int WEIGTH5 = 1;
	private static final int WEIGTH6 = 1;
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
		
		if (state.hasWon(PlayerKind.WHITE)) 
		{
			result = 10000;
		}
		else if (state.hasWon(PlayerKind.BLACK))
		{
			result = -10000;
		}
		else if (state.getGameState() == GameState.DRAW)
			result = 1000;
		else {
			result = state.getTurnOf() == PlayerKind.WHITE ? -9000 : 9000;
		else 
		{
			if (tb.isKingReadyToWin())
				result = result + WEIGTH1;
			else {
				result -= tb.pawnsBlockingKingVictory() * WEIGTH4;
			}
				
			if (tb.isKingInDanger())
				result = result - WEIGTH2;
			
			// valutazioni sul numero di pezzi
			result = result + (tb.getPawnCount(Pawn.WHITE) * WEIGTH3);
			result = result - (tb.getPawnCount(Pawn.BLACK) * WEIGTH4);

	
	//		//considerazioni sulle pedine nella zona del re
			Pair<Integer, Integer> pawnsInFlow = tb.getKingQuadrantPieces();
			result += pawnsInFlow.getFirst() * WEIGTH5;
			result -= pawnsInFlow.getSecond() * WEIGTH6;
			
//			//considerazioni sulle pedine nella zona del re
//			Pair<Integer, Integer> pawnsInFlow = tb.getKingQuadrantPieces();
//			result += pawnsInFlow.getFirst() * WEIGTH5;
//			result -= pawnsInFlow.getSecond() * WEIGTH6;
//			// considerazioni sulle pedine in diagonale rispetto al re
//			result += state.getPawnsOnKingDiagonal() * WEIGTH7;
//			result += state.getPawnsOnKingDiagonal2() * WEIGTH8;
//			return result;
//			return 0;
			
			
			Position kingpos = tb.getKingPosition();
			result += state.getTurnOf() == PlayerKind.WHITE 
					? -position_weight[kingpos.getX()][kingpos.getY()] 
					: position_weight[kingpos.getX()][kingpos.getY()] ;
		}	
		
			return result;
	}
	
}

