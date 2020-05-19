package ai;

import domain.Pair;
import domain.Position;
import domain.State;
import domain.TablutBoard;
import enums.GameState;
import enums.Pawn;
import enums.PlayerKind;

public class HeuristicTablut implements HeuristicFunction{

	private static final int WEIGHT1 = 500;
	private static final int WEIGHT2 = 800;
	private static final int WEIGHT3 = 40;
	private static final int WEIGHT4 = 60;
	private static final int WEIGHT5 = 20;
	private static final int WEIGHT6 = 40;
	private static final int WEIGHT7 = 60;
	private static final int WEIGHT8 = 20;
	
	private static final int[][] position_weight = {
	        {   0, 1000, 1000,   0,    0,   0, 1000, 1000,    0},
	        {1000,  300,  200, 100,    0, 100,  200,  300, 1000},
	        {1000,  200,  200, 200,  200, 200,  200,  200, 1000},
	        {   0,  100,  200,   0,    0,   0,  200,  100,    0},
	        {   0,    0,  200,   0,-100,   0,  200,    0,    0},
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
			result = state.getTurnOf() == PlayerKind.WHITE ? -9000 : 9000;
		else 
		{
			if (tb.isKingReadyToWin())
				result = result + WEIGHT1;
			else
			{
				//assigns a higher weight to white blocking victory because they're aiding the black
				Pair<Integer, Integer> pawns = tb.pawnsBlockingKingVictory();
				if (pawns != null)
				{
//					result -= (pawns.getFirst()+pawns.getSecond()) * WEIGHT4;
					result -= pawns.getFirst() * WEIGHT8;
					result -= pawns.getSecond() * WEIGHT7;
				}
			}
				
			if (tb.isKingInDanger())
				result = result - WEIGHT2;
			
			result = result + (tb.getPawnCount(Pawn.WHITE) * WEIGHT3);
			result = result - (tb.getPawnCount(Pawn.BLACK) * WEIGHT4);
	
			Pair<Integer, Integer> halfBoardPawns = tb.getKingHalfBoardPawns();
			result += halfBoardPawns.getFirst() * WEIGHT5;
			result -= halfBoardPawns.getSecond() * WEIGHT6;
			
			Position kingpos = tb.getKingPosition();
			result += state.getTurnOf() == PlayerKind.WHITE 
					? -position_weight[kingpos.getX()][kingpos.getY()] 
					: position_weight[kingpos.getX()][kingpos.getY()] ;
		}	
		
			return result;
	}
	
}

