package com.jz;

import java.util.*;

class Dummy {
}

//Backtracking;
class Solution {
    //把不变的数据都变成全局变量，这样dfs的参数会少很多；
    int[] tasks;
    int sessionTime;
    int minSession;

    public int minSessions(int[] tasks, int sessionTime) {
        this.tasks = tasks;
        this.sessionTime = sessionTime;
        this.minSession = tasks.length; //初始化；
        int[] sessions = new int[tasks.length]; //最多tasks.length;

        Arrays.sort(tasks); //剪枝1: 倒序排列

        helper(tasks.length - 1, sessions, 0);
        return minSession;
    }

    private void helper(int start, int[] sessions, int curSession) {
        if (start < 0) {
            minSession = Math.min(minSession, curSession);
            return;
        }
        if (curSession >= minSession) { //剪枝2:全局剪枝
            return;
        }

        //暴力尝试把任务 挨个分给工人(这里是work session)
        //这是内循环，需要知道当前工人数（所以参数里传入curSessions）
        for (int i = 0; i < curSession; i++) {
            if (i != 0 && sessions[i] == sessions[i - 1]) {
                continue;  //剪枝3
            }
            if (sessions[i] + tasks[start] <= sessionTime) {
                sessions[i] += tasks[start];
                helper(start - 1, sessions, curSession);
                sessions[i] -= tasks[start];//backtracking
            }
        }
        sessions[curSession] += tasks[start];
        helper(start - 1, sessions, curSession + 1);
        sessions[curSession] -= tasks[start];//backtracking
    }
}


