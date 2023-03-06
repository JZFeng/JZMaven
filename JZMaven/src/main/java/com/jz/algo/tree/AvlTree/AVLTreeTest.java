package com.jz.algo.tree.AvlTree;

public class AVLTreeTest {

    private static int arr[] =
//        {10,40,20,30};
            {10, 20, 30, 40, 50, 60, 70};
//        {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};


    public static void main(String[] args) {
        int i;
        AVLTree<Integer> tree = new AVLTree<Integer>();

        System.out.print("== �������: ");
        for (i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
            tree.add(arr[i]);
        }

        System.out.println("\n�������: ");
        tree.inOrder();
        System.out.println();

        System.out.println("== ��α���: ");
        tree.levelOrder1();
        System.out.println();
        
        /*  
        System.out.println("== ��α���1: ");
        tree.levelOrder1();
        System.out.println();
        
        System.out.println("== ��α���2: ");
        tree.levelOrder2();
        System.out.println();
      
        System.out.println("== ���ĸ߶�: " + tree.height());
        System.out.println("== ��Сֵ: " + tree.minimum());
        System.out.println("== ���ֵ: " + tree.maximum());
       
        System.out.println("��ǰ���ĸ�ڵ���: "+ tree.getRoot().key);
        System.out.println("");        
        
        i = 7;
        System.out.println("== ɾ��ڵ�:" + i);
        tree.remove(i);

        System.out.println("== �����ĸ߶�:" + tree.height());
        System.out.println("== ��������Сֵ: " + tree.minimum());
        System.out.println("== ���������ֵ: " + tree.maximum());
        System.out.println("== ��������ϸ��Ϣ: ");
        tree.levelOrder1();
        System.out.println("\n�����ĸ�ڵ���: "+ tree.getRoot().key);
*/
        // ��ٶ�����
        tree.destroy();
    }

}