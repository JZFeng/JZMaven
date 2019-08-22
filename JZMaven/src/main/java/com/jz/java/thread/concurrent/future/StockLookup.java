package com.jz.java.thread.concurrent.future;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.function.Supplier;

public class StockLookup implements Supplier<String> {
  private String name;

  StockLookup(String name) {
    this.name = name;
  }

  @Override
  public String get() {
    System.out.println("lookup: " + name);
    try {
      String url = "http://suggest3.sinajs.cn/suggest/type=11,12&key=" +
          URLEncoder.encode(name, "UTF-8");
      return DownloadUtil.download(url).split(",")[3];
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
