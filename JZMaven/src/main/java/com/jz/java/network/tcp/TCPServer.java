package com.jz.java.network.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
  public static void main(String[] args) throws IOException {
    ServerSocket ss = new ServerSocket(9090);
    System.out.println("Server is ready!");
    ExecutorService pool = Executors.newFixedThreadPool(50);

    while (true) {
      try {
        Socket socket = ss.accept();
        TimeHandler timeHandler = new TimeHandler(socket);
        pool.submit(timeHandler);
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }
  }
}


class TimeHandler implements Runnable {
  private Socket socket;

  TimeHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
        String cmd = br.readLine();
        String response ;
        if (cmd.equalsIgnoreCase("time")) {
          response = LocalDateTime.now().toString();
        } else if (cmd.equalsIgnoreCase("q")) {
          response = "Bye!";
        } else {
          response = "Invalid request.";
        }
        bw.write(response);
        bw.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}
