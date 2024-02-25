package com.jz.network.tcp;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.1.28", 10086);
        BufferedReader br = new BufferedReader(new FileReader("/Users/jzfeng/Desktop/test.txt"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String line;
        while (  true   ){
            line = br.readLine();
            if( line != null && line.length() > 0){
                System.out.println(line);
                bw.write(line);
                bw.newLine();
                bw.flush();
            } else {
                break;
            }
        }
        br.close();
        bw.close();
        socket.close();
    }
}
