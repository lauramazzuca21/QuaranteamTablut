package domain;

import java.util.List;
import java.util.Random;

import enums.GameState;
import enums.PlayerKind;

public class TablutMCTSState extends TablutState{
    private int visitCount;
    private double winScore;
    
    //COSTRUTTORI
	public TablutMCTSState(Board board) {
		super(board);
		setVisitCount(0);
		setWinScore(0);
		this.setGameState(GameState.PLAYING);

	}

	
	//getters and setters
	public int getVisitCount() {
		return visitCount;
	}


	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}


	public double getWinScore() {
		return winScore;
	}


	public void setWinScore(double winScore) {
		this.winScore = winScore;
	}
    
 
	//altri metodi
    public List<Move> getAllPossibleStates() {
        // constructs a list of all possible states from current state
    	return getPossibleMoves();
    }
    
    public void randomPlay() {
    	List<Move> moveList = getAllPossibleStates();
    	Random rand = new Random(); 
        getBoard().applyMove(moveList.get(rand.nextInt(moveList.size()))); 
    }


	public PlayerKind getOpponent() {
		if(getTurnOf() == PlayerKind.BLACK) return PlayerKind.WHITE;
		else return PlayerKind.BLACK;
	}

	public void incrementVisit() {
		setVisitCount(getVisitCount()+1);
	}

	public void addScore(int winScore2) {
		setWinScore(getWinScore()+winScore2);
	}

}
