/*
 http://www.lintcode.com/en/problem/topological-sorting/#
 
  Given an directed graph, a topological order of the graph nodes is defined as follow:

For each directed edge A -> B in graph, A must before B in the order list.
The first node in the order can be any node in the graph with no nodes direct to it.
Find any topological order for the given graph.

 Notice

You can assume that there is at least one topological order in the graph.

Have you met this question in a real interview? Yes
Clarification
Learn more about representation of graphs

Example
For graph as follow:



The topological order can be:

[0, 1, 2, 3, 4, 5]
[0, 2, 3, 1, 5, 4]
...
 */
package com.jz.algo.graph;

import com.jz.algo.graph.DirectedGraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TopologicalSorting {

	public static void main(String[] args) {

	}

	/**
	 * @param graph:
	 *            A list of Directed graph node
	 * @return: Any topological order for the given graph.
	 */
	public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		// write your code here
		ArrayList<DirectedGraphNode> result = new ArrayList<DirectedGraphNode>();
		
		//统计节点在neighbor中出现过多少次，即表示此节点的入度。
		//不在此hashmap中的节点，说明没有任何节点指向它，也就是入度为0.
		HashMap<DirectedGraphNode, Integer> map = new HashMap();
		for (DirectedGraphNode node : graph) {
			for (DirectedGraphNode neighbor : node.neighbors) {
				if (map.containsKey(neighbor)) {
					map.put(neighbor, map.get(neighbor) + 1);
				} else {
					map.put(neighbor, 1);
				}
			}
		}
		
		//BFS遍历有向图。遍历整个图，找出入度为0的点（起点）放入队列。
		//Question:此处不需要break吗？有可能多个入度为0的点？
		Queue<DirectedGraphNode> q = new LinkedList<DirectedGraphNode>();
		for (DirectedGraphNode node : graph) {
			if (!map.containsKey(node)) {
				q.offer(node);
				result.add(node);
			}
		}
		
		//挨个出队列，并更新hashmap中的入度信息，直到队列为空。
		while (!q.isEmpty()) {
			DirectedGraphNode node = q.poll();
			for (DirectedGraphNode n : node.neighbors) {
				map.put(n, map.get(n) - 1);
				if (map.get(n) == 0) {
					result.add(n);
					q.offer(n);
				}
			}
		}
		return result;
	}

}
