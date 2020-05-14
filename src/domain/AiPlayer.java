package domain;

import enums.PlayerKind;
import ai.HeuristicTablut;
import ai.ResearchAlphaBeta;
import ai.ResearchPVS;

public class AiPlayer extends Player {

	ResearchAlphaBeta ab = new ResearchAlphaBeta();
	ResearchPVS pvs = new ResearchPVS();
	
	public AiPlayer(String id, PlayerKind kind) {
		super(id, kind);
	}

	@Override
	public Move getNextMove(State newState) {	
		return ab.AlphaBetaSearch(new HeuristicTablut(), 7, newState);
//		return pvs.pvSearch(newState, Integer.MAX_VALUE, Integer.MIN_VALUE, 3);
	}

}
