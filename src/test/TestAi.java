package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.HeuristicTablut;
import ai.ResearchAlphaBeta;
import aiMCTS.MCTSearch;
import domain.*;
import enums.GameState;
import enums.PlayerKind;

class TestAi {


//	@Test
//	void test_Alpha_Beta_Pruning_time_white() {
//		List<Player> players = new ArrayList<Player>();
//		
//		players.add(new AiPlayer("TheQuaranteam", PlayerKind.WHITE));
//		players.add(new AiPlayer("Adversary", PlayerKind.BLACK));
//		
//		Game game = new TablutGame(players);
//		
//		game.loop();
//		System.out.println("=================END OF TEST 1=================");
//	}
	
//	@Test
//	void test_Alpha_Beta_Pruning_time_black() {
//		List<Player> players = new ArrayList<Player>();
//		
//		players.add(new AiPlayer("TheQuaranteam", PlayerKind.BLACK));
//		players.add(new AiPlayer("Adversary", PlayerKind.WHITE));
//
//		Game game = new TablutGame(players);
//		game.getState().setTurnOf(PlayerKind.BLACK);	
//		
//		game.loop();
//		System.out.println("=================END OF TEST 2=================");
//	}

//	@Test
//	void test_Alpha_Beta_Pruning_time_white() {
//		List<Player> players = new ArrayList<Player>();
//		
//		players.add(new AiPlayer("TheQuaranteam", PlayerKind.WHITE));
//		players.add(new AiPlayer("Adversary", PlayerKind.BLACK));
//		
//		Game game = new TablutGame(players);
//		
//		game.loop();
//	}
//	
//	@Test
//	void test_Alpha_Beta_Pruning_time_black() {
//		List<Player> players = new ArrayList<Player>();
//		
//		players.add(new AiPlayer("TheQuaranteam", PlayerKind.BLACK));
//		players.add(new AiPlayer("Adversary", PlayerKind.WHITE));
//
//		Game game = new TablutGame(players);
//		game.getState().setTurnOf(PlayerKind.BLACK);	
//		
//		game.loop();
//	}
	
//	@Test
//	void test_Alpha_Beta_Pruning_10_turns() {
//		List<Player> players = new ArrayList<Player>();
//		
//		players.add(new AiPlayer("TheQuaranteam", PlayerKind.WHITE));
//		players.add(new AiPlayer("Adversary", PlayerKind.BLACK));
//		
//		Game game = new TablutGame(players);
//		
//		for(int i = 0; i < 10; i++) {
//			game.loop();
//		}
//	}
	
//	@Test
//	void test_MCS_vs_AB() {
//		List<Player> players = new ArrayList<Player>();
//		
//		players.add(new AiPlayer("TheQuaranteam", PlayerKind.WHITE, new MCTSearch()));
//		players.add(new AiPlayer("Adversary", PlayerKind.BLACK, new ResearchAlphaBeta(20, new HeuristicTablut())));
//		
//		Game game = new TablutGame(players);
//		
//		while (game.getState().getGameState() == GameState.PLAYING)	
//		{
//			game.loop();
//		}
//	}
//	
	@Test
	void test_AB_vs_MCS_10_turns() {
		List<Player> players = new ArrayList<Player>();
		
		players.add(new AiPlayer("TheQuaranteam", PlayerKind.WHITE, new ResearchAlphaBeta(6, new HeuristicTablut())));
		players.add(new AiPlayer("Adversary", PlayerKind.BLACK, new ResearchAlphaBeta(6, new HeuristicTablut())));
		
		Game game = new TablutGame(players);
		
		while (game.getState().getGameState() == GameState.PLAYING)	
		{
			game.loop();
		}
	}

}
