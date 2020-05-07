package com.jz;

import org.testng.Assert;
import org.testng.annotations.Test;
import static com.jz.Utils.*;
import static org.testng.Assert.*;

public class UtilsTest {

  @Test
  public void testDeserializeList()  {
    String[] lists = new String[] {
        "null",
        "1->null",
        "1->2->null",
        "1->2->3->4->null"
    };

    for (String list : lists) {
      ListNode head = deserializeList(list);
      String l = serializeList(head);
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
      TreeNode root = deserializeTree(tree);
      String t = serializeTree(root);
      assertTrue(t.equals(tree));
    }
  }
}