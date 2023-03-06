package com.jz.designpattern.proxy.metriccollector;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理类
public class MetricsCollectorProxy {
  //为了扩展原始类的功能而引入的相关类，如果要记录时间，加入MetricsCollector；
  //如果还要加入Log日志功能，则加入Logger之类的东西，这样在InvocationHandler里使用Logger对象来给原始类添加日志功能。
  private MetricsCollector metricsCollector;

  public MetricsCollectorProxy() {
    this.metricsCollector = new MetricsCollector();
  }

  public Object createProxy(Object proxiedObject) {
    Class<?>[] interfaces = proxiedObject.getClass().getInterfaces();
    DynamicProxyHandler handler = new DynamicProxyHandler(proxiedObject);
    return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, handler);
  }

  //实际上DymamicProxyHandler才是真正意义上的代理类
  private class DynamicProxyHandler implements InvocationHandler {
    private Object proxiedObject; //被代理的对象

    public DynamicProxyHandler(Object proxiedObject) {
      this.proxiedObject = proxiedObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      long startTimestamp = System.currentTimeMillis(); //代理类附加的一些功能。

      //执行被代理类的方法,只需要传入被代理类，即可触发反射调用方法；args只是预定义了一个数组
      Object result = method.invoke(proxiedObject, args);

      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;

      String apiName = proxiedObject.getClass().getName() + ":" + method.getName();
      RequestInfo requestInfo = new RequestInfo(apiName, responseTime, startTimestamp);

      metricsCollector.recordRequest(requestInfo);

      return result;
    }
  }
}

