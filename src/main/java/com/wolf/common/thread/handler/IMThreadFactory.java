package com.wolf.common.thread.handler;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sam on 2020/4/23.
 */
@Slf4j
public class IMThreadFactory implements ThreadFactory {


    private String name;

    private AtomicInteger nextId = new AtomicInteger(1);


    public IMThreadFactory(String groupThreadName) {
        this.name = "From IMThreadFactory's" + groupThreadName + "-worker-" + nextId;
    }

    public void setName(String groupThreadName){
        this.name = "From IMThreadFactory's" + groupThreadName + "-worker-" + nextId;
    }


    @Override
    public Thread newThread(Runnable task) {

        String name = this.name + nextId.getAndIncrement();

        Thread thread = new Thread(null, task, name, 0);
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler(new IMThreadExceptionHandler());
        log.info(thread.getName());
        return thread;
    }
}
