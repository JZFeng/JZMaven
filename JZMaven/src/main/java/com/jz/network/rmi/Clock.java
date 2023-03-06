package com.jz.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface Clock extends Remote{ // must extend Remote interface;
  LocalDateTime currentTime() throws RemoteException; // must throw RemoteException;
}
