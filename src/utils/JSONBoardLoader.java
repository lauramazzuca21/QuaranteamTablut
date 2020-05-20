package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import enums.Pawn;
import enums.Tile;

public class JSONBoardLoader extends BoardLoader {

	private JSONObject board;
	private int dimX, dimY;
	
	
	public JSONBoardLoader(String source) {
		board = JsonUtils.getObjectFromFile(source);
		dimX = board.getInt("TablutXDim");
		dimY = board.getInt("TablutYDim");
	}
	
	
	@Override
	public Pawn[][] getPawnBoardSetup() {
		Pawn[][] result = new Pawn[dimX][dimY];
		JSONArray pawnBoard = board.getJSONArray("TablutPawnBoard");

		for(int i = 0; i < dimX; i++) {
			JSONArray row = pawnBoard.getJSONArray(i);
			for(int j = 0; j < dimY; j++) {
				result[i][j] = Pawn.valueOf(row.getString(j));
			}
		}
		
		return result;
	}

	
	@Override
	public Tile[][] getTileBoardSetup() {
		Tile[][] result = new Tile[dimX][dimY];
		JSONArray tileBoard = board.getJSONArray("TablutTileBoard");
		
		for(int i = 0; i < dimX; i++) {
			JSONArray row = tileBoard.getJSONArray(i);
			for(int j = 0; j < dimY; j++) {
				result[i][j] = Tile.valueOf(row.getString(j));
			}
		}
		return result;
	}
	
}
