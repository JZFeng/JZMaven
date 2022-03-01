package com.jz.jiuzhang;

import java.util.*;


public class Solution {
    //无向图。首先判断 edges.length == n - 1;
    public boolean validTree(int n, int[][] edges) {
        if (n == 0) {
            return false;
        }

        if (edges.length != n - 1) {
            return false;
        }

        //构建图
        Map<Integer, Set<Integer>> graph = initializeGraph(n, edges);

        // bfs
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(0);
        int count = 1;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            count++;
            for (Integer neighbor : graph.get(node)) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                visited.add(neighbor);
                queue.offer(neighbor);
            }
        }

        return (count == n);
    }

    private Map<Integer, Set<Integer>> initializeGraph(int n, int[][] edges) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<Integer>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        return graph;
    }
}
