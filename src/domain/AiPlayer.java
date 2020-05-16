package domain;

import enums.PlayerKind;
import ai.ResearchAlgorithm;

public class AiPlayer extends Player {

	private int turn;
	private ResearchAlgorithm research;
	
	public ResearchAlgorithm getResearch() {
		return research;
	}
	
	public AiPlayer(String id, PlayerKind kind, ResearchAlgorithm research) {
		super(id, kind);
		turn = 0;
		this.research = research;
	}

	@Override
	public Move getNextMove(State newState) {
//		if (this.getKind() == PlayerKind.WHITE && turn == 0)
//		{	
			turn++;
//			return new Move(2, 4, 2, 2); 
//		}
		return this.getResearch().getNextMove(newState);
	}

}
