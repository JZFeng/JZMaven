package com.jz.designpattern.templateMethod.Callback;

public class Li {

    /**
     * 相当于B类有参数为CallBack callBack的f()---->背景三
     *
     * @param callback
     * @param question 小王问的问题
     */

    public void executeMessage(ICallback callback, String question) {
        System.out.println("Wang's question is " + question);
        for (int i = 0; i < 1000; i++) {
            //Li is resolving
        }
        String result = "Answer is 2";
        callback.solve(result);
    }
}
