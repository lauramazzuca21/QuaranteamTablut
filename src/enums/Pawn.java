package enums;

public enum Pawn {
	BLACK("B"), WHITE("W"), KING("K"), EMPTY("O");
	
	private final String pawn;

    private Pawn(final String pawn) {
        this.pawn = pawn;
    }
    
    public boolean equalsPawn(String otherPawn) {
		return (otherPawn == null) ? false : pawn.equals(otherPawn);
	}

	public String toString() {
		return pawn;
	}
}
