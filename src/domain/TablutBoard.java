package domain;

import enums.Loader;
import utils.BoardLoader;

public class TablutBoard extends Board {
	
	private static int DIM = 9;

	public TablutBoard(Loader boardLoader, String source) {
		super(DIM, DIM);
		
		initializeBoard(boardLoader, source);
	}

	private void initializeBoard(Loader boardLoader, String source) {

		BoardLoader loader = BoardLoader.BoardLoaderFactory(boardLoader, source);
		this.setPawnBoard(loader.getPawnBoardSetup());
		this.setTileBoard(loader.getTileBoardSetup());
	}
	
	

}
