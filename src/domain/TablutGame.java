package domain;

import java.util.List;

import enums.GameState;
import enums.Loader;

public class TablutGame extends Game {

	
	public TablutGame(List<Player> players){
		super(players);
		
		this.state = new TablutState(new TablutBoard(Loader.JSON, "resources/board.json"));
	}
	
	@Override
	public void loop() {
		
		for (Player p : players )
		{
			Move nextMove = p.getNextMove(state);
			
			state.applyMove(nextMove);
			System.out.println("turno "+p.getKind());

			System.out.println(state.toString());

			checkGameState();
		}
	}
	
	

	
	private void checkGameState() {
		
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
