package enums;

import java.util.HashMap;
import java.util.Map;

public enum Pawn {
	BLACK("B"), WHITE("W"), KING("K"), EMPTY("O");
	
	private final String pawn;

    private Pawn( String pawn) {
        this.pawn = pawn;
    }
    
    public boolean equalsPawn(String otherPawn) {
		return (otherPawn == null) ? false : pawn.equals(otherPawn);
	}

	public String toString() {
		return pawn;
	}
	
	//****** Reverse Lookup Implementation************//

	//Lookup table
	private static final Map<String, Pawn> lookup = new HashMap<>();
 
	//Populate the lookup table on loading time
	static 
	{
		for(Pawn pawn : Pawn.values())
		{
			lookup.put(pawn.toString(), pawn);
		}
	}
 
	//This method can be used for reverse lookup purpose
	public static Pawn get(String pawn) 
	{
		return lookup.get(pawn);
	}
}
