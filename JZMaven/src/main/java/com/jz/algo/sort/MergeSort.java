package com.jz.algo.sort;

public class MergeSort {

    int[] temp;

    public void mergeSort(int[] nums) {
        // use a shared temp array, the extra memory is O(n) at least
        this.temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
    }

    private void mergeSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }

        int left = start, right = end;
        int mid = left + (right - left) / 2;

        mergeSort(nums, start, mid);
        mergeSort(nums, mid + 1, end);
        merge(nums, start, mid, end);
    }

    private void merge(int[] nums, int start, int mid, int end) {
        int left = start;
        int right = mid + 1;
        int index = start;

        // merge two sorted subarrays in nums to temp array
        while (left <= mid && right <= end) {
            if (nums[left] < nums[right]) {
                temp[index++] = nums[left++];
            } else {
                temp[index++] = nums[right++];
            }
        }
        while (left <= mid) {
            temp[index++] = nums[left++];
        }
        while (right <= end) {
            temp[index++] = nums[right++];
        }

        // copy temp back to nums
        for (index = start; index <= end; index++) {
            nums[index] = temp[index];
        }
    }
}