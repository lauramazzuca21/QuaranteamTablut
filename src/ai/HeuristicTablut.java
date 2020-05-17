package ai;

import domain.Board;
import domain.Position;
import domain.State;
import domain.TablutBoard;
import domain.TablutState;
import enums.GameState;
import enums.Pawn;
import enums.PlayerKind;
import enums.Tile;

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
//	private static final int WEIGTH5 = 1;
//	private static final int WEIGTH6 = 1;
//	private static final int WEIGTH7 = 15;
//	private static final int WEIGTH8 = 5;
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
		
		if (state.hasWon(PlayerKind.WHITE)) 
		{
			return 10000;
		}

		if (state.hasWon(PlayerKind.BLACK)) 
		{
			return -10000;
		}
		
		if (state.getGameState() == GameState.DRAW && state.getTurnOf() == PlayerKind.WHITE)
			return 9000;
			
		if (state.getGameState() == GameState.DRAW && state.getTurnOf() == PlayerKind.BLACK)
			return -9000;
			

		int result = 0;
		
		if (isKingReadyToWin(state))
			result = result + WEIGTH1;
			
		if (isKingInDanger(state))
			result = result - WEIGTH2;
		
		// valutazioni sul numero di pezzi
		result = result + (state.getBoard().getPawnCount(Pawn.WHITE)*WEIGTH3);
		result = result - (state.getBoard().getPawnCount(Pawn.BLACK)*WEIGTH4);
		
		
		
//		//considerazioni sulle pedine nella zona del re
//		int[] pawnsInFlow = state.getPawnsInFlowDirection();
//		result += pawnsInFlow[0] * WEIGTH5;
//		result -= pawnsInFlow[1] * WEIGTH6;
//		// considerazioni sulle pedine in diagonale rispetto al re
//		result += state.getPawnsOnKingDiagonal() * WEIGTH7;
//		result += state.getPawnsOnKingDiagonal2() * WEIGTH8;
//		return result;
//		return 0;
		
		
		Position kingpos = ((TablutBoard) state.getBoard()).getKingPosition();
		result = result + position_weight[kingpos.getX()][kingpos.getY()];
		
		if (state.getTurnOf() == PlayerKind.WHITE)
			return result;
		else return -result;
	}	
	
	
	private static boolean isKingInDanger(State state) {

		Position kingpos = ((TablutBoard) state.getBoard()).getKingPosition();
		int kingY = kingpos.getY();
		int kingX = kingpos.getX();
		int possibileCattura = ((TablutBoard) state.getBoard()).countKingSurrounded();

		if (kingX == 4) {
			if (kingY == 4 && possibileCattura == 3) return true; // trono
			else if (kingY == 3 && possibileCattura == 2) return true; //adiacente al trono
			else if (kingY == 5 && possibileCattura == 2) return true;
		}
		else if (kingY == 4) {
			if ((kingX == 3 || kingX == 5) && possibileCattura == 2) return true;//adiacente al trono
		} 
		else if (kingX == 2 && kingY == 4) return true; //accampamento
		else if (kingX == 4 && ( kingY == 2 || kingY == 6)) return true;//accampamento
		else if (kingX == 6 && kingY == 4) return true;//accampamento

		else if (possibileCattura == 1) return true;//altro

		else if ((kingX == 5 || kingX == 3) && (kingY == 1 || kingY == 7)) return true;
		else if ((kingX == 1 || kingX == 7) && (kingY == 5 || kingY == 3)) return true;

		return false;

	}

	
	private static boolean isKingReadyToWin(State state) {
		TablutBoard tb = (TablutBoard) state.getBoard();
		Position kingpos = tb.getKingPosition();
		int kingY = kingpos.getY();
		int kingX = kingpos.getX();
		
		int x, y;
		//se il percorso in orizzontale � libero e non c'� alcun accampamento
		for (x = kingX  + 1; x < tb.getDimX(); x++)
			if (!tb.getPawn(x, kingY).equals(Pawn.EMPTY) || (tb.getTile(x, kingY) == Tile.CAMP)) break;
		if (x == tb.getDimX()) return true;
		
		for (x = kingX - 1; x >= 0; x--)
			if (!tb.getPawn(x, kingY).equals(Pawn.EMPTY) || ((tb.getTile(x, kingY)==Tile.CAMP))) break;
		if (x < 0) return true;
		
		//se il percorso in verticale � libero e non c'� alcun accampamento
		for (y = kingY + 1; y < tb.getDimY(); y++)
			if (!tb.getPawn(kingX, y).equals(Pawn.EMPTY) || (tb.getTile(kingX, y)==Tile.CAMP)) break;
		if (y == tb.getDimX()) return true;
		
		for (y = kingY - 1; y >= 0; y--)
			if (!tb.getPawn(kingX, y).equals(Pawn.EMPTY) || (tb.getTile(kingX, y)==Tile.CAMP)) break;
		if (y < 0) return true;
		
		//altrimenti non � pronto a vincere	
		return false;
	}
	
	
	
	
	
	
}

