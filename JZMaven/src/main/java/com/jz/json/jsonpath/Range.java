package com.jz.json.jsonpath;

//  x >= start && x <= end
public class Range {
    int start;
    int end;

    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Start : " + start + " , end :" + end;
    }
}
