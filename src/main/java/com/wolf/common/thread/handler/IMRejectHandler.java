package com.wolf.common.thread.handler;

import com.wolf.common.thread.BaseBusinessRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by sam on 2020/4/23.
 */
@Slf4j
public class IMRejectHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.info("rejectedExecution{}", executor);
        //Waiting for a second !!
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("sleep error:",e);
            Thread.currentThread().interrupt();
        }
        log.info("再次将未处理的线程加入线程池， name : {}", ((BaseBusinessRunnable) r).getName());
        if (!executor.isShutdown()) {
            executor.getQueue().poll();
            executor.execute(r);
        }
    }
}
