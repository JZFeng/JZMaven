package com.jz.designpattern.proxy.metriccollector;

public class RequestInfo {

  private String apiName;
  private long responseTime;
  private long startTimestamp;

  RequestInfo(String apiName, long responseTime, long startTimestamp) {
    this.apiName = apiName;
    this.responseTime = responseTime;
    this.startTimestamp = startTimestamp;
  }
}
