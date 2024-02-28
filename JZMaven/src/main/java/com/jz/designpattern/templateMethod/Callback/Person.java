package com.jz.designpattern.templateMethod.Callback;

public class Person implements ICallback {
    private Genius genius;

    Person(Genius genius) {
        this.genius = genius;
    }

    public void ask(String question) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //这里定义了一个匿名内部类。
                // 这里需要传入外部类，所以用Person.this;
                genius.executeMessage(question, Person.this);
            }
        });
        thread.start();

        System.out.println("去干其他事情了");
    }


    @Override
    public String callback(String result) {
        System.out.println("Genius solves this question and answer is " + result);
        return result;
    }
}
