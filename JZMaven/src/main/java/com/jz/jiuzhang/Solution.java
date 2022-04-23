package com.jz.jiuzhang;


import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "tree";
        String res = solution.frequencySort(s);
        System.out.println(res);

    }

    public String frequencySort(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        int len = s.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int maxFreq = Collections.max(map.values());
        List<List<Character>> buckets = new ArrayList<>();
        for (int i = 0; i <= maxFreq; i++) {
            buckets.add(new ArrayList<>());
        }

        for (Character key : map.keySet()) {
            int freq = map.get(key);
            buckets.get(freq).add(key);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = buckets.size() - 1; i >= 0; i--) {
            for (Character c : buckets.get(i)) {
                for (int j = 0; j < i; j++) {
                    sb.append(c);
                }
            }
        }

        return sb.toString().trim();
    }


}

class TrieNode {
    Map<Character, TrieNode> children = new HashMap();
    boolean isWord = false;
}

class StreamChecker {
    TrieNode root = new TrieNode();
    Deque<Character> stream = new ArrayDeque();

    public StreamChecker(String[] words) {
        for (String word : words) {
            TrieNode cur = root;
            for (int i = word.length() - 1; i >= 0 ; i--) {
                char ch = word.charAt(i);
                if (!cur.children.containsKey(ch)) {
                    cur.children.put(ch, new TrieNode());
                }
                cur = cur.children.get(ch);
            }

            cur.isWord = true;
        }
    }

    public boolean query(char letter) {
        stream.addFirst(letter);

        TrieNode cur = root;
        for (char c : stream) {
            if (cur.isWord) {
                return true;
            }
            if (!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }

        return cur.isWord;
    }
}
