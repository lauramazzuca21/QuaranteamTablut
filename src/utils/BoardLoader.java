package utils;

import enums.Loader;
import enums.Pawn;
import enums.Tile;

public abstract class BoardLoader {

	public abstract Pawn[][] getPawnBoardSetup();
	public abstract Tile[][] getTileBoardSetup();

	public static BoardLoader BoardLoaderFactory(Loader loader, String source) {
		switch (loader) {
		case JSON:
				return new JSONBoardLoader(source);
		default:
			break;
		
		}
		return null;
	}
}
