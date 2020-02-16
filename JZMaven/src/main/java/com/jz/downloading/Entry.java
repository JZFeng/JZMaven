package com.jz.downloading;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entry {

  private static final String ROOT_DOWNLOAD_PATH = "/Users/jzfeng/Documents/jiuzhangVideos/";

  private static final String DOWNLOAD_FILE_NAME = "urls3.txt";

  private static final int NUM_OF_THREADS = 10;

  public static void main(String[] args) throws Exception {

    File file = new File(ROOT_DOWNLOAD_PATH + DOWNLOAD_FILE_NAME);

    int num_of_lines = 0;

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = br.readLine();
    BlockingQueue<Task> taskQueue = new LinkedBlockingDeque<>();

    while (line != null && line.trim().length() > 0) {
      num_of_lines++;
      String[] strs = line.split(",");
      Task task = new Task("/Users/jzfeng/Documents/jiuzhangVideos/",
          strs[1] + ".mp4", strs[2]);
      taskQueue.put(task);
      line = br.readLine();
    }

    br.close();
    long start = System.currentTimeMillis();
    System.out.println("Start Time is : " + start);

    List<TaskHandler> taskHandlers = new ArrayList<>();
    for (int i = 0; i < NUM_OF_THREADS; i++) {
      taskHandlers.add(new TaskHandler(taskQueue));
    }
    for (TaskHandler taskHandler : taskHandlers) {
      taskHandler.start();
    }

    for (TaskHandler taskHandler : taskHandlers) {
      taskHandler.join();
    }

    long end = System.currentTimeMillis();

    System.out.println("Downloaded " + num_of_lines + " files, " + (end - start) / 1000 +
        " seconds used.");

  }


  public static void executeATask(Task task) throws Exception {
    String url = task.getUrl();
    String filename = task.getFilename();

    InputStream in = new URL(url).openStream();
    Files.copy(in, Paths.get(task.getFolder() +
        filename), StandardCopyOption.REPLACE_EXISTING);

//    Thread.sleep(2000);

    System.out.println(
        "*****Downloaded " + task.getFolder() + filename + ";" +
            System.currentTimeMillis());
  }

  private static String removeExcessiveSlash(String str) {
    if (str == null || str.length() == 0) {
      return str;
    }
    str = str.trim();

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < str.length() - 1; i++) {
      char c = str.charAt(i);
      if (c == '/' && str.charAt(i + 1) == '/') {
        continue;
      } else {
        sb.append(c);
      }
    }

    sb.append(str.charAt(str.length() - 1));

    return sb.toString().trim();

  }

  private static Set<String> readURLs(File file) throws IOException {
    LinkedHashSet<String> res = new LinkedHashSet<>();

    int num_of_lines = 0;

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = br.readLine();


    while (line != null && line.trim().length() > 0) {
      num_of_lines++;
      line.split("(https://.*.myqcloud.com/.*/v.f30.mp4)");
      Pattern pattern = Pattern.compile("(https://.*.myqcloud.com/.*/v.f30.mp4)");
      Matcher matcher = pattern.matcher(line);
      if (matcher.matches()) {
        res.add(matcher.group(1));
      }

      line = br.readLine();
    }

    return res;
  }

}


