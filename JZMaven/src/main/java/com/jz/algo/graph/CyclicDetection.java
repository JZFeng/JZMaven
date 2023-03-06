/**
 * @Author jzfeng
 * @Date 4/24/22-6:14 PM
 */

package com.jz.algo.graph;

import java.util.HashMap;
import java.util.*;

//DFS + Parent Node
//neighbor访问过，并且没有走回头路，就表示有环；
public class CyclicDetection {

    Map<Integer, List<Integer>> graph = new HashMap<>();

    public boolean isCyclicUndirectedGraph(int n ) {
        boolean[] visited = new boolean[n];
        for(int i = 0 ; i < n ; i++) {
            if( !visited[i] ) {
                if(isCyclicUtil(i, visited, -1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isCyclicUtil(int cur, boolean[] visited, int parent) {
        visited[cur] = true;
        for(int neighbor : graph.getOrDefault(cur, new ArrayList<>())) {
            if(!visited[neighbor]) {
                if(isCyclicUtil(neighbor, visited, cur)) {
                    return true;
                }
            } else {
                if( neighbor != parent ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCyclicUndirectedGraph2(int n ) {
        DSU dsu = new DSU(n);
        Set<List<Integer>> edges = new HashSet<>();
        for(int i = 0 ; i < n ; i++) {
            for(int neighbor : graph.getOrDefault(i, new ArrayList<>())) {
                List<Integer> edge = new ArrayList<>();
                edge.add(i);
                edge.add(neighbor);
                Collections.sort(edge);
                edges.add(edge);
            }
        }

        for(List<Integer> edge : edges) {
            int a = edge.get(0), b = edge.get(1);
            if(dsu.find(a) == dsu.find(b) ) {
                return true;
            }
            dsu.union(a, b);
        }

        return false;
    }

}
