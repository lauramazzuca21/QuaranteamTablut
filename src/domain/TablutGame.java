package domain;

import enums.GameState;
import enums.Loader;

public class TablutGame extends Game {

	
	public TablutGame(Player player){
		super(player);
		
		this.state = new TablutState(new TablutBoard(Loader.JSON, "resources/board.json"), player.getKind());
	}
	
	@Override
	public void loop(State newState) {
		
		if (newState != null)
			this.state = newState;
		
		Move nextMove = player.getNextMove(state);
		
		state.applyMove(nextMove);
		
		if (state.getGameState() == GameState.WIN)
		{
			System.out.println("You win");
		}
		
		else if(state.getGameState() == GameState.LOSE)
		{
			System.out.println("You lost");
		}
		
		else if(state.getGameState() == GameState.DRAW)
		{
			System.out.println("Draw");
		}
		
		
	}

}
