package domain;

import enums.Loader;

public class TablutGame extends Game {

	
	public TablutGame(Player player){
		super(player);
		
		this.state = new TablutState(new TablutBoard(Loader.JSON, "resources/board.json"), player.getTurn());
	}
	
	@Override
	public void loop(State newState) {

		this.state = newState;
		
		player.getNextMove(state);
		
	}
	
	

}
