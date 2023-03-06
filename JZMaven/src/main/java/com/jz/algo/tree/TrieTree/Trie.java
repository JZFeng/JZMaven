/**
 * @Author jzfeng
 * @Date 3/22/22-12:38 AM
 */

package com.jz.algo.tree.TrieTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("application");
        trie.insert("appeal");
        trie.insert("app");
        List<String> words = trie.findWordsByPrefix("ap");
        words.stream().forEach(System.out::println);
    }

    public TrieNode root;

    public Trie() {
        //此处使用null表示root，或者选一个完全用不到的字符;
        this.root = new TrieNode(null, new HashMap<>(), false);
    }

    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode(c, new HashMap<>(), false));
            }
            cur = cur.children.get(c);
        }
        cur.isWord = true;
    }

    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toLowerCase().toCharArray()) {
            if (!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }

        return cur.isWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toLowerCase().toCharArray()) {
            if (!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }

        return true;
    }

    public List<String> findWordsByPrefix(String prefix) {
        prefix = prefix.trim().toLowerCase();
        List<String> res = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return res;
        }

        TrieNode prefixNode = findNodeByPrefix(prefix);
        List<String> words = helper(prefixNode);

        prefix = prefix.substring(0, prefix.length() - 1);
        for (int i = 0; i < words.size(); i++) {
            words.set(i, prefix + words.get(i));
        }

        return words;
    }

    //从该node开始往下所有的word；
    public List<String> helper(TrieNode node) {
        List<String> res = new ArrayList<>();
        if (node == null) {
            return res;
        }
        if (node.isWord) {
            res.add(node.c + "");
        }

        if (node.children.size() != 0) {
            List<List<String>> lists = new ArrayList<>();
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                TrieNode value = entry.getValue();
                List<String> words = helper(value);
                lists.add(words);
            }

            for (List<String> list : lists) {
                for (String w : list) {
                    res.add((node.c == null ? "" : node.c) + w);
                }
            }
        }

        return res;
    }

    //根据prefix找到节点；
    public TrieNode findNodeByPrefix(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return null;
        }

        TrieNode cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!cur.children.containsKey(c)) {
                return null;
            }
            cur = cur.children.get(c);
        }

        return cur;
    }
}

class TrieNode {
    Character c;
    Map<Character, TrieNode> children;
    Boolean isWord;

    TrieNode(Character c, Map<Character, TrieNode> children, Boolean isWord) {
        this.c = c;
        this.children = children;
        this.isWord = isWord;
    }
}