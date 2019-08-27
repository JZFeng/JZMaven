package com.jz.java.thread.concurrent.threadlocal;


class ThreadLocalSample {
  public static void main(String[] args) {
    Thread thread1 = new ProcessThread(new User("Bob", 150));
    Thread thread2 = new ProcessThread(new User("Alice" , 50));
    thread1.start();
    thread2.start();

  }
}

class User {
  private String name;
  private int level;

  User(String name , int level) {
    this.name = name;
    this.level = level;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }
}

class UserContext implements AutoCloseable {

  private static final ThreadLocal<User> context = new ThreadLocal<>();

  UserContext(User user) {
    context.set(user);
  }

  public static User getCurrentUser() {
    return context.get();
  }

  @Override
  public void close()  {
    context.remove();
  }
}


class Greeting {
  public void hello() {
    User user = UserContext.getCurrentUser();
    System.out.println("Hello " + user.getName() + "!");
  }
}

class Level {
  public static void checkLevel() {
    User user = UserContext.getCurrentUser();
    int level = user.getLevel();
    if(level > 100) {
      System.out.println(user.getName() + " is a VIP user");
    } else {
      System.out.println(user.getName() + " is a registered user");
    }
  }
}

class ProcessThread extends Thread {
  private User user;

  ProcessThread(User user) {
    this.user = user;
  }

  @Override
  public void run() {
    try (UserContext ctx = new UserContext(user)) {
      new Greeting().hello();
      Level.checkLevel();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}