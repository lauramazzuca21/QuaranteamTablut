package domain;

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
		
		player.getNextMove(state);
		
	}

}
