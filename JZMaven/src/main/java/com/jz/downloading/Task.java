package com.jz.downloading;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Task {
    private String folder;
    private String filename;
    private String url;

    Task(String folder, String filename, String url) {
        this.filename = filename;
        this.folder = folder;
        this.url = url;
    }

    public String getFolder() {
        return folder;
    }

    public String getFilename() {
        return filename;
    }

    public String getUrl() {
        return url;
    }

    public void execute() {

        InputStream in = null;
        try {
            in = new URL(url).openStream();
            Files.copy(in, Paths.get(folder +
                    filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(
                    "*****Downloaded " + folder + filename + ";" +
                            System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
