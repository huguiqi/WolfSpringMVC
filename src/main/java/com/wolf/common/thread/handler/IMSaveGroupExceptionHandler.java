package com.wolf.common.thread.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by sam on 2020/4/27.
 */
@Slf4j
public class IMSaveGroupExceptionHandler implements Thread.UncaughtExceptionHandler {

    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("IMSaveGroupExceptionHandler.uncaughtException,thread:" + t.getName() + e.getMessage());
    }
}
