package com.jz;

public class Amazon {
  private static String[] reverseArray2(String[] Array) {
    String[] new_array = new String[Array.length];
    for (int i = 0; i < Array.length; i++) {
      // 反转后数组的第一个元素等于源数组的最后一个元素：
      new_array[i] = Array[Array.length - i - 1];
    }
    return new_array;

  }


  public static void selectSort(int[] a) {
    if (a == null || a.length <= 0) {
      return;
    }
    for (int i = 0; i < a.length; i++) {
      int min = a[i]; //最小值
      int position = i; // 将当前下标定义为最小值下标
      for (int j = i + 1; j < a.length; j++) {
        if (a[j] < min) {// a[j] < min 从小到大排序；a[j] > min 从大到小排序
          min = a[j];
          position = j; // 如果有小于当前最小值的关键字将此关键字的下标赋值给flag
        }
      }
      if (position != i) {
        a[position] = a[i];
        a[i] = min;
      }
    }
  }

  public static void insertSort(int[] source) {
    if (source == null || source.length == 0) {
      return;
    }

    int key = 0, j = 0;

    for (int i = 1; i < source.length; i++) {
      key = source[i];
      j = i - 1;
      while (j >= 0 && source[j] > key) {
        source[j + 1] = source[j];
        j--;
      }

      source[j + 1] = key;
    }

  }

  public static void main(String[] args) {

  }


}
