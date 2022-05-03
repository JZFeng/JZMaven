package com.jz.jiuzhang;


import java.util.*;


public class Solution {
    //无向图。首先判断 edges.length == n - 1;
    //此题可以转换为无向图找环的问题；
    public boolean validTree(int n, int[][] edges) {
        DSU dsu = new DSU(n);
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            if (dsu.find(a) == dsu.find(b)) {
                return false;
            }
            dsu.union(a, b);
        }

        return edges.length == n - 1;
    }

    class DSU {
        int[] parent;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }

    public boolean validTree2(int n, int[][] edges) {
        if (n == 0) {
            return false;
        }

        if (edges.length != n - 1) {
            return false;
        }

        //构建图
        Map<Integer, List<Integer>> graph = buildGraph(n, edges);

        // bfs
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(0);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            visited.add(node);
            for (Integer neighbor : graph.get(node)) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                queue.offer(neighbor);
            }
        }

        return (visited.size() == n);
    }

    public boolean validTree1(int n, int[][] edges) {
        if (n == 0) {
            return false;
        }
        if (edges.length != n - 1) {
            return false;
        }

        Map<Integer, List<Integer>> graph = buildGraph(n, edges);
        boolean[] visited = new boolean[n];
        if (hasCycle(graph, 0, visited, -1)) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                return false;
            }
        }

        return true;
    }

    private boolean hasCycle(Map<Integer, List<Integer>> graph, int cur, boolean[] visited, int parent) {
        visited[cur] = true;
        for (int neighbor : graph.get(cur)) {
            if (!visited[neighbor]) {
                if (hasCycle(graph, neighbor, visited, cur)) {
                    return true;
                }
            } else {
                if (parent != neighbor) {
                    return true;
                }
            }
        }
        return false;
    }


    private Map<Integer, List<Integer>> buildGraph(int n, int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<Integer>());
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
