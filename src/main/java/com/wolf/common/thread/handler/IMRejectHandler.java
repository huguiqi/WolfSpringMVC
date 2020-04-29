package com.wolf.common.thread.handler;

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
        System.out.println(executor.toString());
        log.info("rejectedExecution:{}", executor);
    }
}
