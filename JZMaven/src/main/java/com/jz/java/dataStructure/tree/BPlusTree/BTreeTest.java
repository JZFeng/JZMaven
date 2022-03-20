package com.jz.java.dataStructure.tree.BPlusTree;

public class BTreeTest {
    public static void main(String args[]) {
        IntegerBTree tree = new IntegerBTree();

        // test for the btree on assignment4
        tree.insert(1);
        tree.insert(78);
        tree.insert(37);
        tree.insert(150);
        tree.insert(35);
        tree.insert(145);
        tree.insert(19);
        tree.insert(24);

        tree.insert(10);
        tree.insert(210);
        tree.insert(17);
        tree.insert(20);
        tree.insert(30);
        tree.insert(201);

        tree.insert(140);
        tree.insert(207);
        tree.insert(120);
        tree.insert(5);

        tree.insert(115);
        tree.insert(51);
        tree.insert(40);
        tree.insert(7);

        // test for the btree on notes
        tree = new IntegerBTree();
        tree.insert(10);
        tree.insert(48);
        tree.insert(23);
        tree.insert(33);
        tree.insert(12);

        tree.insert(50);

        tree.insert(15);
        tree.insert(18);
        tree.insert(20);
        tree.insert(21);
        tree.insert(31);
        tree.insert(45);
        tree.insert(47);
        tree.insert(52);

        tree.insert(30);

        tree.insert(19);
        tree.insert(22);

        tree.insert(11);
        tree.insert(13);
        tree.insert(16);
        tree.insert(17);

        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);
        tree.insert(8);
        tree.insert(9);

        // tree.remove(18);
        // tree.remove(12);
        tree.remove(33);

        tree.remove(10);
        tree.remove(22);
        tree.remove(21);

        tree.remove(12);
        tree.remove(15);
        tree.remove(18);
        tree.remove(19);
        tree.remove(20);

        tree.remove(23);
        tree.remove(30);
        tree.remove(31);

        tree.remove(45);
        tree.remove(47);
        tree.remove(48);
        tree.remove(50);
        tree.remove(52);

        return;
    }
}

class IntegerBTree extends BTree<Integer, Integer> {
    public void insert(int key) {
        this.insert(key, key);
    }

    public void remove(int key) {
        this.delete(key);
    }
}