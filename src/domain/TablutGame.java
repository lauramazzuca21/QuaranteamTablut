package domain;

import enums.GameState;
import enums.Loader;
import enums.PlayerKind;

public class TablutGame extends Game {

	
	public TablutGame(Player player){
		super(player);
		
		this.state = new TablutState(new TablutBoard(Loader.JSON, "resources/board.json"), PlayerKind.WHITE, player.getKind());
	}
	
	@Override
	public void loop(State newState) {

		this.state = newState;
		
		Move nextMove = player.getNextMove(state);
		
		state.applyMove(nextMove);
		
		if (state.getGameState() == GameState.WIN)
		{
			
		}
		
		
	}

}
