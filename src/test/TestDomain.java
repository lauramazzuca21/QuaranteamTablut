package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.*;
import enums.Loader;
import enums.Pawn;
import enums.PlayerKind;
import enums.Tile;

class TestDomain {

	private Pawn[][] pawnBoard = {
			{Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.BLACK, Pawn.BLACK, Pawn.BLACK, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
			{Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.BLACK, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
			{Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.WHITE, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
			{Pawn.BLACK, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.WHITE, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.BLACK},
			{Pawn.BLACK, Pawn.BLACK, Pawn.WHITE, Pawn.WHITE, Pawn.KING, Pawn.WHITE, Pawn.WHITE, Pawn.BLACK, Pawn.BLACK},
			{Pawn.BLACK, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.WHITE, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.BLACK},
			{Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.WHITE, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
			{Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.BLACK, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
			{Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.BLACK, Pawn.BLACK, Pawn.BLACK, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY}
	};
	
	private Tile[][] tileBoard = {
			{Tile.EMPTY, Tile.ESCAPE, Tile.ESCAPE, Tile.CAMP, Tile.CAMP, Tile.CAMP, Tile.ESCAPE, Tile.ESCAPE, Tile.EMPTY},
			{Tile.ESCAPE, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.CAMP, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.ESCAPE},
			{Tile.ESCAPE, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.ESCAPE},
			{Tile.CAMP, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.CAMP},
			{Tile.CAMP, Tile.CAMP, Tile.EMPTY, Tile.EMPTY, Tile.CASTLE, Tile.EMPTY, Tile.EMPTY, Tile.CAMP, Tile.CAMP},
			{Tile.CAMP, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.CAMP},
			{Tile.ESCAPE, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.ESCAPE},
			{Tile.ESCAPE, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.CAMP, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.ESCAPE},
			{Tile.EMPTY, Tile.ESCAPE, Tile.ESCAPE, Tile.CAMP, Tile.CAMP, Tile.CAMP, Tile.ESCAPE, Tile.ESCAPE, Tile.EMPTY}
	};
	
	@Test
	void test_board_loader_JSON() {
		
		Board tablut = new TablutBoard(Loader.JSON,  "resources/board.json");
		
		assertTrue(Arrays.deepEquals(pawnBoard, tablut.getPawnBoard()));
		assertTrue(Arrays.deepEquals(tileBoard, tablut.getTileBoard()));		
	}
	
	@Test
	void test_tablut_state_getPossibleMoves_White() {
		
		TablutBoard tablut = new TablutBoard(Loader.JSON,  "resources/board.json");
		
		State tablutState = new TablutState(tablut, PlayerKind.WHITE, PlayerKind.WHITE);
		
		List<Move> possibleMoves = tablutState.getPossibleMoves();
		
		for (Move m : possibleMoves)
		{
			System.out.println(m.toString());
		}
		
		assertTrue(possibleMoves.size() != 0);
	}
	
	@Test
	void test_tablut_state_getPossibleMoves_Black() {
		
		TablutBoard tablut = new TablutBoard(Loader.JSON,  "resources/board.json");
		
		State tablutState = new TablutState(tablut, PlayerKind.BLACK, PlayerKind.WHITE);
		
		List<Move> possibleMoves = tablutState.getPossibleMoves();
		
		for (Move m : possibleMoves)
		{
			System.out.println(m.toString());
		}
		
		assertTrue(possibleMoves.size() != 0);
	}
	
	@Test
	void test_tablut_board_getBlackEatenPawns() {
		
		TablutBoard tablut = new TablutBoard(Loader.JSON,  "resources/board.json");
		
		State tablutState = new TablutState(tablut, PlayerKind.BLACK, PlayerKind.WHITE);
		
		List<Move> possibleMoves = tablutState.getPossibleMoves();
		
		for (Move m : possibleMoves)
		{
			System.out.println(m.toString());
		}
		
		assertTrue(possibleMoves.size() != 0);
	}
	
	@Test
	void test_tablut_board_getWhiteEatenPawns() {
		
		TablutBoard tablut = new TablutBoard(Loader.JSON,  "resources/board.json");
		
		State tablutState = new TablutState(tablut, PlayerKind.BLACK, PlayerKind.WHITE);
		
		List<Move> possibleMoves = tablutState.getPossibleMoves();
		
		for (Move m : possibleMoves)
		{
			System.out.println(m.toString());
		}
		
		assertTrue(possibleMoves.size() != 0);
	}
	
	@Test
	void test_tablut_board_isKingCaptured() {
		
		TablutBoard tablut = new TablutBoard(Loader.JSON,  "resources/board.json");
		
		assertFalse(tablut.isKingCaptured());
		
		tablut.applyMove(new Move(4, 3, 6, 3));
		tablut.applyMove(new Move(0, 3, 4, 3));
		tablut.applyMove(new Move(5, 4, 5, 2));
		tablut.applyMove(new Move(5, 8, 5, 4));
		tablut.applyMove(new Move(4, 5, 7, 5));
		tablut.applyMove(new Move(0, 3, 4, 3));
		tablut.applyMove(new Move(5, 4, 5, 2));
		tablut.applyMove(new Move(5, 8, 5, 4));
		
		assertTrue(tablut.isKingCaptured());
	}

}
