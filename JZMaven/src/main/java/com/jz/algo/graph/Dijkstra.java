/**
 * @Author jzfeng
 * @Date 4/24/22-10:50 AM
 */

package com.jz.algo.graph;

import java.util.*;

/**
 Dijkstra算法可以解决无负权图的最短路径问题，只能应付单源起点的情况。
 * 针对稠密网，当 边数量 接近点数量的平方时，采用邻接矩阵存图，枚举算法进行更好
 * 针对稀疏网，当 边数量接近点的数量时，采用邻接表存图，优先队列实现Dijkstra更好
 */
public class Dijkstra {

    //邻接表存储图
    List<int[]>[] graph;

    //源点到其他点的最短距离，dis[start] = 0, 其他为 +∞
    int[] distance;

    //记录前一个节点；
    Integer[] prev;

    //结点 0 - n-1 是否访问
    boolean[] visited;

    //正无穷，除2的意义在于 距离相加时不会溢出int
    public static final int INF = Integer.MAX_VALUE / 2;

    public void dijkstra(int n, int[][] edges, int s) {
        // 1. 抽象化。根据edge信息建图。
        // 注意：有时候edge并不会直接给出，比如一些题目给出的是字符串表示的结点，那么需要使用 Map<String, Integer> 来 给字符串编号，再抽象化
        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            // edge三元组，表示{start,end, weight};
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        // 2. 初始化源点到其他点的最短距离
        distance = new int[n];
        Arrays.fill(distance, INF);
        // 源点到自身的距离为0
        distance[s] = 0;

        prev = new Integer[n];

        // 3. 初始化访问标志，默认为false
        visited = new boolean[n];

        // 4. 初始化优先队列, 根据权值升序排序
        //PQ里存放的是点的信息:顶点以及起点到该顶点的距离,(vertex, distance)
        //信息以distance升序排列；
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[]{s, 0});

        // 5. dijkstra
        while (!pq.isEmpty()) {
            // 弹出最短路长度最小的点 和 其权值
            int[] cur = pq.poll();
            int u = cur[0];
            // 访问过，跳过
            if (visited[u]) {
                continue;
            }

            visited[u] = true;
            // 遍历所有 u 能够到达的点，刚开始为 u = s
            for (int[] neighbor : graph[u]) {
                // 下一个点 v, 即其边权值 w
                int v = neighbor[0], w = neighbor[1];
                if (distance[v] > distance[u] + w) {
                    // s->v 的距离 > s->u 的距离 + u->v 的距离，更新最短距离;
                    distance[v] = distance[u] + w;
                    prev[v] = u;
                    // 加入优先队列，s->v 的距离 dis[v]
                    pq.add(new int[]{v, distance[v]});
                }
            }
        }
    }

    public List<Integer> getPath(Integer end){
        List<Integer> path = new LinkedList<>();

        while( end != null){
            path.add(0, end);
            end = prev[end];
        }

        return path;
    }

    //从起点到终点的最短距离
    public int getShortestDistance(int end){
        return distance[end];
    }

}

