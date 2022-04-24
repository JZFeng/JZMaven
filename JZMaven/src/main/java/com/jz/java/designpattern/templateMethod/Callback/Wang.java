package com.jz.java.designpattern.templateMethod.Callback;

/**
 * 这个是小王
 * 实现了一个回调接口CallBack，相当于----->背景一
 */

public class Wang implements ICallback {
    /**
     * 小李对象的引用
     * 相当于----->背景二
     */

    private Li li;

    Wang(Li li) {
        this.li = li;
    }

    public void askAQuestion(String question) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 小王调用小李中的方法，在这里注册回调接口
                 * 这就相当于A类调用B的方法C
                 */
                //注意这里必须使用Wang.this,因为这里定义了一个匿名内部类。
                // 但是这里需要传入外部类（也就是Wang的实例），所以用Wang.this;
                li.executeMessage(Wang.this, question);
            }
        });
        thread.start();

        System.out.println("Wang goes out for shopping");
    }


    @Override
    public String solve(String result) {
        System.out.println("Li solves this question and answer is " + result);
        return result;
    }
}
