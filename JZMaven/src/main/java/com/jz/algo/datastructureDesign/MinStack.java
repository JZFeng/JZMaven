/**
 * @Author jzfeng
 * @Date 3/13/23-10:39 PM
 */

package com.jz.algo.datastructureDesign;

import java.util.ArrayDeque;
import java.util.Deque;

//[155. Min Stack](https://leetcode.com/problems/min-stack/)
class MinStack {
    private Deque<Integer> stack;
    private Deque<Integer> min;

    public MinStack() {
        this.stack = new ArrayDeque<Integer>();
        this.min = new ArrayDeque<Integer>();
    }

    public void push(int val) {
        stack.push(val);
        if( min.isEmpty() || val <= min.peek()   ) { //这里是个细节 <= 而不是<
            min.push(val);
        }
    }

    public void pop() {
        if( !stack.isEmpty() ) {
            Integer v = stack.pop();
            if( !min.isEmpty() && min.peek().equals(v )) {
                min.pop();
            }
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min.peek();
    }
}

