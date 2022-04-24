package com.jz.jiuzhang;


import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int costs = solution.minCostToSupplyWater(3, new int[]{1, 2, 2}, new int[][]{{1, 2, 1}, {2, 3, 1}});
        System.out.println(costs);
    }

    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        Map<Integer, Map<Integer,Integer>> graph = new HashMap<>();
        // 图，起点 -> Map（终点列表，cost）；
        //加虚拟节点；
        for(int i = 1; i <= n ; i++) {
            graph.computeIfAbsent(0, k -> new HashMap<>() ).put(i, wells[i - 1]);
        }
        //建图；
        for(int i = 0 ; i < pipes.length; i++) {
            int[] edge = pipes[i];
            int x = edge[0], y = edge[1],cost = edge[2];

            int minFrom0To1 = graph.computeIfAbsent(x, k -> new HashMap<>()).getOrDefault(y, Integer.MAX_VALUE);
            int minFrom1To0 = graph.computeIfAbsent(y, k -> new HashMap<>()).getOrDefault(x, Integer.MAX_VALUE);
            graph.get(x).put(y, Math.min(cost, minFrom0To1));
            graph.get(y).put(x, Math.min(cost, minFrom1To0));
        }

        int costs = 0 ;
        PriorityQueue<int[]> pq = new PriorityQueue<>( (a,b) -> a[1] - b[1]);
        Set<Integer> visited = new HashSet<>();
        pq.offer(new int[]{0,0}); // PQ里存放的是{curNode, cost};
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curNode = cur[0], cost = cur[1];
            if( !visited.add(curNode) ) {
                continue;
            }
            costs += cost;
            for(int neighbor : graph.getOrDefault(curNode, new HashMap<>()).keySet()) {
                if(!visited.contains(neighbor)) {
                    pq.offer(new int[]{neighbor, graph.get(curNode).get(neighbor)});
                }
            }
        }

        return costs;
    }
}