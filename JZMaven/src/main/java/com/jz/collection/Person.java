package com.jz.collection;

import java.util.Objects;

public class Person implements Comparable {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Name : " + name + " ; Age : " + age ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Person) {
            Person p = (Person) obj;
            return Objects.equals(this.name, p.name) && this.age == p.age;
        }

        return false;


    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.age);
    }

 /*   @Override
    //if name is equal, compared by age; if name and age is the same, they are the same then.
    public int compareTo(Object o) {
        int result = -1;

        if(this == o) {
            return 0;
        }

        if(o instanceof Person) {
            Person p = (Person) o;
            result = this.name.compareTo(p.name);
            if(result == 0) {
                result = Integer.compare(this.age, p.age);
            }
        } else {
            try {
                throw new exceptiondemo("Object is not a Person");
            } catch (exceptiondemo e) {
                e.printStackTrace();
            }
        }


        return result;
    }
    */


    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Person)) {
            try {
                throw new Exception("Object is not a Person instance");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Person p = (Person)o;
        int result = 0;
        if(this.age == p.age) {
            result = this.name.compareTo(p.name);
        } else {
            result = (this.age > p.age) ? 1 : -1;
        }

        return result;
    }
}
