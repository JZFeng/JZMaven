package com.jz.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericExtends {
  public static void main(String[] args) {

    Pair<Integer> pair1 = new Pair<>(123,456);
    System.out.println(add(pair1));

    Pair<Number> pair2 = new Pair<>(1.23,4.56);
    System.out.println(add(pair2));

    Pair<Double> pair3 = new Pair<>(123.4,234.5);
    System.out.println(add(pair3));

    List<? extends Number> elist = null;
    elist = new ArrayList<Integer>();
//    elist.add(new Integer(1)); compile error;
  }

  //add方法，不能够确定实例化对象的具体类型，因此无法add具体对象至列表中，可能的实例化对象如下。
  //总结：extends 上界类型通配符add方法受限，但可以获取列表中的各种类型的数据，并赋值给父类型（extends Number）的引用。
  // 因此如果你想从一个数据类型里获取数据，使用 ? extends 通配符。限定通配符总是包括自己。


  public static int add(Pair<? extends Number> pair) {
    Number first = pair.getFirst();
    Number last = pair.getLast();

    return first.intValue() + last.intValue();
  }


}
