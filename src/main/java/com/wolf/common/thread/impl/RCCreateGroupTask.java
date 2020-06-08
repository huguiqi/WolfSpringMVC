package com.wolf.common.thread.impl;

import com.wolf.common.thread.AbstractThridCreateGroup;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sam on 2020/4/21.
 */
@Slf4j
public class RCCreateGroupTask extends AbstractThridCreateGroup {


    private boolean hasResponseSuccess;

    private String responseRcGroupId;


    public String getResponseRcGroupId() {
        return responseRcGroupId;
    }




    @Override
    public void run() {

        log.info("RCCreaterGroupTask----run");
        System.out.println("RCCreaterGroupTask----run");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            log.error("sssss",e);
            Thread.currentThread().interrupt();
        }
        System.out.println("RCCreaterGroupTask----finish");
//        if (this.getCountDownLatch() != null){
//            this.getCountDownLatch().countDown();
//        }


    }



    public void valid() {
        log.info("校验融云新建群任务");
    }


    public boolean getHasResponseSuccess() {
        return hasResponseSuccess;
    }

    public boolean hasCreateGroup() {
        return true;
    }

}
