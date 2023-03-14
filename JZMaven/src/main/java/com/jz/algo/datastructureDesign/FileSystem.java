/**
 * @Author jzfeng
 * @Date 4/10/22-3:35 PM
 */

package com.jz.algo.datastructureDesign;

import java.util.*;

/**
 [1166. Design File System](https://leetcode.com/problems/design-file-system/)
 解题思路分析：
 * 本题考察的重点在于如何设计数据结构。我们知道文件目录是按照树形结构来设计的，因此我们的数据结构也应该设计为树形结构。
 *
 * 首先定义一个节点类，该类代表了某一级的目录（文件夹）或者是某个文件。类中需要包含以下信息：
 *
 * 该目录（文件夹）下包含的所有子目录（子文件夹）和文件
 * 如果当前目录是文件，记录文件内容的字符串
 */

public class FileSystem {
    Node root;

    public FileSystem() {
        root = new Node();
    }

    public List<String> ls(String path) {
        String[] dirs = path.split("/");
        Node cur = root;
        for (String dir : dirs) {
            if ("".equals(dir)) {
                continue;
            }

            cur = cur.fileList.get(dir);
        }

        List<String> res = new ArrayList<>(cur.fileList.keySet());

        return res;
    }

    public void mkdir(String path) {
        String[] dirs = path.split("/");
        Node cur = root;
        for (String dir : dirs) {
            if ("".equals(dir)) {
                continue;
            }

            Node child = cur.fileList.get(dir);
            if (child == null) {
                child = new Node();
                cur.fileList.put(dir, child);
            }

            cur = child;
        }
    }

    public void addContentToFile(String filePath, String content) {
        String[] dirs = filePath.split("/");
        Node cur = root;
        for (int i = 0; i < dirs.length; i++) {
            String dir = dirs[i];
            if ("".equals(dir)) {
                continue;
            }

            Node child = cur.fileList.get(dir);
            if (child == null) {
                child = new Node();
                cur.fileList.put(dir, child);
            }

            //写入文件；
            if (i == dirs.length - 1) {
                child.text.append(content);
                child.fileList.put(dir, null);
            }

            cur = child;
        }
    }

    public String readContentFromFile(String filePath) {
        String[] dirs = filePath.split("/");
        Node cur = root;
        for (String dir : dirs) {
            if ("".equals(dir)) {
                continue;
            }
            cur = cur.fileList.get(dir);
        }

        return cur.text.toString();
    }
}


class Node {
    // 该目录下的所有子目录和文件
    // key是子目录或者文件名称，key为子目录或文件的节点对象
    Map<String, Node> fileList ;
    // 如果当前目录是文件，记录文件内容
    StringBuilder text;

    Node() {
        this.fileList = new TreeMap<>();
        this.text = new StringBuilder();
    }
}