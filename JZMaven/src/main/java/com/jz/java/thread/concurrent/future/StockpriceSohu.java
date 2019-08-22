package com.jz.java.thread.concurrent.future;

import java.util.function.Supplier;

public class StockpriceSohu implements Supplier<StockPrice> {
  @Override
  public StockPrice get() {
    String url = "http://api.money.126.net/data/feed/0000001,money.api";
    System.out.println("GET: " + url);
    try {
      String result = DownloadUtil.download(url);
      int priceIndex = result.indexOf("\"price\"");
      int start = result.indexOf(":", priceIndex);
      int end = result.indexOf(",", priceIndex);
      return new StockPrice(Float.parseFloat(result.substring(
          start + 1, end)), "sohu");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
