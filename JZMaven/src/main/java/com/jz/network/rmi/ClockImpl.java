package com.jz.network.rmi;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class ClockImpl implements Clock {
  @Override
  public LocalDateTime currentTime() throws RemoteException {
    return LocalDateTime.now();
  }
}
