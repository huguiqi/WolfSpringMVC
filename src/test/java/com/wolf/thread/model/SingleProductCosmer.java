package com.wolf.thread.model;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by sam on 2017/7/11.
 */
public class SingleProductCosmer {


@Test
public void testSingleTest(){

    KaoDulkResource resource = new KaoDulkResource();
        new Thread(new Producer(resource)).start();
        new Thread(new Comsumer(resource)).start();

        new Thread(new Producer(resource)).start();
        new Thread(new Comsumer(resource)).start();

        //模拟主线程持续运行的环境下
    try {
        TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}


}

class Producer implements Runnable{

    //共享资源
    private KaoDulkResource resource;

    public Producer(KaoDulkResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.product("烤鸭");
        }
    }
}


class Comsumer implements Runnable{

    private KaoDulkResource resource;

    public Comsumer(KaoDulkResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.consume();
        }
    }
}

