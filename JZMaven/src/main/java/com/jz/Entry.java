package com.jz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class Entry {
  private static final Log log = LogFactory.getLog(Entry.class);

  public static void main(String[] args) {
    int[] nums = new int[] {10, 12, 14, 21, 22, 23, 23, 23, 23, 32, 35, 43, 43, 43, 45, 45, 56, 60, 76, 89};
    Arrays.sort(nums);
    StringBuilder sb = new StringBuilder();
    for (int num : nums) {
      sb.append(num + ",");
    }
    log.info(sb.toString());
    log.info(percentile95(nums));
  }

  private static int percentile95(int[] nums) {
    return 0;
  }

}
