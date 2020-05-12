package ai;

import domain.Board;
import domain.State;
import domain.TablutState;
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

	
	public int getStateValue(State state) {		

		if (state.hasWon(PlayerKind.WHITE)) return 10000;

		if (state.hasWon(PlayerKind.BLACK)) return -10000;

		int result = 0;

		/*
		if (isKingReadyToWin(state))
			result += WEIGTH1;

		if (isKingInDanger(state))
			result -= WEIGTH2;
		*/
		
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


/*
	private static boolean isKingInDanger(ITablutState state) {
		int[] king = state.getCoordKing();
		//@Matteo versione semplificata -- quella completa non funziona


		int possibileCattura = 0;

		if (state.getState().getPawn(king[0] - 1, king[1]).equals(Pawn.BLACK)) possibileCattura++;
		if (state.getState().getPawn(king[0] + 1, king[1]).equals(Pawn.BLACK)) possibileCattura++;
		if (state.getState().getPawn(king[0], king[1] - 1).equals(Pawn.BLACK)) possibileCattura++;
		if (state.getState().getPawn(king[0], king[1] + 1).equals(Pawn.BLACK)) possibileCattura++;

		if (king[0] == 4) {
			if (king[1] == 4 && possibileCattura == 3) return true; // trono
			else if (king[1] == 3 && possibileCattura == 2) return true; //adiacente al trono
			else if (king[1] == 5 && possibileCattura == 2) return true;
		}//adiacente al trono
		else if (king[1] == 4) {
			if (king[0] == 3 && possibileCattura == 2) return true;//adiacente al trono
			else if (king[0] == 5 && possibileCattura == 2) return true;//adiacente al trono
		} else if (king[0] == 2 && king[1] == 4) return true; //accampamento
		else if (king[0] == 4 && king[1] == 2) return true;//accampamento
		else if (king[0] == 6 && king[1] == 4) return true;//accampamento
		else if (king[0] == 4 && king[1] == 6) return true;//accampamento

		else if (possibileCattura == 1) return true;//altro

		else if ((king[0] == 5 || king[0] == 3) && (king[1] == 1 || king[1] == 7)) return true;
		else if ((king[0] == 1 || king[0] == 7) && (king[1] == 5 || king[1] == 3)) return true;

		return false;

	}


	private static boolean isKingReadyToWin(ITablutState state) {
		int king[] = state.getCoordKing();
		int x, y;
		//se il percorso in orizzontale � libero e non c'� alcun accampamento
		for (x = king[0] + 1; x < state.getState().getBoard().length; x++)
			if (!state.getState().getPawn(x, king[1]).equals(Pawn.EMPTY) || state.isPawnAccampamento(x, king[1])) break;
		if (x == state.getState().getBoard().length) return true;
		for (x = king[0] - 1; x >= 0; x--)
			if (!state.getState().getPawn(x, king[1]).equals(Pawn.EMPTY) || state.isPawnAccampamento(x, king[1])) break;
		if (x < 0) return true;
		//se il percorso in verticale � libero e non c'� alcun accampamento
		for (y = king[1] + 1; y < state.getState().getBoard().length; y++)
			if (!state.getState().getPawn(king[0], y).equals(Pawn.EMPTY) || state.isPawnAccampamento(king[0], y)) break;
		if (y == state.getState().getBoard().length) return true;
		for (y = king[1] - 1; y >= 0; y--)
			if (!state.getState().getPawn(king[0], y).equals(Pawn.EMPTY) || state.isPawnAccampamento(king[0], y)) break;
		if (y < 0) return true;
		//altrimenti non � pronto a vincere	
		return false;
	}
	*/
	
	
	
	
	
	
}

