package com.jz.designpattern.templateMethod.Callback;

public class Genius {

    public void executeMessage(String question, ICallback callback)  {
        System.out.println("Received question is " + question);
        System.out.println("Working on the question: " + question);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String result = "Answer is 2";
        callback.callback(result);
    }
}
