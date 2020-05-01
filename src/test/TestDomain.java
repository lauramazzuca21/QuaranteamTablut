package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import domain.Board;
import domain.TablutBoard;
import enums.Loader;
import enums.Pawn;
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
		
		Board tablut = new TablutBoard(Loader.JSON);
		
		assertTrue(Arrays.deepEquals(pawnBoard, tablut.getPawnBoard()));
		assertTrue(Arrays.deepEquals(tileBoard, tablut.getTileBoard()));		
	}

}
