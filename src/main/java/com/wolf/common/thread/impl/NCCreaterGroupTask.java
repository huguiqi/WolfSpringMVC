package com.wolf.common.thread.impl;

import com.wolf.common.thread.AbstractThridCreateGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sam on 2020/4/21.
 */
@Slf4j
public class NCCreaterGroupTask extends AbstractThridCreateGroup {


    private String responseNCGroupId;


    public String getResponseNCGroupId() {
        return responseNCGroupId;
    }


    public void run() {

        System.out.println("NCCreaterGroupTask----run");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error("ssssss", e);
            Thread.currentThread().interrupt();
        }

        System.out.println("NCCreaterGroupTask----finish");
        if (this.getCountDownLatch() != null){
            this.getCountDownLatch().countDown();
        }

    }


    public boolean hasCreateGroup() {
        return true;
    }

    public void valid() {

    }
}
