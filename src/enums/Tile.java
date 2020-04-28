package enums;

public enum Tile {
	ESCAPE("E"), CASTLE("T"), CAMP("C"), EMPTY("O");
	
	private final String tile;

    private Tile(final String tile) {
        this.tile = tile;
    }
    
    public boolean equalsTile(String otherPawn) {
		return (otherPawn == null) ? false : tile.equals(otherPawn);
	}

	public String toString() {
		return tile;
	}
}
