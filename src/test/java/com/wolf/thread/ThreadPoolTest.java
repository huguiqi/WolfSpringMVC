package com.wolf.thread;


/**
 * Created by sam on 2020/4/20.
 */


import com.wolf.common.thread.BaseBusinessRunnable;
import com.wolf.common.thread.IMAsynchrThreadExecutor;
import com.wolf.common.thread.impl.IMGroupSaveDBTask;
import com.wolf.common.thread.impl.NCCreaterGroupTask;
import com.wolf.common.thread.impl.RCCreateGroupTask;
import com.wolf.common.utils.PageByPage;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


//@RunWith(SpringRunner.class)
//@Ignore
public class ThreadPoolTest {


    @Test
    public void postGroupTest() throws InterruptedException {

        long start = System.currentTimeMillis();
        IMAsynchrThreadExecutor executor = IMAsynchrThreadExecutor.getInstance();
        //初始化
        RCCreateGroupTask rcCreateGroupTask = new RCCreateGroupTask();
//        rcCreateGroupTask.setHasWaitRun(true);
        NCCreaterGroupTask ncCreateGroupTask = new NCCreaterGroupTask();
//        ncCreateGroupTask.setHasWaitRun(true);

        if (rcCreateGroupTask.hasCreateGroup()) {
            rcCreateGroupTask.valid();
            executor.execute(rcCreateGroupTask);
        }

        if (ncCreateGroupTask.hasCreateGroup()) {
            ncCreateGroupTask.valid();
            executor.execute(ncCreateGroupTask);
        }


        IMGroupSaveDBTask groupSaveDBTask = new IMGroupSaveDBTask();
//        groupSaveDBTask.setHasWaitRun(false);
        if (groupSaveDBTask.hasCreateGroup()) {
            groupSaveDBTask.valid();
            executor.execute(groupSaveDBTask);
        }


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


    @Test
    public void testExecutorService() {

        IMAsynchrThreadExecutor executor = IMAsynchrThreadExecutor.getInstance();

        List<String> employeeIds = new ArrayList<>();
        int i = 0;
        while (i < 1822) {
            employeeIds.add("employee_" + i);
            i++;
        }
        long start = System.currentTimeMillis();
        PageByPage.execute(employeeIds, 100, currentUserIds -> {
            executor.execute(new BaseBusinessRunnable() {
                @Override
                public void run() {
                    System.out.println("执行任务:" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        });


        executor.getPool().shutdown();

        while (true) {
            IMAsynchrThreadExecutor executor1 = IMAsynchrThreadExecutor.getInstance();
            if (executor1.getPool().isTerminated()) {
                long end = System.currentTimeMillis();
                System.out.println("需要时间:" + (end - start));
                break;
            }
        }
    }

    @Test
    public void testForkJoin() {

        ForkJoinPool joinPool = ForkJoinPool.commonPool();

        List<String> employeeIds = new ArrayList<>();
        int i = 0;
        while (i < 1822) {
            employeeIds.add("employee_" + i);
            i++;
        }
        long start = System.currentTimeMillis();

        PageByPage.execute(employeeIds, 100, currentUserIds -> {
            joinPool.execute(new BaseBusinessRunnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "jokinPool---run");
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

        });

        long end = System.currentTimeMillis();
        System.out.println("需要时间:" + (end - start));

        System.out.println("待执行任务:" + joinPool.getQueuedSubmissionCount());
        while (joinPool.getQueuedSubmissionCount() > 0) {
            System.out.println("未完成");
        }

        joinPool.shutdown();
        long end1 = System.currentTimeMillis();
        System.out.println(joinPool.isTerminating());

        System.out.println("总时间:" + (end1 - start));
    }
}


	
	
