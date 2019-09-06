package com.jz.java.network.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;

public class ClockClient {
  public static void main(String[] args) throws RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry(1099);
    Clock clock = (Clock)registry.lookup("Clock");
    LocalDateTime localDateTime = clock.currentTime();
    System.out.println("RMI Result : " + localDateTime);
  }
}
