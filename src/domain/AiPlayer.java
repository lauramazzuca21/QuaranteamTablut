package domain;

import enums.PlayerKind;
import ai.ResearchAlphaBeta;
import ai.ResearchPVS;
import aiMCTS.MCTSearch;

public class AiPlayer extends Player {

	ResearchAlphaBeta ab = new ResearchAlphaBeta();
	ResearchPVS pvs = new ResearchPVS();
	int turn;
	
	MCTSearch mtcs = new MCTSearch();

	public AiPlayer(String id, PlayerKind kind) {
		super(id, kind);
		turn = 0;
	}

	@Override
	public Move getNextMove(State newState) {
//		if (this.getKind() == PlayerKind.WHITE && turn == 0)
//		{	
//			turn++;
//			return new Move(2, 4, 2, 2); 
//		}
//		return ab.AlphaBetaSearch(new HeuristicTablut(), 7, newState);
//		return pvs.pvSearch(newState, Integer.MAX_VALUE, Integer.MIN_VALUE, 3);
		TablutMCTSState MCTSState = new TablutMCTSState(newState);

		return mtcs.findNextMove(MCTSState);
	}

}
