package com.jz.jiuzhang;


import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] products = new String[]{"mobile","mouse","moneypot","monitor","mousepad"};
        String word = "mouse";
        List<List<String>> lists = solution.suggestedProducts(products, word);
        lists.forEach(System.out::println);
    }


    private static final int size = 3;
    private TrieNode root;
    private List<List<String>> res;

    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.next.containsKey(c)) {
                cur.next.put(c, new TrieNode());
            }
            cur = cur.next.get(c);
            cur.queue.offer(word);
            if (cur.queue.size() > size) {
                cur.queue.poll();
            }
        }

        cur.isEnd = true;
    }

    public void startWith(String word) {
        TrieNode cur = root;
        boolean exist = true;
        for (char c : word.toCharArray()) {
            if (!exist || !cur.next.containsKey(c)) {
                exist = false;
                res.add(new ArrayList<>());
                continue;
            }
            cur = cur.next.get(c);
            List<String> tmp = new ArrayList<>();
            while (!cur.queue.isEmpty()) {
                tmp.add(cur.queue.poll());
            }
            Collections.reverse(tmp);
//            cur.queue.addAll(tmp);
            res.add(tmp);
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        res = new ArrayList<>();
        root = new TrieNode();
        for (String s : products) {
            insert(s);
        }

        startWith(searchWord);

        return res;
    }
}

class TrieNode {
    Map<Character, TrieNode> next = new HashMap<>();
    boolean isEnd;
    PriorityQueue<String> queue;

    public TrieNode() {
        next = new HashMap<>();
        queue = new PriorityQueue<>((o1, o2) -> o2.compareTo(o1));
    }
}
