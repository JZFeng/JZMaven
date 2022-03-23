package com.jz.java.multiThread.concurrent.future;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUtil {

    public static String download(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setDoOutput(false);
        conn.setRequestMethod("GET");
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null && line.length() > 0) {
            sb.append(line);
            line = br.readLine();
        }

        conn.disconnect();
        br.close();
        return sb.toString();
    }
}
