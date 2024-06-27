package com.jz.designpattern.callback;

public class Genius {

    public void executeMessage(String question, ICallback callback)  {
        System.out.println("天才Received question is " + question);
        System.out.println("天才Working on the question: " + question);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String result = "Answer is 2";
        callback.callback(result);
    }
}
