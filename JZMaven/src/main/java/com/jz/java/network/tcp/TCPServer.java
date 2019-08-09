package com.jz.java.network.tcp;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TCPServer {
  public static void main(String[] args) throws IOException {
    ServerSocket ss = new ServerSocket(9090);
    System.out.println("Server is ready.");
    Socket socket = ss.accept();

      try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
          String request = br.readLine();
          if(request.equalsIgnoreCase("Time")) {
            bw.write(LocalDateTime.now().toString());
            bw.flush();
          } else {
            bw.write("Invalid Request");
            bw.flush();
          }
        }
      }
  }

}
