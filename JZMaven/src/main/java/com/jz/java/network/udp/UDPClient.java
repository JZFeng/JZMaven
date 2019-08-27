package com.jz.java.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient {
  public static void main(String[] args) throws IOException, InterruptedException {
    try (DatagramSocket socket = new DatagramSocket()) {
      socket.connect(InetAddress.getLoopbackAddress(),9090);

      byte[] request = "time".getBytes(StandardCharsets.UTF_8);
      DatagramPacket packet = new DatagramPacket(request, request.length);
      socket.send(packet);
      Thread.sleep(1000);

      byte[] response = new byte[1024];
      DatagramPacket responsePacket = new DatagramPacket(response, response.length);
      socket.receive(responsePacket);
      System.out.println("Response is " + new String(responsePacket.getData(), StandardCharsets.UTF_8).trim());
    }
  }
}
