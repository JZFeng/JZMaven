package com.jz.downloading;

public class Task  {
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

}
