package com.jz;

import com.jz.pojo.ListNode;
import com.jz.pojo.TreeNode;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UtilsTest {

  @Test
  public void testDeserializeList()  {
    String[] lists = new String[] {
        "1"
//        "1,2",
//        "1,2,3,4"
    };

    for (String list : lists) {
      ListNode head = ListNode.of(list);
      String l = head.toString();
      assertTrue(list.equals(l), "Wrong deserialization list: " + list);
    }
  }


  @Test
  public void testDeserializeTree()  {
    String[] trees = new String[] {
        "3,9,20,#,#,15,7",
        "1",
        "1,2,3"
    };

    for (String tree : trees) {
      TreeNode root = TreeNode.of(tree);
      String t = root.toString();
      assertTrue(t.equals(tree));
    }
  }
}