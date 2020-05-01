package utils;

import enums.Pawn;
import enums.Tile;

public class ServerStringBoardLoader extends BoardLoader {

	private String serverString;
	
	public ServerStringBoardLoader(String serverString) {
		
		this.serverString = serverString;
	}
	
	public void setServerString(String serverString) {
		this.serverString = serverString;
	}
	
	@Override
	public Pawn[][] getPawnBoardSetup() {
		Pawn[][] result = new Pawn[9][9];

		String toConvert = serverString.replace('T', 'O');
		
		int j = 0;
		for(int i = 0, n = toConvert.length(); i < n ; i++) { 
			
		    result[i][j] = Pawn.get(  Character.toString(toConvert.charAt(i))  );
		    
		    if (j == 8) j = 0;
		    j++;
		    
		}
		
		return result;
	}

	@Override
	public Tile[][] getTileBoardSetup() {
		// TODO Auto-generated method stub
		return null;
	}

}
