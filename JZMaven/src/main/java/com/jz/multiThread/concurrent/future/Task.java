package com.jz.multiThread.concurrent.future;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

//可以抽象出一个类，封装必须要做的网络连接和关闭的步骤，只暴露业务逻辑处理的扩展点；
public abstract class Task{
    String url;

    Task(String url){
        this.url = url;
    }

    public abstract String doHandle();

    //模版方法
    public String handle()  {
        try {
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");

            String res = doHandle();

            conn.disconnect();
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
