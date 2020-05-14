package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.*;
import enums.PlayerKind;

class TestAi {

	/*
	@Test
	void test_Alpha_Beta_Pruning_time_white() {
		List<Player> players = new ArrayList<Player>();
		
		players.add(new AiPlayer("TheQuaranteam", PlayerKind.WHITE));
		players.add(new AiPlayer("Adversary", PlayerKind.BLACK));
		
		Game game = new TablutGame(players);
		
		game.loop();
		System.out.println("=================END OF TEST 1=================");
	}
	
	@Test
	void test_Alpha_Beta_Pruning_time_black() {
		List<Player> players = new ArrayList<Player>();
		
		players.add(new AiPlayer("TheQuaranteam", PlayerKind.BLACK));
		players.add(new AiPlayer("Adversary", PlayerKind.WHITE));

		Game game = new TablutGame(players);
		game.getState().setTurnOf(PlayerKind.BLACK);	
		
		game.loop();
		System.out.println("=================END OF TEST 2=================");
	}
	*/
	
	@Test
	void test_Alpha_Beta_Pruning_10_turns() {
		List<Player> players = new ArrayList<Player>();
		
		players.add(new AiPlayer("TheQuaranteam", PlayerKind.WHITE));
		players.add(new AiPlayer("Adversary", PlayerKind.BLACK));
		
		Game game = new TablutGame(players);
		
		for(int i = 0; i < 10; i++) {
			game.loop();
			
		}
		
		System.out.println("=================END OF TEST 3=================");
	}

}
