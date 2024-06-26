package com.jz.algo.sort;

// Java program for implementation of Heap Sort
//http://www.geeksforgeeks.org/heap-sort/


import java.util.Arrays;

public class HeapSort {
	public void heapSort(int nums[]) {
		int n = nums.length;

		// Build heap (rearrange array)
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(nums, n, i);

		// One by one extract an element from heap
		for (int i = n - 1; i >= 0; i--) {
			// Move current root to end
			int temp = nums[0];
			nums[0] = nums[i];
			nums[i] = temp;

			// call max heapify on the reduced heap
			heapify(nums, i, 0);
		}
	}

	void heapify(int[] nums, int n, int i) {
		int largest = i; // Initialize largest as root
		int l = 2 * i + 1; // left = 2*i + 1
		int r = 2 * i + 2; // right = 2*i + 2

		// If left child is larger than root
		if (l < n && nums[l] > nums[largest])
			largest = l;

		// If right child is larger than largest so far
		if (r < n && nums[r] > nums[largest])
			largest = r;

		// If largest is not root
		if (largest != i) {
			int swap = nums[i];
			nums[i] = nums[largest];
			nums[largest] = swap;

			// Recursively heapify the affected sub-tree
			heapify(nums, n, largest);
		}
	}
}
