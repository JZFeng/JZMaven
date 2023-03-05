/**
 * @Author jzfeng
 * @Date 3/5/23-11:25 AM
 */

package com.jz.java.dataStructure.array;

class PrefixSum {
    /* 前缀和主要适用的场景是原始数组不会被修改的情况下，频繁查询某个区间的累加和。
    前缀和数组,presum[i] 表示前i个数的和,(0， i-1）
    sum(i,j) = presum[j + 1] - presum[i];
    构建前缀和数组：
*/
    private int[] prefix;

    /* 输入一个数组，构造前缀和 */
    public PrefixSum(int[] nums) {
        prefix = new int[nums.length + 1];
        // 计算 nums 的累加和
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }

    /* 查询闭区间 [i, j] 的累加和 */
    public int query(int i, int j) {
        return prefix[j + 1] - prefix[i];
    }
}