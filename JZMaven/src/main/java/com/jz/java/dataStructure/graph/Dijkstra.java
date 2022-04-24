/**
 * @Author jzfeng
 * @Date 4/24/22-10:50 AM
 */

package com.jz.java.dataStructure.graph;

import java.util.*;

/**
 * @author jzfeng
 * @date 2022/4/21 16:20
 * @description Dijkstra算法：一种求解 非负权图上 单源最短路径算法，流程有两步：
 * 将结点分成两个集合：已确定最短路长度的点集（记为 S 集合）的和未确定最短路长度的点集（记为 T 集合）。一开始所有的点都属于 T 集合。
 * 定义：n 为 图上点的数目，m 为 图上边的数目
 * s 为最短路的源点
 * D(u) 为 s点到 u点的实际最短路长度，dis(u)为 s -> u 点的 估计最短路长度，
 * w(u,v)为 (u, v)这一条边的边权值。
 * 初始化 dis(s) = 0，其他点的 dis 均为 +∞。
 * <p>
 * 然后重复这些操作：
 * <p>
 * 1. 从 T 集合中，选取一个最短路长度最小的结点，移到 S集合中。
 * 2. 对那些刚刚被加入 S 集合的结点的所有出边执行松弛操作。
 * 直到 T 集合为空，算法结束。
 * <p>
 * 针对稠密网，当 边数量 接近点数量的平方时，采用邻接矩阵存图，枚举算法进行更好
 * 针对稀疏网，当 边数量接近点的数量时，采用邻接表存图，优先队列实现Dijkstra更好
 */
public class Dijkstra {
    //邻接表存储图
    List<int[]>[] graph;

    //源点到其他点的最短距离，dis[s] = 0, 其他为 +∞
    int[] dist;

    //结点 0 - n-1 是否访问
    boolean[] visited;

    //正无穷，除2的意义在于 距离相加时不会溢出int
    public static final int INF = Integer.MAX_VALUE / 2;

    /**
     * 算法
     *
     * @param n    n个结点
     * @param edges 有向边 + 权值，如[1, 2, 5]表示 结点 1->2 的边权值为 5
     * @param s    源点 0 <= s < n
     */

    public void dijkstra(int n, int[][] edges, int s) {
        // 1. 抽象化。根据edge信息建图。
        // 注意：有时候edge并不会直接给出，比如一些题目给出的是字符串表示的结点，那么需要使用 Map<String, Integer> 来 给字符串编号，再抽象化
        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            // w(edge[0], edge[1]) = edge[2]
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }
        // 2. 初始化源点到其他点的最短距离
        dist = new int[n];
        Arrays.fill(dist, INF);
        // 源点到自身的距离为0
        dist[s] = 0;

        // 3. 初始化访问标志，默认为false
        visited = new boolean[n];

        // 4. 初始化优先队列, 根据权值升序排序
        //PQ里存放的是点信息；
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
            for (int[] q : graph[u]) {
                // 下一个点 v, 即其边权值 w
                int v = q[0], w = q[1];
                if (dist[v] > dist[u] + w) {
                    // s->v 的距离 > s->u 的距离 + u->v 的距离，更新最短距离，注意时 s-> 其他点 距离为 +∞
                    dist[v] = dist[u] + w;
                    // 加入优先队列，s->v 的距离 dis[v]
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }
    }
}

