package com.jz.java.network.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
  public static void main(String[] args) throws IOException {

    try (Socket socket = new Socket(InetAddress.getLoopbackAddress(), 9090)){
      try ( BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8))) {
        try( BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8))) {
          bw.write("Time\n");
          bw.flush();
          String response = br.readLine();
          System.out.println(response);
        }
      }
    }
  }
}
