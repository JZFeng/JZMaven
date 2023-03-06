package com.jz.algo.sort;

public class MergeSort {
	public static void sortInteger(int[] nums) {
		int[] tmp = new int[nums.length];
		mergeSort(nums, 0, nums.length - 1, tmp);
	}
	
	private static void mergeSort(int[] nums, int start, int end, int[] tmp) {
		if (nums == null || nums.length <= 1 ) {
			return; 
		}
		if ( start >= end) {
			return;
		}
		
		int mid = start + (end - start) / 2; 
		mergeSort(nums, start, mid, tmp);
		mergeSort(nums, mid + 1, end, tmp);
		merge(nums, start, mid, end ,tmp);
	}
	
	private static void merge(int[] nums, int start, int mid, int end, int[] tmp) {
		int left = start;
		int right = mid + 1;
		int index = left; 
		while (left <= mid && right <= end) {
			if(nums[left] <= nums[right]) {
				tmp[index++] = nums[left++];
			} else {
				tmp[index++] = nums[right++];
			}
		}
		
		while (left <= mid) {
			tmp[index++] = nums[left++]; 
		}
		while (right <= end) {
			tmp[index++] = nums[right++];
		}
		
		for(int i = start; i <= end; i++) {
			nums[i] = tmp[i];
		}
		
	}
}
