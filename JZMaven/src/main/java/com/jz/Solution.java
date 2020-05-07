package com.jz;

import java.util.*;

public class Solution {

  public static void main(String[] args) {
    Solution solution = new Solution();
    String[] words = new String[]{"yes","lint","code","yes","code","baby","you","baby","chrome","safari","lint","code","body","lint","code"};
    solution.topKFrequentWords(words, 3);
  }


  private String[] topKFrequentWords(String[] words, int k) {
    // write your code here
    if (words == null || words.length == 0 || k <= 0) {
      return words;
    }
    if (k > words.length) {
      k = k % words.length;
    }


    Map<String, Integer> map = new HashMap<>();
    for (String word : words) {
      if (map.containsKey(word)) {
        map.put(word, map.get(word) + 1);
      } else {
        map.put(word, 1);
      }
    }

    Comparator<Map.Entry<String, Integer>> entryComparator = (e1, e2) -> {
      int res = e2.getValue() - e1.getValue();
      if (res == 0) {
        res = e1.getKey().compareTo(e2.getKey());
      }

      return res;
    };

    List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
    Collections.sort(list, entryComparator);

    String[] res = new String[k];
    int index = 0;
    for (Map.Entry<String, Integer> entry : list) {
      res[index++] = entry.getKey();
    }

    return res;
  }


}