package com.wolf.common.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sam on 2020/4/21.
 */
public abstract class BaseBusinessRunnable  implements Runnable {


    private CountDownLatch countDownLatch;
    private String name;

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public String getName() {
        return name;
    }

    public BaseBusinessRunnable setName(String name) {
        this.name = name;
        return this;
    }
}
