package com.jz.network.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClockServer {
  public static void main(String[] args) throws RemoteException, AlreadyBoundException {
    Clock clockImpl = new ClockImpl(); // creat an object instance;
    Clock stub = (Clock)UnicastRemoteObject.exportObject(clockImpl, 1099); // create server stub
    LocateRegistry.createRegistry(1099);
    Registry registry = LocateRegistry.getRegistry();
    registry.bind("Clock", stub);
    System.out.println("RMI Server is ready");
  }
}
