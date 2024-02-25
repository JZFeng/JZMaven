package com.jz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

class Solution {
    public static void main(String[] args) throws IOException {
        InetAddress byName = InetAddress.getByName("jz-ubuntu");
        byName = InetAddress.getByName("192.168.1.101");
        System.out.println(byName.getHostName());
        System.out.println(byName.getHostAddress());
        System.out.println(byName);
    }
}


