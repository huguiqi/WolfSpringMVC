package com.wolf.common.thread.handler;

import com.wolf.common.thread.BaseBusinessRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sam on 2020/4/21.
 */
@Slf4j
public class IMThreadExcutor {


    private static final Integer DEFAULT_CORE_SIZE = 2;

    private Integer threadSize;

    private List<BaseBusinessRunnable> taskList = new LinkedList<BaseBusinessRunnable>();

    protected CountDownLatch countDownLatch;

    private ExecutorService executorService;

    private IMThreadFactory threadFactory;

    //设置缓存队列为5个任务(如遇线程不足，将会缓存到此队列)
    private static final BlockingQueue QUEUE = new LinkedBlockingQueue(5);

    private static final IMRejectHandler REJECT_HANDLER = new IMRejectHandler();

    private boolean hasWaitFinish = true;

    private Lock lock;

    private AtomicInteger taskSize = new AtomicInteger(0);


    public IMThreadExcutor(String name) {
        this.threadSize = DEFAULT_CORE_SIZE;
        this.lock = new ReentrantLock();
        this.threadFactory = new IMThreadFactory(name);
    }


    public IMThreadExcutor(String name,boolean hasWaitFinish) {
        this.threadSize = DEFAULT_CORE_SIZE;
        this.lock = new ReentrantLock();
        this.threadFactory = new IMThreadFactory(name);
        this.hasWaitFinish = hasWaitFinish;
    }


    public void addTask(BaseBusinessRunnable task) {
        try {
            lock.lock();
            this.taskList.add(task);
            if (task.getHasWaitRun()){
                this.taskSize.getAndIncrement();
            }
        } finally {
            lock.unlock();
        }
    }


    public void execute() {

        this.threadSize = this.taskSize.get();

        if (hasWaitFinish){
            if (this.countDownLatch == null) {
                this.countDownLatch = new CountDownLatch(threadSize);
            }
        }

        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(threadSize, threadSize * 2, 60, TimeUnit.SECONDS, QUEUE,this.threadFactory, REJECT_HANDLER);
//            this.executorService = Executors.newFixedThreadPool(this.threadSize,this.threadFactory);
        }

        for (final BaseBusinessRunnable task : this.taskList) {
            task.setCountDownLatch(this.countDownLatch);
            this.executorService.submit(task);
        }


    }


    public void countDownWait() {
        if (this.taskSize.get() == 0) {
            log.warn("无任务等待结果");
            return;
        }
        try {
            this.countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("等待线程执行完成出现异常中断");
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        if (this.taskSize.get() == 0)
            return;

        if (this.executorService.isTerminated())
            this.executorService.shutdown();
    }

}
