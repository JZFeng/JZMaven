package com.jz.designpattern.prototype;

public class Entry {
  public static void main(String[] args)  {
    Resume resume1 = new Resume("JZ", 20);
    resume1.setWorkExperience(new WorkExperience("2015/5", "2011/9", "Telenav"));

    Resume resume2 = resume1.clone();
    resume2.setName("Lin");
    resume2.setAge(26);
    resume2.setWorkExperience(new WorkExperience("2011/9", "2016/3", "eBay"));


    System.out.println(resume1);
    System.out.println(resume2);

    /*Student student1 = new Student("Jason", 4, true);
    Student student2 = student1.clone();
    student2.setName("JZ");student2.setAge(36);

    System.out.println(student1);
    System.out.println(student2);*/
  }
}
