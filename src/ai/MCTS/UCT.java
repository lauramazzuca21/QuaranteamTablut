package ai.MCTS;

import java.util.Collections;
import java.util.Comparator;

import domain.Node;


//Upper Confidence bounds applied to Trees

//algorithm that deals with the flaw of Monte-Carlo Tree Search, 
//when a program may favor a losing move with only one or a few forced refutations, 
//but due to the vast majority of other moves provides a better random playout score than other, better moves

public class UCT {
	
	public static double uctValue(
      int totalVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return ((double) nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }
 
   
	public static Node findBestNodeWithUCT(Node node) {
        int parentVisit = node.getVisitNumber();
        return Collections.max(
          node.getChildArray(),
          Comparator.comparing(c -> uctValue(parentVisit, c.getWinScore(), c.getVisitNumber())));
    }
	
}