/**
 * @Author jzfeng
 * @Date 4/24/22-6:14 PM
 */

package com.jz.java.dataStructure.graph;

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


}
