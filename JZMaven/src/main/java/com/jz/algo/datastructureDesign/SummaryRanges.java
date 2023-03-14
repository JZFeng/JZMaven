/**
 * @Author jzfeng
 * @Date 3/7/23-4:48 PM
 */

package com.jz.algo.datastructureDesign;

import java.util.*;
/*
[352. Data Stream as Disjoint Intervals]
https://leetcode.com/problems/data-stream-as-disjoint-intervals/
 */
class SummaryRanges {
    //用TreeSet存放排序之后的intervals;
    TreeSet<int[]> intervals;

    public SummaryRanges() {
        this.intervals = new TreeSet<>( (a,b) -> a[0] - b[0] );
    }

    public void addNum(int value) {
        int[] interval = new int[]{value, value};
        if(intervals.contains(interval)){
            return;
        }

        int[] lower = intervals.lower(interval);
        int[] higher = intervals.higher(interval);
        if( higher != null && higher[0] == value ){
            return;
        }

        //LOWER -- INTERVAL -- HIGHTER , 三种关系：1 链接左右； 2 链接lower 3链接higher;
        if(  lower!= null && lower[1] + 1 == value && higher != null && value + 1 == higher[0]   ){
            lower[1] = higher[1];
            intervals.remove(higher);
        } else if(lower!= null && lower[1] + 1 >= value ){ //这是个坑，没考虑过 >=,原来的interval中已经包含了value;
            lower[1] = Math.max(lower[1], value );
        } else if( higher != null && value + 1 == higher[0] ) {
            higher[0] = Math.min(value , higher[0]);
        } else {
            intervals.add(interval);
        }
    }

    public int[][] getIntervals() {
        int[][] res = new int[intervals.size()][];
        int index = 0;
        for( int[] interval : intervals){
            res[index++] = interval;
        }
        return res;
    }
}

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges obj = new SummaryRanges();
 * obj.addNum(value);
 * int[][] param_2 = obj.getIntervals();
 */