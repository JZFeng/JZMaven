package com.jz;

import java.util.*;

import com.jz.Utils.*;


class Solution {



    public List<List<String>> findLadders(String beginWord, String endWord, List<String> dict) {
        Set<String> dictionary = new HashSet<>(dict);
        //这是个坑，一定要记得把beginWord和endWord加入Set
        List<List<String>> ladders = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        //Corner case，这是题意；
        if (!dict.contains(endWord)) {
            return ladders;
        }
        //这是个坑，一定要记得把beginWord加入Set
        dict.add(beginWord);

        Map<String, List<String>> graph = buildGraph(dictionary);
        Map<String, Integer> distance = bfs(endWord, beginWord, graph);
        List<String> path = new ArrayList<>();
        dfs(beginWord, endWord,  path , graph, ladders, distance);

        return ladders;
    }

    private void dfs(String cur,
                     String endWord,
                     List<String> ladder,
                     Map<String, List<String>> graph,
                     List<List<String>> ladders,
                     Map<String, Integer> distance
    ) {
        //backtracking，把问题规模缩小；
        ladder.add(cur);
        //这里用的是equals不是==；
        if( cur.equals(endWord) ) {
            ladders.add(new ArrayList<>(ladder));
        } else {
            List<String> nextWords = graph.get(cur);
            for(String nextWord : nextWords) {
                if(distance.containsKey(nextWord) && distance.get(cur) == distance.get(nextWord)  + 1 ) {
                    dfs( nextWord, endWord,  ladder,graph, ladders, distance );
                }
            }
        }

        ladder.remove(ladder.size() - 1 );
    }


    //distance表示该字符串离终点单词的距离；
    private Map<String, Integer>  bfs( String end,
                                       String start,
                                       Map<String, List<String>> graph) {
        Map<String, Integer> distance = new HashMap<>();

        Queue<String> queue = new LinkedList<>();
        queue.offer(end);
        distance.put(end, 0);
        //直接用distance.containsKey来去重；
        while(!queue.isEmpty()) {
            String cur = queue.poll();
            List<String> nextWords = graph.get(cur);
            for(String nextWord : nextWords) {
                if( !distance.containsKey(nextWord) ) {
                    //这里有个坑，queue.offer(nextWord)必须在if程序块内；
                    distance.put(nextWord, distance.get(cur) + 1);
                    queue.offer(nextWord);
                }
            }
        }

        return distance;
    }

    private Map<String, List<String>> buildGraph(Set<String> dict) {
        Map<String, List<String>> graph = new HashMap<>();
        for(String word: dict) {
            graph.put( word, nextWords(word, dict));
        }
        return graph;
    }

    private List<String> nextWords(String word, Set<String> dict) {
        int len = word.length();
        List<String> res = new ArrayList<>();

        for(int i = 0 ; i < len; i++) {
            for(char j = 'a' ; j <= 'z'; j++) {
                String candidate = word.substring(0, i) + j + word.substring(i + 1);
                if( word.charAt(i) != j && dict.contains(candidate) ) {
                    res.add(candidate);
                }
            }
        }
        return res;
    }
}