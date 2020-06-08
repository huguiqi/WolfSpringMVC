package com.wolf.common.thread.handler;

import com.wolf.common.thread.BaseBusinessRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sam on 2020/4/21.
 */
@Slf4j
public class IMCountDownThreadExcutor extends BaseIMThreadExecutor{

    private Integer threadSize;

    private List<BaseBusinessRunnable> taskList = new LinkedList<BaseBusinessRunnable>();

    private volatile CountDownLatch countDownLatch;

    private Lock lock;

    private boolean hasWaitFinish = true;

    private AtomicInteger taskSize = new AtomicInteger(0);


    public IMCountDownThreadExcutor(String name) {
        this.lock = new ReentrantLock();
        threadFactory.setName(name);
        POOL_EXECUTOR.allowCoreThreadTimeOut(true);
    }

    public IMCountDownThreadExcutor(String name, boolean hasWaitFinish) {
        this.lock = new ReentrantLock();
        threadFactory.setName(name);
        this.hasWaitFinish = hasWaitFinish;
        POOL_EXECUTOR.allowCoreThreadTimeOut(true);
    }


    public void addTask(BaseBusinessRunnable task) {
        try {
            lock.lock();
            if (task == null) {
                log.warn("不能添加空的task!!");
                return;
            }

            this.taskList.add(task);
            this.taskSize.getAndIncrement();
        } finally {
            lock.unlock();
        }
    }


    public void execute() {

        if (lock.tryLock()) {
            this.threadSize = this.taskSize.get();
            if (this.threadSize == 0) {
                return;
            }

            if (hasWaitFinish) {
                if (this.countDownLatch == null) {
                    this.countDownLatch = new CountDownLatch(threadSize);
                }
            }


            for (BaseBusinessRunnable task : this.taskList) {
                task.setCountDownLatch(this.countDownLatch);
                this.POOL_EXECUTOR.submit(task);
            }
            log.info("{}.execute, taskList:{}", this.getClass().toString(), taskList);
//            clearTask();
            lock.unlock();
        }


    }


    public void countDownWait() {
        if (!this.hasWaitFinish) {
            return;
        }
        if (this.taskSize.get() == 0) {
            log.warn("无任务需要等待.....");
            return;
        }
        try {
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("等待线程执行完成出现异常中断");
            Thread.currentThread().interrupt();
        }
    }



    public Integer getTaskSize() {
        if (this.taskSize == null)
            return null;

        return this.taskSize.get();
    }

    @Override
    public void execute(BaseBusinessRunnable task) {
        log.warn("此类无此实现,请使用其它方法!!");
    }
}
