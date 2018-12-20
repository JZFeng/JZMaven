package com.jz.designpattern.factory.abstractfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Factory {

  private static final Map<String, String> operators = new HashMap<String, String>();
  static {
    operators.put("+", "Add");
    operators.put("-", "Sub|Neg");
    operators.put("*", "Mul");
    operators.put("/", "Div");
    operators.put("%", "Mod");
  }


  public static Operation createOpeartion(String operator, double... nums) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    String name = "com.jz.designpattern.factory.abstractfactory.";
    if(operator.equals("-")) {
      name = name + ((nums.length == 1) ? "Neg" : "Sub");
    } else {
      name = name + operators.get(operator);
    }

    Class<?> clazz = Class.forName(name);
    Constructor<? extends Operation> constructor = (Constructor<? extends Operation>) clazz.getDeclaredConstructor(double.class, double.class);

    return (Operation) constructor.newInstance(nums[0], nums[1]);
  }

}
