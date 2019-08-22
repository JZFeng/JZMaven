package com.jz.java.thread.concurrent.future;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.*;

public class FutureSample {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    Future<String> future = executorService.submit(new DownloadTask("https://www.ebay.com"));
    String result = future.get();
    System.out.println(result);
    executorService.shutdown();
  }
}


class DownloadTask implements Callable<String> {
  private String url;

  DownloadTask(String url) {
    this.url = url;
  }

  @Override
  public String call() throws Exception {
    System.out.println("Start downloading...");
    StringBuilder sb = new StringBuilder();
    URLConnection conn = new URL(url).openConnection();
    conn.connect();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"))){
      String line = br.readLine();
      while(line != null && line.length() > 0) {
        sb.append(line + "\n");
        line = br.readLine();
      }
    }

    return sb.toString();
  }


}


