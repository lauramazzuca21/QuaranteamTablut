package aiMCTS;

import java.util.List;

import ai.ResearchAlgorithm;
import domain.Move;
import domain.Pair;
import domain.Position;
import domain.State;
import domain.TablutMCTSState;
import enums.GameState;
import enums.PlayerKind;

public class MCTSearch  implements ResearchAlgorithm {
   static final int WIN_SCORE = 100;
   static final int DRAW_SCORE = 10;
   static final int END_TIMER = 55000; //60 secondi
   int level;
   PlayerKind opponent;
 
	@Override
	public Move getNextMove(State newState) {
    	long start= System.currentTimeMillis();
        int count = 0;
        Tree tree = new Tree();
		TablutMCTSState MCTSState = new TablutMCTSState(newState);
        tree.getRoot().setState(MCTSState.toString());
        tree.getRoot().setPlayer(MCTSState.getTurnOf());
    	TablutMCTSState stateCopy = MCTSState.deepCopy();

        expandNode(tree.getRoot(), stateCopy);
        
        
        while (System.currentTimeMillis() - start < END_TIMER) {
            //selects the most promising child of the root node so that we have to analize less 
        	Node promisingNode = selectPromisingNode(tree.getRoot());
            //promising nodes are only nodes of 0-1 breadth level
            Position[] eaten = stateCopy.applyMove(promisingNode.getUsedMove());
        	
            //if it's not a winning state, we expand the children of the promising node
        	if (stateCopy.hasWon(stateCopy.getTurnOf()) == false) {
                expandNode(promisingNode, stateCopy);
            }
        	
            Node nodeToExplore = promisingNode;
            //if the promising node has been expanded, get a random child
            if (promisingNode.getChildArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            
            PlayerKind winner = simulateRandomPlayout(nodeToExplore, stateCopy);
            backPropogation(nodeToExplore, winner);
            stateCopy.undoMove(promisingNode.getUsedMove(), eaten);
            count++;
        }
 

        Node winnerNode = tree.getRoot().getChildWithMaxScore();
        System.out.println("Loops: " + count + " Visited: " + winnerNode.getVisitNumber() + " WinScore: " + winnerNode.getWinScore());

        tree.setRoot(winnerNode);
        return winnerNode.getUsedMove();
    }
   
    
    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (node.getChildArray().size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }
    
    private void expandNode(Node node, TablutMCTSState state) {
        List<Move> possibleMoves = state.getPossibleMoves();
        
        for (Move nextMove : possibleMoves)
        {
        	PlayerKind currentPlayer = state.getTurnOf();
        	Position[] eaten = state.applyMove(nextMove);
        	Node newNode = new Node(state.toString(), currentPlayer);
        	state.undoMove(nextMove, eaten);
            newNode.setParent(node);
            newNode.saveMoveUsed(nextMove);
            node.getChildArray().add(newNode);
        }
    }

   
    private PlayerKind simulateRandomPlayout(Node nodeToExplore, TablutMCTSState state) {
    	    	
    	TablutMCTSState stateCopy = state.deepCopy();
    	Pair<GameState, PlayerKind> boardStatus;
    	
    	do {
    		boardStatus = stateCopy.randomPlay();
    	}while (boardStatus.getFirst() == GameState.PLAYING);
        
        if (boardStatus.getFirst() == GameState.LOSE) {
            return boardStatus.getSecond() == PlayerKind.WHITE ? PlayerKind.BLACK : PlayerKind.WHITE;
        }
        else if (boardStatus.getFirst()  == GameState.WIN){
            return boardStatus.getSecond();
        }
        else {
        	return null;
        }    
    }
    
    private void backPropogation(Node nodeToExplore, PlayerKind playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.incrementVisit();
            if (tempNode.getPlayer() == playerNo) {
                tempNode.incrementWinScore(WIN_SCORE);
            }
            else if(playerNo == null) {
            	tempNode.incrementWinScore(DRAW_SCORE);
            }
            tempNode = tempNode.getParent();
        }
    }
    
   
    
}
