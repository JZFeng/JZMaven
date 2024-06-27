package com.jz.designpattern.callback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Person implements ICallback {
    String name;
    public void ask(String question, Genius genius) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //这里定义了一个匿名内部类。
                // 这里需要传入外部类，所以用Person.this;
                genius.executeMessage(question, Person.this);
            }
        });
        thread.start();

        System.out.println(this.name + " 去干其他事情了");
    }


    @Override
    public String callback(String result) {
        System.out.println("天才 solves this question and answer is " + result);
        return result;
    }
}
