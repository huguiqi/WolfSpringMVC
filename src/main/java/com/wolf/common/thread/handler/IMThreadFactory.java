package com.wolf.common.thread.handler;

import com.wolf.common.thread.BaseBusinessRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sam on 2020/4/23.
 */
@Slf4j
public class IMThreadFactory implements ThreadFactory {


    private final String NAMEPREFIX;

    private AtomicInteger nextId = new AtomicInteger(1);


    public IMThreadFactory(String groupThreadName) {
        this.NAMEPREFIX = "From IMThreadFactory's" + groupThreadName + "-worker-" + nextId;
    }


    @Override
    public Thread newThread(Runnable task) {
        String taskName = "";
        String name = NAMEPREFIX + nextId.getAndIncrement() + taskName;

        Thread thread = new Thread(null, task, name, 0);
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler(businessTask.getExceptionHandler);
        log.info(thread.getName());
        System.out.println(thread.getName());
        return thread;
    }
}
