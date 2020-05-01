package utils;

import enums.Loader;
import enums.Pawn;
import enums.Tile;

public abstract class BoardLoader {

	public abstract Pawn[][] getPawnBoardInitialSetup();
	public abstract Tile[][] getTileBoardInitialSetup();

	public static BoardLoader BoardLoaderFactory(Loader loader) {
		switch (loader) {
		case JSON:
				return new JSONBoardLoader();
		default:
			break;
		
		}
		return null;
	}
}
