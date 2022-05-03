/**
 * @Author jzfeng
 * @Date 4/24/22-6:27 PM
 */

package com.jz.java.dataStructure.graph;

import java.util.*;

//https://leetcode.com/problems/connecting-cities-with-minimum-cost/

/**
 * There are n cities labeled from 1 to n. You are given the integer n and an array connections
 * where connections[i] = [xi, yi, costi] indicates that
 * the cost of connecting city xi and city yi (bidirectional connection) is cost i.
 * <p>
 * Return the minimum cost to connect all the n cities such that there is at least
 * one path between each pair of cities. If it is impossible to connect all the n cities, return -1,
 * <p>
 * The cost is the sum of the connections' costs used.
 */
public class MST {
    //kruskal
    public int kruskal(int n, int[][] connections) {
        Arrays.sort(connections, (a, b) -> a[2] - b[2]);
        DSU dsu = new DSU(n + 1);
        int costs = 0;
        for (int[] conn : connections) {
            int x = dsu.find(conn[0]), y = dsu.find(conn[1]);
            if (x != y) {
                dsu.union(conn[0], conn[1]);
                costs += conn[2];
                n--;
            }
        }

        return n == 1 ? costs : -1;
    }


    //prim，使用PQ;
    public int prim(int n, int[][] connections) {
        //构建图，邻接表;neighbor的点和cost；
        Map<Integer, List<int[]>> graph = new HashMap<>();
        //根据边的weight排序；PQ里放的是边；
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[2] - b[2];
        });
        Set<Integer> visited = new HashSet<>();
        int costs = 0;

        //构建图；
        for (int[] edge : connections) {
            int x = edge[0], y = edge[1], cost = edge[2];
            graph.computeIfAbsent(x, (k) -> new ArrayList<>()).add(new int[]{y, cost});
            graph.computeIfAbsent(y, (k) -> new ArrayList<>()).add(new int[]{x, cost});
        }

        pq.add(new int[]{1, 1, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int x = cur[0], y = cur[1], cost = cur[2];
            if (!visited.contains(y)) {
                visited.add(y);
                costs += cost;
                for (int[] neighbor : graph.get(y)) {
                    pq.add(new int[]{y, neighbor[0], neighbor[1]});
                }
            }
        }

        return visited.size() == n ? costs : -1;
    }
}
