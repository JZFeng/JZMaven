package com.jz.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10086);
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line ;
            while ( true){
                line = br.readLine();
                if(line != null && line.length() > 0) {
                    System.out.println("server received : " + line);
                } else {
                    break;
                }
            }
            br.close();
            socket.close();
        }
    }
}
