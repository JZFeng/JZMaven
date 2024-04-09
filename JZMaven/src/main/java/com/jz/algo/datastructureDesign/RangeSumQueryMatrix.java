/**
 * @Author jzfeng
 * @Date 3/13/23-10:46 PM
 */

package com.jz.algo.datastructureDesign;

//[304. Range Sum Query 2D - Immutable](https://leetcode.com/problems/range-sum-query-2d-immutable/description/)

class RangeSumQueryMatrix {
    //用DP先求，然后通过几何方程式计算；
    //dp[i][j]表示从(0,0)到（i，j）的sum;
    int[][] dp;
    public RangeSumQueryMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        this.dp = new int[m + 1][n+ 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i+1][j+1] = dp[i+1][j] + dp[i][j+1] - dp[i][j] + matrix[i][j];
            }
        }
    }

    public int sumRegion(int r1, int c1, int r2, int c2) {
        return dp[r2 + 1][c2 +1] - dp[r1][c2+1] - dp[r2 +1][c1] + dp[r1][c1];
    }
}

class NumMatrix1 {
    //用DP先求，然后通过几何方程式计算；
    //dp[i][j]表示从(0,0)到（i，j）的sum;
    int[][] dp;

    public NumMatrix1(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        this.dp = new int[m][n];
        dp[0][0] = matrix[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = matrix[i][0] + dp[i - 1][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = matrix[0][j] + dp[0][j - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j] - dp[i - 1][j - 1] + matrix[i][j];
            }
        }
    }

    public int sumRegion(int r1, int c1, int r2, int c2) {
        if (r1 == 0 && c1 == 0) {
            return dp[r2][c2];
        } else if (r1 == 0) {
            return dp[r2][c2] - dp[r2][c1 - 1];
        } else if (c1 == 0) {
            return dp[r2][c2] - dp[r1 - 1][c2];
        }
        return dp[r2][c2] - dp[r1 - 1][c2] - dp[r2][c1 - 1] + dp[r1 - 1][c1 - 1];
    }
}
