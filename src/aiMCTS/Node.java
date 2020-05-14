package aiMCTS;

import java.util.List;
import java.util.Random;

import domain.Move;
import domain.TablutMCTSState;

public class Node {
	private TablutMCTSState state;
	private Node parent;
    private List<Node> childArray;
    private Move usedMove;
    
    public Node() {
    	
    }
    
    public Node(TablutMCTSState state) {
    	this.state = state;
    }
    
    public Node(Node node) {
    	this.state = node.getState();
    	this.parent = node.getParent();
    	this.childArray = node.getChildArray();
    }

	// setters and getters
	public TablutMCTSState getState() {
		return state;
	}
	public void setState(TablutMCTSState state) {
		this.state = state;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public List<Node> getChildArray() {
		return childArray;
	}
	public void setChildArray(List<Node> childArray) {
		this.childArray = childArray;
	}

	public Node getRandomChildNode() {
    	Random rand = new Random(); 
    	return childArray.get(rand.nextInt(childArray.size())); 
	}

	public Node getChildWithMaxScore() {
		double max = 0;
		Node maxNode = new Node();
		for(Node n : getChildArray()) {
			if (n.getState().getWinScore() >= max) {
				max = n.getState().getWinScore();
				maxNode = n;
			}
		}
		return maxNode;
	}

	public void saveMoveUsed(Move usedMove) {
		this.setUsedMove(usedMove);
	}

	public Move getUsedMove() {
		return usedMove;
	}

	public void setUsedMove(Move usedMove) {
		this.usedMove = usedMove;
	}
}