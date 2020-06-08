package com.wolf.common.thread.handler;

/**
 * Created by sam on 2020/4/23.
 */
public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("uncaughtException" + t.getName());
    }
}
