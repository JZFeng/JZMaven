package com.jz.annotation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationDemo {
  public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
    Person p1 = new Person("Xiao Ming", 25, "100123");
    Person p2 = new Person(null, 15, "8080");
    checkPerson(p1);
    checkPerson(p2);

    Method[] methods = Person.class.getMethods();
    for(Method method : methods) {
      if ( method.getReturnType() == void.class) {

      }
    }


 /*   Method[] methods = Person.class.getMethods();
    for(Method method : methods) {
      Annotation[][] annotations = method.getParameterAnnotations();
      for(int i = 0 ; i  < annotations.length ; i++) {
        for(int j = 0 ; j < annotations[i].length; j++) {
          System.out.println(annotations[i][j].toString());
        }
      }
    }
*/


  }


  public static void checkMethod(Method getter, Person person) throws InvocationTargetException, IllegalAccessException {
    Object value = getter.invoke(person);
    if (getter.isAnnotationPresent(NotNull.class) ) {
      if (value == null) {
        System.out.println("Return value of " + getter.getName() +  " cannot be null");
      }
    }

    if ( getter.isAnnotationPresent(Range.class) && value != null ) {
      Range range = getter.getAnnotation(Range.class);

      int tmp = 0;
      if(value instanceof Integer) {
        tmp = (Integer)value;
      } else if(value instanceof String) {
        tmp = ((String) value).length();
      }

      if( tmp > range.max() || tmp < range.min()  ) {
        System.out.println("Return value " + value + " out of range." );
      }
    }

    if ( getter.isAnnotationPresent(Zipcode.class) && value != null) {
      String zipcode = (String)value;
      if(!isValidZipcode(zipcode)) {
        System.out.println("Invalid zip code : " + zipcode);
      }
    }
  }


  public static void checkPerson(Person person) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
    /*Class clazz = Person.class;
    Field[] fields = clazz.getDeclaredFields();
    for(Field field : fields) {
      checkField(field, person);
    }*/

    //check java bean getter return value;
    BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
    PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
    for(PropertyDescriptor pd : pds ) {
      Method read = pd.getReadMethod();
      Object value = read.invoke(person); //invoke getting, get the return value;
      checkMethod(read, person);
    }
  }

  public static void checkField(Field field, Person person) throws IllegalAccessException {
    field.setAccessible(true);
    Object value = field.get(person);

    if (field.isAnnotationPresent(Range.class)) {
      Range range = field.getAnnotation(Range.class) ;
      int age = (int) value;
      if(age < range.min()) {
        System.out.println("age should not be smaller than " + range.min());
      }
      if(age > range.max()) {
        System.out.println("age should not be larger than " + range.max());
      }
    }

    if(field.isAnnotationPresent(NotNull.class)) {
      if ( value == null) {
        System.out.println(field.getName() + " cannot be null");
      }

    }
  }


  public static boolean isValidZipcode(String str) {
    if(str == null ) {
      return false;
    }

    str = str.trim();
    if(str.length() != 6) {
      return false;
    }

    for(int i = 0 ; i < str.length(); i++) {
      char c = str.charAt(i);
      if ( c < '0' || c > '9') {
        return false;
      }
    }

    return true;
  }

}
