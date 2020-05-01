package domain;

import enums.Loader;
import utils.BoardLoader;

public class TablutBoard extends Board {
	
	private static int DIM = 9;

	public TablutBoard(Loader boardLoader) {
		super(DIM, DIM);
		
		initializeBoard(boardLoader);
	}

	private void initializeBoard(Loader boardLoader) {

		BoardLoader loader = BoardLoader.BoardLoaderFactory(boardLoader);
		this.setPawnBoard(loader.getPawnBoardInitialSetup());
		this.setTileBoard(loader.getTileBoardInitialSetup());
	}
	
	

}
