/**
 * @Author jzfeng
 * @Date 2/28/23-8:51 AM
 */

package com.jz.pojo;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    TrieNode root;

    public Trie() {
        this.root = new TrieNode(null, new HashMap<>());
    }

    public void insert(String word) {
        int len = word.length();
        TrieNode cur = root;
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            if (!cur.children.containsKey(c)) {
                TrieNode node = new TrieNode(c, new HashMap<>());
                cur.children.put(c, node);
            }
            cur = cur.children.get(c);
        }
        cur.isWord = true;
    }

    public boolean search(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        int len = word.length();
        TrieNode cur = root;
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            if (!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }

        return cur.isWord;
    }

    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return false;
        }
        int len = prefix.length();
        TrieNode cur = root;
        for (int i = 0; i < len; i++) {
            char c = prefix.charAt(i);
            if (!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }

        return true;
    }
}

class TrieNode {
    Character c;
    Map<Character, TrieNode> children;
    boolean isWord;

    TrieNode(Character c, Map<Character, TrieNode> children) {
        this.c = c;
        this.children = children;
        this.isWord = false;
    }
}