package com.wolf.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by sam on 2017/7/9.
 */
public class StopThread {
    //基本数据类型的本地变量，本身具有原子性
//    private static  boolean stopRequested;
    private static volatile   boolean stopRequested;
    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (!stopRequested)
                    i++;
            }
        });
        backgroundThread.start();
        //尽量用这个方法代替Thread.sleep()方法
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
