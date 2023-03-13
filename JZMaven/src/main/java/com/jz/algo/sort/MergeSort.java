package com.jz.algo.sort;

public class MergeSort {
	public int[] mergeSort(int[] nums) {
		return divide(nums, 0, nums.length - 1);
	}

	private int[] divide(int[] nums, int left, int right) {
		if( left >= right) {
			return  new int[]{nums[left]};
		}

		int mid = left + (right - left ) / 2;
		int[] l = divide(nums, left, mid);
		int[] r = divide(nums, mid + 1, right);

		return merge(l, r);
	}


	public int[] merge(int[] nums1, int[] nums2) {
		int m = nums1.length, n = nums2.length;
		int[] res = new int[m + n];
		int i = 0, j = 0, index = 0;
		while (i < m && j < n) {
			if (nums1[i] < nums2[j]) {
				res[index++] = nums1[i++];
			} else {
				res[index++] = nums2[j++];
			}
		}
		while (i < m) {
			res[index++] = nums1[i++];
		}
		while (j < n) {
			res[index++] = nums2[j++];
		}
		return res;
	}
}
