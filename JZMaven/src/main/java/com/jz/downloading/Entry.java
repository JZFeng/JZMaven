package com.jz.downloading;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Entry {

  private static final String file = "/Users/jzfeng/Documents/download.txt";

  public static void main(String[] args) throws IOException, InterruptedException {

    boolean isValid = validateURLLinks(new File("/Users/jzfeng/Documents/download.txt"));
    if (!isValid) {
      throw new RuntimeException("Duplicate URLs in the download.txt file.");
    }

    BufferedReader br = new BufferedReader(new FileReader(new File(Entry.file)));
    String line = br.readLine();

    while (line != null && line.trim().length() > 0) {
      String[] strs = line.split(",");
      String filename = strs[0] + "_" + strs[1] + ".mp4";
      String url = "https://www.feiyangedu.com" + strs[2];

      InputStream in = new URL(url).openStream();
      Files.copy(in, Paths.get("/Users/jzfeng/Documents/Java教程/" +
          filename), StandardCopyOption.REPLACE_EXISTING);
      Thread.sleep(60000);
      line = br.readLine();
    }

    br.close();
  }


  private static boolean validateURLLinks(File file) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    Set<String> set = new TreeSet<>();
    String line = br.readLine();
    while (line != null && line.trim().length() > 0) {
      String url = line.split(",")[2];
      if (!set.add(url)) {
        System.out.println("Duplicate URL is : " + url);
        return false;
      }
      line = br.readLine();
    }

    br.close();

    return true;
  }

}


