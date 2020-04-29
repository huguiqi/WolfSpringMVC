package com.wolf.thread;


/**
 * Created by sam on 2020/4/20.
 */


import com.wolf.common.thread.handler.IMThreadExcutor;
import com.wolf.common.thread.impl.IMGroupSaveDBTask;
import com.wolf.common.thread.impl.NCCreaterGroupTask;
import com.wolf.common.thread.impl.RCCreateGroupTask;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//@RunWith(SpringRunner.class)
//@Ignore
public class ThreadPoolTest {


    private IMThreadExcutor excutor = new IMThreadExcutor("新建群聊");

    @Test
    public void postGroupTest() throws InterruptedException {

        long start = System.currentTimeMillis();

        //初始化
        RCCreateGroupTask rcCreateGroupTask = new RCCreateGroupTask();
        rcCreateGroupTask.setHasWaitRun(true);
        NCCreaterGroupTask ncCreateGroupTask = new NCCreaterGroupTask();
        ncCreateGroupTask.setHasWaitRun(true);

        if (rcCreateGroupTask.hasCreateGroup()) {
            rcCreateGroupTask.valid();
            excutor.addTask(rcCreateGroupTask);
        }

        if (ncCreateGroupTask.hasCreateGroup()) {
            ncCreateGroupTask.valid();
            excutor.addTask(ncCreateGroupTask);
        }


        IMGroupSaveDBTask groupSaveDBTask = new IMGroupSaveDBTask();
        groupSaveDBTask.setHasWaitRun(false);
        if (groupSaveDBTask.hasCreateGroup()){
            groupSaveDBTask.valid();
            excutor.addTask(groupSaveDBTask);
        }

        excutor.execute();

        excutor.countDownWait();

        excutor.shutdown();


        long end = System.currentTimeMillis();

        System.out.println("需要：" + (end - start) + "ms");

        Thread.sleep(1000);


    }


    @Test
    public void testPostGroup2() {

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CountDownLatch countDownLatch = new CountDownLatch(2);

        RCCreateGroupTask rcCreateGroupTask = new RCCreateGroupTask();
        rcCreateGroupTask.setCountDownLatch(countDownLatch);

        NCCreaterGroupTask ncCreateGroupTask = new NCCreaterGroupTask();
        ncCreateGroupTask.setCountDownLatch(countDownLatch);


        executorService.execute(rcCreateGroupTask);
        executorService.execute(ncCreateGroupTask);

        try {

            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        long end = System.currentTimeMillis();

        System.out.println("需要：" + (end - start) + "ms");
        Assert.assertTrue(true);

//        executorService.shutdown();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


	
	
