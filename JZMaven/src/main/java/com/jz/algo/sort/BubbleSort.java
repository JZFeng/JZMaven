package com.jz.algo.sort;

public class BubbleSort {

	public static void main(String[] args) {

	}

	public static int[] bubbleSort(int[] a) {

		if (a == null || a.length == 0)
			throw new IllegalArgumentException("Array is empty.");

		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] > a[j]) {
					int tmp = a[i];
					a[i] = a[j];
					a[j] = tmp;
				}
			}
		}

		return a;
	}

}
