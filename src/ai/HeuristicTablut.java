package ai;

import domain.Board;
import domain.Position;
import domain.State;
import domain.TablutBoard;
import domain.TablutState;
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
	private static final int WEIGTH5 = 1;
	private static final int WEIGTH6 = 1;
	private static final int WEIGTH7 = 15;
	private static final int WEIGTH8 = 5;

	
	public int getStateValue(State state) {		
		TablutBoard tb = (TablutBoard) state.getBoard();
		
		if (state.hasWon(PlayerKind.WHITE)) return 10000;

		if (state.hasWon(PlayerKind.BLACK)) return -10000;

		int result = 0;
		
		if (isKingReadyToWin(state))
			result = result + WEIGTH1;
			
		if (isKingInDanger(state))
			result = result - WEIGTH2;
		
		// valutazioni sul numero di pezzi
		result = result + (state.getBoard().getPawnCount(Pawn.WHITE)*WEIGTH3);
		result = result - (state.getBoard().getPawnCount(Pawn.BLACK)*WEIGTH4);
		
		
		/*
		//considerazioni sulle pedine nella zona del re
		int[] pawnsInFlow = state.getPawnsInFlowDirection();
		result += pawnsInFlow[0] * WEIGTH5;
		result -= pawnsInFlow[1] * WEIGTH6;
		// considerazioni sulle pedine in diagonale rispetto al re
		result += state.getPawnsOnKingDiagonal() * WEIGTH7;
		result += state.getPawnsOnKingDiagonal2() * WEIGTH8;
		return result;
		return 0;
		*/
		
		return result;
	}


	private static boolean isKingInDanger(State state) {

		TablutBoard tb = (TablutBoard) state.getBoard();
		Position kingpos = tb.getKingPosition();
		
		int possibileCattura = 0;

		if (state.getBoard().getPawn(kingpos.getX()-1, kingpos.getY()).equals(Pawn.BLACK)) possibileCattura++;
		if (state.getBoard().getPawn(kingpos.getX()+ 1, kingpos.getY()).equals(Pawn.BLACK)) possibileCattura++;
		if (state.getBoard().getPawn(kingpos.getX(), kingpos.getY() - 1).equals(Pawn.BLACK)) possibileCattura++;
		if (state.getBoard().getPawn(kingpos.getX(), kingpos.getY() + 1).equals(Pawn.BLACK)) possibileCattura++;

		if (kingpos.getX() == 4) {
			if (kingpos.getY() == 4 && possibileCattura == 3) return true; // trono
			else if (kingpos.getY() == 3 && possibileCattura == 2) return true; //adiacente al trono
			else if (kingpos.getY() == 5 && possibileCattura == 2) return true;
		}//adiacente al trono
		else if (kingpos.getY() == 4) {
			if (kingpos.getX() == 3 && possibileCattura == 2) return true;//adiacente al trono
			else if (kingpos.getX() == 5 && possibileCattura == 2) return true;//adiacente al trono
		} else if (kingpos.getX() == 2 && kingpos.getY() == 4) return true; //accampamento
		else if (kingpos.getX() == 4 && kingpos.getY() == 2) return true;//accampamento
		else if (kingpos.getX() == 6 && kingpos.getY() == 4) return true;//accampamento
		else if (kingpos.getX() == 4 && kingpos.getY() == 6) return true;//accampamento

		else if (possibileCattura == 1) return true;//altro

		else if ((kingpos.getX() == 5 || kingpos.getX() == 3) && (kingpos.getY() == 1 || kingpos.getY() == 7)) return true;
		else if ((kingpos.getX() == 1 || kingpos.getX() == 7) && (kingpos.getY() == 5 || kingpos.getY() == 3)) return true;

		return false;

	}

	
	private static boolean isKingReadyToWin(State state) {
		TablutBoard tb = (TablutBoard) state.getBoard();
		Position kingpos = tb.getKingPosition();
		
		int x, y;
		//se il percorso in orizzontale � libero e non c'� alcun accampamento
		for (x = kingpos.getX()  + 1; x < tb.getDimX(); x++)
			if (!tb.getPawn(x, kingpos.getY()).equals(Pawn.EMPTY) || (tb.getTile(x, kingpos.getY()) == Tile.CAMP)) break;
		
		if (x == tb.getDimX()) return true;
		
		for (x = kingpos.getX() - 1; x >= 0; x--)
			if (!tb.getPawn(x, kingpos.getY()).equals(Pawn.EMPTY) || ((tb.getTile(x, kingpos.getY())==Tile.CAMP))) break;
		if (x < 0) return true;
		
		//se il percorso in verticale � libero e non c'� alcun accampamento
		for (y = kingpos.getY() + 1; y < tb.getDimY(); y++)
			if (!tb.getPawn(kingpos.getX(), y).equals(Pawn.EMPTY) || (tb.getTile(kingpos.getX(), y)==Tile.CAMP)) break;
		
		if (y == tb.getDimX()) return true;
		
		for (y = kingpos.getY() - 1; y >= 0; y--)
			if (!tb.getPawn(kingpos.getX(), y).equals(Pawn.EMPTY) || (tb.getTile(kingpos.getX(), y)==Tile.CAMP)) break;
		if (y < 0) return true;
		
		//altrimenti non � pronto a vincere	
		return false;
	}
	
	
	
	
	
	
}

