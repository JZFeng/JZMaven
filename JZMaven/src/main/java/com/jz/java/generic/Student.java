package com.jz.java.generic;

public class Student implements Comparable<Student> {
    private String name;
    private int score;

    Student(String name, int score) {
      this.name = name;
      this.score = score;
    }

    @Override
    public String toString() {
      return "Student : ( name : " + name + " ; score : " + score + " )";
    }

  @Override
  public int compareTo(Student o) {
    int result = 0 ;
    if(this.score > o.score) {
      result = 1;
    } else if (this.score < o.score) {
      result = -1;
    } else {
      result = this.name.compareTo(o.name);
    }

    return result;
  }
}
