package com.jz.algo.sort;

public class InsertSort {

	public static void main(String[] args) {

	}
	
	public static int[] insertSort(int[] a) {

		if (a == null || a.length == 0)
			throw new IllegalArgumentException("Array is empty.");

		for (int i = 1; i < a.length; i++) {

			int key = a[i];
			int j = i - 1;
			for (; j >= 0; j--) {
				if (key < a[j]) {
					a[j + 1] = a[j];

				}

			}

			a[j + 1] = key;
		}

		return a;
	}

}
