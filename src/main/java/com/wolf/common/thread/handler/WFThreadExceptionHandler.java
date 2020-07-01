package com.wolf.common.thread.handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WFThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("IMThread exception,threadName:{},threadId:{},error:{}",t.getName(),t.getId(),e);
    }
}
