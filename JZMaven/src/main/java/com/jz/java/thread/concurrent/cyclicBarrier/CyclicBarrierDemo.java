/**
 * @Author jzfeng
 * @Date 2/27/22-3:35 PM
 */

package com.jz.java.thread.concurrent.cyclicBarrier;

import java.util.concurrent.*;

public class CyclicBarrierDemo {
    public static void main(String[] args) throws Exception {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new TourGuideTask());
        ExecutorService executor = Executors.newFixedThreadPool(3);
        //登哥最大牌，到的最晚
        executor.execute(new TravelTask(cyclicBarrier, "哈登", 5));
        executor.execute(new TravelTask(cyclicBarrier, "保罗", 3));
        executor.execute(new TravelTask(cyclicBarrier, "戈登", 1));
        executor.shutdown();
    }
}

/**
 * 旅行线程
 * Created by jiapeng on 2018/1/7.
 */
class TravelTask implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private String name;
    private int arriveTime;//赶到的时间

    public TravelTask(CyclicBarrier cyclicBarrier, String name, int arriveTime) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
        this.arriveTime = arriveTime;
    }

    @Override
    public void run() {
        try {
            //模拟达到需要花的时间
            Thread.sleep(arriveTime * 1000);
            System.out.println(name + "到达集合点");
            //必须等到导游把护照都发下去。
            cyclicBarrier.await();
            System.out.println(name + "开始旅行啦～～");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}


/**
 * 导游线程，都到达目的地时，发放护照和签证
 * Created by jiapeng on 2018/1/7.
 */
class TourGuideTask implements Runnable {

    @Override
    public void run() {
        System.out.println("****导游分发护照签证****");
        try {
            //模拟发护照签证需要2秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}