package com.jz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

class Dummy {
}


class Solution {
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length, n = maze[0].length;
        int ex = hole[0], ey = hole[1];

        int[][] dirs = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
        char[] dch = {'d', 'l', 'r', 'u'};
        boolean[][] visted = new boolean[m][n];

        PriorityQueue<Node> pq = new PriorityQueue<>((x, y) -> (x.dist == y.dist) ? x.path.compareTo(y.path) : x.dist - y.dist);
        pq.offer(new Node(ball[0], ball[1], 0, ""));

        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        while (!pq.isEmpty()) {
            Node node = pq.poll();

            if (visted[node.x][node.y]) continue;
            if (node.x == ex && node.y == ey) return node.path;

            for (int i = 0; i < 4; i++) {
                int x = node.x, y = node.y, d = node.dist, dx = dirs[i][0], dy = dirs[i][1];

                while (isValid(maze, x,y)) {
                    x += dx;y += dy; d++;
                    if (x == ex && y == ey) {
                        return node.path + dch[i];
                    }
                }
                x += dx;y += dy; d--;
                if( d < dist[x][y] ) {
                    dist[x][y] = d;
                    pq.offer(new Node(x,y, dist[x][y], node.path + dch[i]));
                }

                visted[node.x][node.y] = true;
            }
        }

        return "impossible";
    }

    private boolean isValid(int[][] maze, int x, int y) {
        int m = maze.length, n = maze[0].length;
        return x >= 0 && x < m && y >= 0 && y < n && maze[x][y] != 1;
    }
}

class Node {
    int x, y, dist;
    String path;

    Node(int x, int y, int dist, String path) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.path = path;
    }
}