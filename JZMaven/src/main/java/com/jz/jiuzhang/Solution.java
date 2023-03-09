package com.jz.jiuzhang;


import java.util.*;

class Solution {

    //bfs在matrix上的应用

    int[] d_x = new int[]{1,-1,0,0};
    int[] d_y = new int[]{0,0,1,-1};

    public int shortestDistance(int[][] maze, int[] start, int[] destination)  {
        int m = maze.length, n = maze[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];

        queue.offer(start);
        visited[start[0]][start[1]] = true;
        int steps = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int j = 0; j < size; j++) {
                int[] cur = queue.poll();
                if (cur[0] == destination[0] && cur[1] == destination[1]) {
                    return steps;
                }
                //四个方向；
                for (int i = 0 ; i < 4; i++) {
                    int x = cur[0] + d_x[i];
                    int y = cur[1] + d_y[i];

                    //一直往前滚；
                    while (isValid(x,y, maze)) {
                        x += d_x[i];
                        y += d_y[i];
                    }
                    x -= d_x[i];
                    y -= d_y[i];

                    if (!visited[x][y]) {
                        queue.offer(new int[]{x, y});
                        visited[x][y] = true;
                    }
                }
            }
            steps++;
        }

        return -1;
    }

    private boolean isValid(int x, int y, int[][] maze){
        int m = maze.length, n = maze[0].length;
        return x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0;
    }

}