package com.wolf.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by sam on 2017/7/7.
 */
public class ThreadTest {

    @Test
    public void testArrayListThread() {

        int a = 0;
        do {
            // 用来测试的List
            List<String> data = new ArrayList<String>();
            // 用来让主线程等待100个子线程执行完毕
            CountDownLatch countDownLatch = new CountDownLatch(100);
            // 启动100个子线程
            for (int i = 0; i < 100; i++) {
                SampleTask task = new SampleTask(data, countDownLatch);
                Thread thread = new Thread(task);
                thread.start();
            }
            try {
                // 主线程等待所有子线程执行完成，再向下执行
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // List的size
            System.out.println(data.size());
            a++;
        } while (a < 10);

        // 连续运行7次，得出：10000，9990，10000，9991，10000，10000，9996
        //因为ArrayList不是系统安全的，即使串行化执行，得出的也不全是10000;

    }

    @Test
    public void testArrayListThread2() {

        int a = 0;
        do {
            long start = System.currentTimeMillis();
            // 用来测试的List
            List<String> data = new ArrayList<String>();
            // 用来让主线程等待100个子线程执行完毕
            CountDownLatch countDownLatch = new CountDownLatch(100);
            // 启动100个子线程
            for (int i = 0; i < 100; i++) {
                SampleTaskSyn task = new SampleTaskSyn(data, countDownLatch);
                Thread thread = new Thread(task);
                thread.start();
            }
            try {
                // 主线程等待所有子线程执行完成，再向下执行
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // List的size
            System.out.println(data.size());
            long end = System.currentTimeMillis();
            System.out.println("用时：" + (end - start) + "ms");
            a++;
        } while (a < 10);


        // 连续运行7次，都得出：10000

    }


    @Test
    public void testVectorThread() {

        int a = 0;
        do {
            long start = System.currentTimeMillis();
            // 用来测试的List
            Vector<String> data = new Vector<String>();

            // 用来让主线程等待100个子线程执行完毕
            CountDownLatch countDownLatch = new CountDownLatch(100);
            // 启动1000个子线程
            for (int i = 0; i < 1000; i++) {
                SampleTaskVector task = new SampleTaskVector(data, countDownLatch);
                Thread thread = new Thread(task);
                thread.start();
            }

            try {
                // 主线程等待所有子线程执行完成，再向下执行
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // List的size，主线程获取data的size
            System.out.println(data.size());
            long end = System.currentTimeMillis();
            System.out.println("用时：" + (end - start) + "ms");
            a++;
            // 连续运行10次，得出的值不全是10000
        } while (a < 10);

        //SampleTaskVector类内部成员变量Vector是线程安全的,CountDownLatch是用来串行化线程执行的，所以这不是并发，如果只串行100个线程，其它999个线程任其并行，那数据就不会全是1000

    }

    @Test
    public void testVectorThread2() {

        int a = 0;
        do {
            long start = System.currentTimeMillis();
            // 用来测试的List
            Vector<String> data = new Vector<String>();
            // 启动100个子线程
            for (int i = 0; i < 1000; i++) {
                SampleTaskVector2 task = new SampleTaskVector2(data);
                Thread thread = new Thread(task);
                thread.start();
            }
            //保证一组线程都走完再打印data.size
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // List的size
            System.out.println(data.size());
            long end = System.currentTimeMillis();
            System.out.println("用时：" + (end - start) + "ms");
            a++;
        } while (a < 10);

        // 连续运行7次，都得出：10000
    }

    @Test
    public void testNotSynThread() {

        ATMCard atmCard = new ATMCard();

        ATMRunable1 runable1 = new ATMRunable1(atmCard);
        ATMRunable1 runable2 = new ATMRunable1(atmCard);
//        ATMRunable2 runable2 = new ATMRunable2(atmCard);

        Thread t1 = new Thread(runable1);
        t1.start();

        Thread t2 = new Thread(runable2);
        t2.start();

    }


    @Test
    public void testNotSynThread2() {

        ATMCard2 atmCard = new ATMCard2();
        ATMRunable2 runable1 = new ATMRunable2(atmCard);
        ATMRunable2 runable2 = new ATMRunable2(atmCard);

        Thread t1 = new Thread(runable1);
        t1.start();
        Thread t2 = new Thread(runable2);
        t2.start();

    }

    @Test
    public void testThreadJoin() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("00 run thread.name :" + Thread.currentThread().getName());
            }
        });


        new Thread(new ThreadJoin(t1)).start();

        t1.start();

    }
}

class ThreadJoin implements Runnable {

    private Thread t1;

    public ThreadJoin(Thread t1) {
        this.t1 = t1;
    }

    @Override
    public void run() {
        try {
            //当前线程等待t1线程结束之后财返回执行
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("01 run thread.name:" + Thread.currentThread().getName());
    }

}


class ATMRunable1 implements Runnable {

    private ATMCard atmCard;

    public ATMRunable1(ATMCard atmCard) {
        this.atmCard = atmCard;
    }

    @Override
    public void run() {
        synchronized (atmCard) {
            atmCard.changeCharge(100);
            atmCard.printTotal();
        }
    }
}

class ATMRunable2 implements Runnable {

    private ATMCard2 atmCard;

    public ATMRunable2(ATMCard2 atmCard) {
        this.atmCard = atmCard;
    }

    @Override
    public void run() {
//        synchronized (atmCard) {
        atmCard.changeCharge(-50);
        atmCard.printTotal();
//        }

    }
}


class ATMCard {

    //余额，本身具有原子性，但changeCharge方法不具有原子性
    int charge;


    public void changeCharge(int money) {
        charge = charge + money;
    }


    public void printTotal() {
        System.out.println("total money:" + this.charge);
    }
}

class ATMCard2 {

    //余额
    int charge;


    public synchronized void changeCharge(int money) {
        charge = charge + money;
    }


    public synchronized void printTotal() {
        System.out.println("total money:" + this.charge);
    }
}


class SampleTask implements Runnable {
    CountDownLatch countDownLatch;
    List<String> data;

    public SampleTask(List<String> data, CountDownLatch countDownLatch) {
        this.data = data;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 每个线程向List中添加100个元素
        for (int i = 0; i < 100; i++) {
            data.add("1");
        }
        // 完成一个子线程
        countDownLatch.countDown();
    }
}


class SampleTaskSyn implements Runnable {
    CountDownLatch countDownLatch;
    List<String> data;

    public SampleTaskSyn(List<String> data, CountDownLatch countDownLatch) {
        this.data = data;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 每个线程向List中添加100个元素
        for (int i = 0; i < 100; i++) {
            synchronized (data) {
                data.add("1");
            }
        }
        // 完成一个子线程
        countDownLatch.countDown();
    }
}

class SampleTaskVector implements Runnable {
    CountDownLatch countDownLatch;
    Vector<String> data;

    public SampleTaskVector(Vector<String> data, CountDownLatch countDownLatch) {
        this.data = data;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        synchronized (data) {
            data.add("1");
        }
        // 完成一个子线程
        countDownLatch.countDown();
    }
}

class SampleTaskVector2 implements Runnable {
    Vector<String> data;

    public SampleTaskVector2(Vector<String> data) {
        this.data = data;
    }

    @Override
    public void run() {
        synchronized (data) {
            data.add("1");
        }
    }
}
