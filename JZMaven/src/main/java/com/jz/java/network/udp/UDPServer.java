package com.jz.java.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class UDPServer {
  public static void main(String[] args) throws IOException {
    DatagramSocket sock = new DatagramSocket(9090);
    System.out.println("Server is ready!");

    while(true) {
      try {
        byte[] buff = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        sock.receive(packet);
        String  s = new String(packet.getData(), StandardCharsets.UTF_8).trim();
        System.out.println("Received request is " + s + " from " + packet.getSocketAddress());
        packet.setData(LocalDateTime.now().toString().getBytes(StandardCharsets.UTF_8));
        sock.send(packet);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
