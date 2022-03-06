/**
 * @Author jzfeng
 * @Date 3/5/22-9:19 PM
 */

package com.jz.jiuzhang;

import java.util.*;

class WordDistance {

    Map<String, List<Integer>>  str2index;

    public WordDistance(String[] wordsDict) {
        str2index = new HashMap<>();
        for(int i = 0  ; i < wordsDict.length; i++) {
            String word = wordsDict[i];
            str2index.getOrDefault(word, new ArrayList<>()).add(i);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> l1 = str2index.get(word1);
        List<Integer> l2 = str2index.get(word2);
        int p1 = 0, p2 = 0;
        int min = Integer.MAX_VALUE;
        while( p1 < l1.size() && p2 < l2.size() ) {
            Integer index1 = l1.get(p1);
            Integer index2 = l2.get(p2);
            if( index1 > index2 ) {
                min = Math.min( index1 - index2, min);
                p2++;
            } else {
                min = Math.min(index2 - index1, min);
                p1++;
            }
        }

        return min;
    }
}