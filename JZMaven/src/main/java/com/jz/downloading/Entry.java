package com.jz.downloading;

import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entry {

    private static final String ROOT_DOWNLOAD_PATH = "/Users/jzfeng/Documents/jiuzhangVideos/";

    private static final String DOWNLOAD_FILE_NAME = "urls.txt";

    private static final int NUM_OF_THREADS = 3;

    public static void main(String[] args) throws Exception {
        //读取文件中的url转化为Task;
        int num_of_lines = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(Entry.class.getClassLoader().getResourceAsStream("urls.txt")));
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

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new TaskHandler(taskQueue));
        }
        executorService.shutdown();
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


