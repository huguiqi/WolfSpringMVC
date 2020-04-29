package com.wolf.common.thread.impl;

import com.wolf.common.thread.handler.IMSaveGroupExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sam on 2020/4/23.
 */
@Slf4j
public class IMGroupSaveDBTask  extends AbstractIMLocalDBGroupTask{

    public boolean hasCreateGroup() {
        return true;
    }

    public void valid() {

    }

    public void run() {

        System.out.println("IMGroupSaveDBTask----run");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error("ssssss", e);
            Thread.currentThread().interrupt();
        }

        System.out.println(11/0);

        System.out.println("IMGroupSaveDBTask----finish");
    }

    public Thread.UncaughtExceptionHandler getExceptionHandler() {
        return new IMSaveGroupExceptionHandler();
    }
}
