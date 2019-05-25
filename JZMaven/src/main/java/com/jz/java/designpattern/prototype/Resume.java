package com.jz.java.designpattern.prototype;

public class Resume implements Cloneable {

  private String name;
  private int age;
  private WorkExperience workExperience;

  public Resume(String name) {
    this.name = name;
  }

  public Resume(String name, int age) {
    this.name = name;
    this.age = age;
  }

  //private constructor , will be invoked by clone();
  private Resume(String name, int age, WorkExperience workExperience)  {
    this.name = name;
    this.age = age;
    this.workExperience = workExperience.clone();
  }

  //override clone method, calling private constructor;
  public Resume clone()  {
    return new Resume(name, age, workExperience);
  }


  //java bean;
  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getAge() {
    return age;
  }

  public WorkExperience getWorkExperience() {
    return workExperience;
  }

  public void setWorkExperience(WorkExperience workExperience) {
    this.workExperience = workExperience;
  }

  @Override
  public String toString() {
    return "[Name : " + name + " ; Age : " + age + " ; " + " Work Experience : " + workExperience;
  }
}
