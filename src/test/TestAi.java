package test;

import org.junit.jupiter.api.Test;

import domain.AiPlayer;
import domain.Game;
import domain.TablutGame;
import enums.PlayerKind;

class TestAi {

	
	@Test
	void test_Alpha_Beta_Pruning_time_white() {
		Game ourGame = new TablutGame(new AiPlayer("TheQuaranteam", PlayerKind.WHITE));
		
		ourGame.loop(ourGame.getState());
		System.out.println(ourGame.getState().toString());
	}
	
	@Test
	void test_Alpha_Beta_Pruning_time_black() {
		Game ourGame = new TablutGame(new AiPlayer("TheQuaranteam", PlayerKind.BLACK));
		
		ourGame.getState().setTurnOf(PlayerKind.BLACK);
		
		ourGame.loop(ourGame.getState());
		System.out.println(ourGame.getState().toString());
	}
	
	@Test
	void test_Alpha_Beta_Pruning_10_turns() {
		Game ourGame = new TablutGame(new AiPlayer("TheQuaranteam", PlayerKind.WHITE));
		Game game = new TablutGame(new AiPlayer("Adversary", PlayerKind.BLACK));
		
		
		for(int i = 0; i < 10; i++) {
			ourGame.loop(i == 0 ? null : game.getState());
			System.out.println(ourGame.getState().toString());
			
			game.loop(ourGame.getState());
			System.out.println(game.getState().toString());
		}
	}

}
