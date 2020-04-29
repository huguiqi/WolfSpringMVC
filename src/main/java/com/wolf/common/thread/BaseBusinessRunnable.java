package com.wolf.common.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sam on 2020/4/21.
 */
public abstract class BaseBusinessRunnable  implements Runnable {

    private CountDownLatch countDownLatch;

    private boolean hasWaitRun;

    public abstract Thread.UncaughtExceptionHandler getExceptionHandler();

    public boolean getHasWaitRun() {
        return hasWaitRun;
    }

    public void setHasWaitRun(boolean hasWaitRun) {
        this.hasWaitRun = hasWaitRun;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        if (hasWaitRun){
            this.countDownLatch = countDownLatch;
        }
    }

}
