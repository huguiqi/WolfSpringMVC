package com.wolf.common.thread.handler;


import com.wolf.common.thread.BaseBusinessRunnable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class BaseIMThreadExecutor {

    private static final Integer THREAD_RUN_TIME = 400;
    private static final Integer THREAD_WAIT_TIME = 2000;

    private static final Integer DEFAULT_CORE_SIZE = (((THREAD_WAIT_TIME + THREAD_RUN_TIME) / THREAD_RUN_TIME) + 1) * Runtime.getRuntime().availableProcessors();

    protected static IMThreadFactory threadFactory = new IMThreadFactory("IM线程工厂");

    //当请求任务队列达到设置的队列长度，且线程数达到最大线程时，执行给定的拒绝策略。
    private static final BlockingQueue QUEUE = new LinkedBlockingQueue<BaseBusinessRunnable>(DEFAULT_CORE_SIZE * 2 + 10);

    private static final IMRejectHandler REJECT_HANDLER = new IMRejectHandler();

    protected static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(DEFAULT_CORE_SIZE, DEFAULT_CORE_SIZE * 2, 60, TimeUnit.SECONDS, QUEUE, threadFactory, REJECT_HANDLER);
//    protected static final ExecutorService POOL_EXECUTOR = Executors.newCachedThreadPool(threadFactory);


    public abstract void execute();

    public abstract void execute(BaseBusinessRunnable task);
}
