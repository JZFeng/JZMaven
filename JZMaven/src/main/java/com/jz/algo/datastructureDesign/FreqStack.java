/**
 * @Author jzfeng
 * @Date 3/8/22-12:23 AM
 */

package com.jz.algo.datastructureDesign;

import java.util.*;

/*[895. Maximum Frequency Stack](https://leetcode.com/problems/maximum-frequency-stack/)
这题和LFU类似；
首先 想到的是Map<Integer, Integer> num2freq;
其次，维护一个maxFreq
那么问题来了，当maxFreq一样的时候，怎么找到最新的那个元素呢？
那就使用Map<Integer, Stack<Integer>> freq2stack吧；*/
class FreqStack {

    int maxFreq ;
    Map<Integer, Integer> num2freq;
    Map<Integer, Deque<Integer>> freq2stack;

    public FreqStack() {
        this.maxFreq = 0;
        this.num2freq = new HashMap<>();
        this.freq2stack = new HashMap<>();
    }

    public void push(int val) {
        int curFreq = num2freq.getOrDefault(val, 0) + 1;
        num2freq.put(val, curFreq);
        maxFreq = Math.max(maxFreq, curFreq);
        freq2stack.putIfAbsent(curFreq, new ArrayDeque<>());
        freq2stack.get(curFreq).push(val);
    }

    public int pop() {
        Integer maxFreqNum = freq2stack.get(maxFreq).pop();
        num2freq.put(maxFreqNum, num2freq.get(maxFreqNum) - 1 );
        // 如果当前最高频率的栈为空, 说明最高频率需要更新 (减去 1)
        if( freq2stack.get(maxFreq).size() == 0 ) {
            maxFreq--;
        }
        return maxFreqNum;
    }
}