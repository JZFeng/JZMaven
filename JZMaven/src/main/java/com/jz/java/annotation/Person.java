package com.jz.java.annotation;

public class Person {
  @NotNull
  private String name;

  @Range(max = 20)
  private int age;

  private String zipcode;

  Person(@NotNull String name, @NotNull @Range(max = 20) int age) {
    this.name = name;
    this.age = age;
  }

  Person(String name, int age, String zipcode) {
    this.name = name;
    this.age = age;
    this.zipcode = zipcode;
  }

  @Range(max = 20)
  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @NotNull
  @Range(min = 2 , max = 5)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Zipcode
  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  @Override
  public String toString() {
    return "Name : " + name + " ; Age : " + age;
  }

}
