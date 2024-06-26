package com.jz.designpattern.Interpreter;


import java.util.Deque;
import java.util.LinkedList;


interface Expression {
  long interpret();
}

class NumberExpression implements Expression {
  private long number;

  public NumberExpression(long number) {
    this.number = number;
  }

  public NumberExpression(String number) {
    this.number = Long.parseLong(number);
  }

  @Override
  public long interpret() {
    return this.number;
  }
}

class AdditionExpression implements Expression {
  private Expression exp1;

  private Expression exp2;

  public AdditionExpression(Expression exp1, Expression exp2) {
    this.exp1 = exp1;
    this.exp2 = exp2;
  }

  @Override
  public long interpret() {
    return exp1.interpret() + exp2.interpret();
  }
}

class SubstractionExpression implements Expression {
  private Expression exp1;

  private Expression exp2;

  public SubstractionExpression(Expression exp1, Expression exp2) {
    this.exp1 = exp1;
    this.exp2 = exp2;
  }

  @Override
  public long interpret() {
    return exp1.interpret() - exp2.interpret();
  }
}

class MultiplicationExpression implements Expression {
  private Expression exp1;
  private Expression exp2;

  public MultiplicationExpression(Expression exp1, Expression exp2) {
    this.exp1 = exp1;
    this.exp2 = exp2;
  }

  @Override
  public long interpret() {
    return exp1.interpret() * exp2.interpret();
  }
}

class DivisionExpression implements Expression {
  private Expression exp1;

  private Expression exp2;

  public DivisionExpression(Expression exp1, Expression exp2) {
    this.exp1 = exp1;
    this.exp2 = exp2;
  }

  @Override
  public long interpret() {
    return exp1.interpret() / exp2.interpret();
  }
}

class ExpressionInterpreter {
  private Deque<Expression> numbers = new LinkedList<>();

  private long interpret(String expression) {
    String[] elements = expression.split(" ");
    int length = elements.length;
    for (int i = 0; i < (length + 1) / 2; ++i) {
      numbers.addLast(new NumberExpression(elements[i]));
    }

    for (int i = (length + 1) / 2; i < length; ++i) {
      String operator = elements[i];
      boolean isValid = "+".equals(operator) || "-".equals(operator)
          || "*".equals(operator) || "/".equals(operator);
      if (!isValid) {
        throw new RuntimeException("Expression is invalid: " + expression);
      }

      Expression exp1 = numbers.pollFirst();
      Expression exp2 = numbers.pollFirst();
      Expression combinedExp = null;
      if (operator.equals("+")) {
        combinedExp = new AdditionExpression(exp1, exp2);
      } else if (operator.equals("-")) {
        combinedExp = new SubstractionExpression(exp1, exp2);
      } else if (operator.equals("*")) {
        combinedExp = new MultiplicationExpression(exp1, exp2);
      } else if (operator.equals("/")) {
        combinedExp = new DivisionExpression(exp1, exp2);
      }
      long result = combinedExp.interpret();
      numbers.addFirst(new NumberExpression(result));
    }

    if (numbers.size() != 1) {
      throw new RuntimeException("Expression is invalid: " + expression);
    }

    return numbers.pop().interpret();
  }


  public static void main(String[] args) {
    String expression = "8 3 2 4 - + *";
    ExpressionInterpreter interpreter = new ExpressionInterpreter();

    System.out.println(interpreter.interpret(expression));

  }
}
