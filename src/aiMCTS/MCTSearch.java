package aiMCTS;

import java.util.List;

import domain.Move;
import domain.TablutMCTSState;
import enums.GameState;
import enums.PlayerKind;
import domain.State;

public class MCTSearch {
   static final int WIN_SCORE = 10;
   static final int END_TIMER = 60000; //60 secondi
   int level;
   PlayerKind opponent;
 
    public Move findNextMove (TablutMCTSState state) {
    	long start= System.currentTimeMillis();
        opponent = state.getOpponent();
        
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(state.getBoard());
        rootNode.getState().setTurnOf(opponent);
 
        while (System.currentTimeMillis() - start < END_TIMER) {
            Node promisingNode = selectPromisingNode(rootNode);
            if (promisingNode.getState().hasWon(state.getTurnOf()) == false) {
                expandNode(promisingNode);
            }
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            PlayerKind winner = simulateRandomPlayout(nodeToExplore);
            backPropogation(nodeToExplore, winner);
        }
 
        Node winnerNode = rootNode.getChildWithMaxScore();
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
    
    private void expandNode(Node node) {
        List<Move> possibleMoves = node.getState().getAllPossibleStates();
        possibleMoves.forEach(nextMove -> {
            Node newNode = new Node(node.getState());
        	newNode.getState().applyMove(nextMove);
            newNode.setParent(node);
            newNode.getState().setTurnOf(node.getState().getOpponent());
            newNode.saveMoveUsed(nextMove);
            node.getChildArray().add(newNode);
        });
    }

   
    private PlayerKind simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
        TablutMCTSState tempState = tempNode.getState();
        GameState boardStatus = tempState.getGameState();
//    if(boardStatus != GameState.PLAYING) {
//        if (boardStatus == GameState.LOSE) {
//            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
//            return tempNode.getState().getOpponent();
//        }
//        else if (boardStatus == GameState.WIN){
//            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
//            return tempNode.getState().getTurnOf();
//        }
//        else if (boardStatus == GameState.DRAW){
//        	return null;
//        }
//    }
        while (boardStatus == GameState.PLAYING) {
            tempState.setTurnOf(tempState.getOpponent());
            tempState.randomPlay();
            boardStatus = tempState.getGameState();
        }
        
        if (boardStatus == GameState.LOSE) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return tempNode.getState().getOpponent();
        }
        else if (boardStatus == GameState.WIN){
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return tempNode.getState().getTurnOf();
        }
        else{
        	return null;
        }    
    }
    
    private void backPropogation(Node nodeToExplore, PlayerKind playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().incrementVisit();
            if (tempNode.getState().getTurnOf() == playerNo) {
                tempNode.getState().addScore(WIN_SCORE);
            }
            tempNode = tempNode.getParent();
        }
    }
    
   
    
}
