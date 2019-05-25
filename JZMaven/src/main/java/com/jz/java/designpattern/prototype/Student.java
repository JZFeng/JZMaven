package com.jz.java.designpattern.prototype;

public class Student implements Cloneable {
  private String name;
  private int age;
  private boolean gender;

  Student(String name, int age, boolean gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  public Student clone() {
    Student student = null;
    try {
      student = (Student)super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    return student;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(boolean gender) {
    this.gender = gender;
  }

  @Override
  public String toString() {
    return "[Name: " + name  + " ;  Age " + age + " ; Gender : " + (gender ? "Male" : "Female" ) + "]";
  }
}
