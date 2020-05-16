package ai;

import java.util.HashMap;

import domain.Pair;

public class TranspositionTable {

	//in the Pair Key is the depth, Value is the Value associated to the configuration by ABP
	private HashMap<String, Pair<Integer, Integer>> table = new HashMap<String, Pair<Integer, Integer>>();
	private static TranspositionTable instance = null;
	
	public static TranspositionTable getInstance() {
		if (instance == null)
			instance = new TranspositionTable();
		return instance;
	}
	
	public void add(String board, int depth, int value) {
		
		if (table.containsKey(board)) {
			int oldDepth = table.get(board).getKey();
			if (oldDepth < depth)
				table.put(board, new Pair<Integer, Integer>(depth, value));
		}
		else {
			table.put(board, new Pair<Integer, Integer>(depth, value));
		}
	}

	public boolean contains(String board) {
		return table.containsKey(board);
	}

	public Integer getValue(String board, int depth) {
	
		return table.get(board).getKey() > depth ? table.get(board).getValue() : null;
	}
	
	public void clear() {
		this.table.clear();
	}
	
	
}
