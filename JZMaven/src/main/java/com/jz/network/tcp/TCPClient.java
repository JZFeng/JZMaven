package com.jz.network.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        String cmd;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            cmd = bufferedReader.readLine();
        }

        try (Socket socket = new Socket(InetAddress.getLoopbackAddress(), 9090)) {
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    bw.write(cmd + "\n");
                    bw.flush();
                    String response = br.readLine();
                    System.out.println(response);
                }
            }
        }
    }
}
