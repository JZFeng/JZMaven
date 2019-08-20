package com.jz.downloading;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Entry {

  private static final String ROOT_DOWNLOAD_PATH = "/Users/jzfeng/Documents/Java教程/";

  private static final String ROOT_URL_PREFIX = "https://www.feiyangedu.com";

  private static final String DOWNLOAD_FILE_NAME = "Java多线程编程.txt";

  private static final int NUM_OF_THREADS = 5;

  public static void main(String[] args) throws Exception {

    File file = new File(ROOT_DOWNLOAD_PATH + DOWNLOAD_FILE_NAME);

    boolean isValid = validateURLLinks(file);
    if (!isValid) {
      throw new RuntimeException(
          "Duplicate URLs in " + ROOT_DOWNLOAD_PATH + DOWNLOAD_FILE_NAME);
    }

    int num_of_lines = 0;

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = br.readLine();
    BlockingQueue<Task> taskQueue = new LinkedBlockingDeque<>();

    while (line != null && line.trim().length() > 0) {
      num_of_lines++;
      String[] strs = line.split(",");
      Task task = new Task(strs[0], strs[1] + ".mp4", strs[2]);
      taskQueue.put(task);
      line = br.readLine();
    }

    br.close();
    long start = System.currentTimeMillis();
    System.out.println("Start Time is : " + start);

    List<TaskHandler> taskHandlers = new ArrayList<>();
    for(int i = 0 ; i < NUM_OF_THREADS; i++) {
      taskHandlers.add(new TaskHandler(taskQueue));
    }
    for(TaskHandler taskHandler : taskHandlers) {
      taskHandler.start();
    }

    for(TaskHandler taskHandler : taskHandlers) {
      taskHandler.join();
    }

    long end = System.currentTimeMillis();

    System.out.println("Downloaded " + num_of_lines + " files, " + (end - start) / 1000 +
        " seconds used.");

  }


  private static boolean validateURLLinks(File file) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    Set<String> set = new TreeSet<>();
    String line = br.readLine();
    while (line != null && line.trim().length() > 0) {
      String[] strs = line.split(",");
      if (strs.length != 3) {
        throw new RuntimeException(line + " has more than three parameters.");
      }

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

  public static void executeATask(Task task) throws Exception {
    String url = task.getUrl();
    String folder = ROOT_DOWNLOAD_PATH +
        DOWNLOAD_FILE_NAME.substring(0, DOWNLOAD_FILE_NAME.indexOf(".")) + "/" +
        task.getFolder();

    createFolderByPath(folder);

    String filename = task.getFilename();

    InputStream in = new URL(ROOT_URL_PREFIX + url).openStream();
    Files.copy(in, Paths.get(folder + "/" +
        filename), StandardCopyOption.REPLACE_EXISTING);

//    Thread.sleep(2000);

    System.out.println(
        "*****Downloaded " + task.getFolder() + "/" + filename + ";" +System.currentTimeMillis());
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

  private static void createFolderByPath(String path) {
    if (path == null || path.length() == 0) {
      return;
    }

    if (path.startsWith(ROOT_DOWNLOAD_PATH)) {
      path = path.substring(ROOT_DOWNLOAD_PATH.length()).trim();
    }

    String[] strs = path.split("/");
    StringBuilder sb = new StringBuilder(ROOT_DOWNLOAD_PATH.substring(0,
        ROOT_DOWNLOAD_PATH.length() - 1));
    for (String str : strs) {
      sb.append("/" + str);
      File f = new File(sb.toString());
      if (!f.exists()) {
        f.mkdir();
      }
    }
  }

}


