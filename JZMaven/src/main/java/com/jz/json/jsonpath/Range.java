package com.jz.json.jsonpath;

//  x >= start && x <= end
public class Range implements Filter {
    private int start;
    private int end;

    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isValid() {
        return (start < end) ? false: true;
    }

    public int getStart(){
        return start;
    }

    public int getEnd(){
        return end;
    }


    @Override
    public String toString() {
        return "Start : " + start + " , end :" + end;
    }
}
