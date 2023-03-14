/**
 * @Author jzfeng
 * @Date 3/13/23-10:46 PM
 */

package com.jz.algo.datastructureDesign;

//[304. Range Sum Query 2D - Immutable](https://leetcode.com/problems/range-sum-query-2d-immutable/description/)
class RangeQueryMatrix {

    int[][] dp;

    public RangeQueryMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        this.dp = new int[m + 1][ n + 1];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n; j++) {
                dp[i + 1][j + 1] = dp[i + 1][j] + dp[i][j + 1] - dp[i][j] + matrix[i][j] ;
            }
        }
    }

    public int sumRegion(int r1, int c1, int r2, int c2) {
        return dp[r2 + 1][c2 + 1] - dp[r1][c2 + 1] - dp[r2 + 1][c1] + dp[r1 ][c1];
    }
}