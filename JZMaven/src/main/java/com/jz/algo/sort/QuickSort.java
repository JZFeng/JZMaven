package com.jz.algo.sort;


public class QuickSort {

    private static void sortInteger(int[] nums) {
        quickSortII(nums, 0, nums.length - 1);
    }

    private static void quickSortII(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }

        int p = partition(nums, start, end);
        quickSortII(nums, start, p - 1);
        quickSortII(nums, p, end);
    }

    //partition function , RETURN VALUE IS ALWAYS "left";
    //快速排序，partition的返回值 是以Pivot为中轴partition之后， nums[index]及之后的元素都 大于等于Pivot；
    //the return value of partition function is NOT the position of Pivot !!!!!!
    private static int partition(int[] nums, int start, int end) {
        if (start >= end) {
            return -1;
        }

        int left = start;
        int right = end;
        int mid = left + (start - left) / 2;
        int pivot = nums[mid];
        while (left <= right) {
            while (left <= right && nums[left] < pivot) {
                left++;
            }
            while (left <= right && nums[right] > pivot) {
                right--;
            }
            if (left <= right) {
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;
                left++;
                right--;
            }
        }

        return left;
    }

    private static void quickSort(int[] nums, int start, int end) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        if (start >= end) {
            return;
        }

        int left = start;
        int right = end;
        int mid = left + (right - left) / 2;
        int pivot = nums[mid]; // set the PIVOT in advance.

        while (left <= right) {
            while (left <= right && nums[left] < pivot) {
                left++;
            }
            while (left <= right && nums[right] > pivot) {
                right--;
            }

            if (left <= right) {
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;
                left++;
                right--;
            }
        }

        quickSort(nums, start, right);
        quickSort(nums, left, end);

    }

}
