package com.jz.java.thread.concurrent.future;

import com.jz.java.thread.concurrent.future.DownloadUtil;

import java.util.function.Supplier;

class StockpriceLookupSupplier implements Supplier<Float> {

  @Override
  public Float get() {
    try {
      String result = DownloadUtil.download("http://hq.sinajs.cn/list=sh000001").split(",")[3];
      return Float.parseFloat(result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
