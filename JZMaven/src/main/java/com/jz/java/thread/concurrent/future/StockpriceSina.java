package com.jz.java.thread.concurrent.future;

import java.util.function.Supplier;

class StockpriceSina implements Supplier<StockPrice> {

  @Override
  public StockPrice get() {
    try {
      String url = "http://hq.sinajs.cn/list=sh000001";
      System.out.println("GET: " + url);
      String result = DownloadUtil.download(url).split(",")[3];
      return new StockPrice(Float.parseFloat(result),"sina");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
