package com.jz.algo.graph;

import java.util.ArrayList;

public class DirectedGraphNode {
	int label;
	public ArrayList<DirectedGraphNode> neighbors;

	DirectedGraphNode(int x) {
		label = x;
		neighbors = new ArrayList<DirectedGraphNode>();
	}
	
}
