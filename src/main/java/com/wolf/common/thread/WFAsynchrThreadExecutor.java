package com.wolf.common.thread;

import com.wolf.common.thread.handler.BaseWFThreadExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@Slf4j
public class WFAsynchrThreadExecutor extends BaseWFThreadExecutor {

    private WFAsynchrThreadExecutor() {
        super();
        POOL_EXECUTOR.allowCoreThreadTimeOut(true);
        //初如化所有核心线程
        POOL_EXECUTOR.prestartAllCoreThreads();
        threadFactory.setName("线程工厂初始化线程");
    }

    private static class SingleHolder {
        private static WFAsynchrThreadExecutor instance = new WFAsynchrThreadExecutor();
    }

    public static WFAsynchrThreadExecutor getInstance() {
        return SingleHolder.instance;
    }

    @Override
    public void execute() {
        log.warn("此类无此实现,请使用其它方法!!");
    }

    public void execute(BaseBusinessRunnable task) {
        if (task == null) {
            log.warn("不能执行空的task!!");
            return;
        }
        threadFactory.setName(task.getName());
        POOL_EXECUTOR.execute(task);
    }


    public ExecutorService getPool(){
        return POOL_EXECUTOR;
    }

}
