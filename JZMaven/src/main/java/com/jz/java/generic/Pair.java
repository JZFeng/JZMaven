package com.jz.java.generic;

public class Pair<T> {
  private T first;
  private T last;

  Pair(T first, T last) {
    this.first = first;
    this.last = last;
  }

  public T getFirst() {
    return first;
  }

  public void setFirst(T first) {
    this.first = first;
  }

  public T getLast() {
    return last;
  }

  public void setLast(T last) {
    this.last = last;
  }

  @Override
  public String toString() {
    return "Pair(" + first + "," + last + ")";
  }


  public static <K extends Number> Pair<K> create(K first, K last) {
    return new Pair<K>(first, last);
  }

 /* class name clash error because of Type Erasure;
  public boolean equals(T o) {

  }*/
}
