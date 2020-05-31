package com.jz.java.designpattern.Singleton;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class BackendServer {
  private static final int SERVER_COUNT = 3;
  private int serverNum;
  private String serverAddress;

  private static Map<Integer,BackendServer > concurrentHashMap = new ConcurrentHashMap<>();

  static {
    concurrentHashMap.put(1, new BackendServer(1, "192.134.22.138:8080"));
    concurrentHashMap.put(2, new BackendServer(2, "192.134.22.138:8080"));
    concurrentHashMap.put(3, new BackendServer(3, "192.134.22.138:8080"));
  }

  private BackendServer(int serverNum, String serverAddress) {
    this.serverNum = serverNum;
    this.serverAddress = serverAddress;
  }

  public static BackendServer getRandomInstance(){
    Random random  = new Random();

    return concurrentHashMap.get(random.nextInt(SERVER_COUNT + 1));
  }
}
