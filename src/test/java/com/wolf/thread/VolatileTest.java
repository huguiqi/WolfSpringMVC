package com.wolf.thread;


/**
 * Created by sam on 2017/7/9.
 */
public class VolatileTest {

// 使用volatile时，主线程对flag作了修改，由于是volatile修饰，子线程在本地作判断时，直接取的主存的值

    //    static volatile boolean flag = false;
    // 不使用volatile时，第一个线程由于没有同步到flag值，一直取的是本地缓存的flag值，还是false
    static  boolean flag = false;

// volatile 无法解决并发数据原子性的问题，只实现了变量在各线程的可见性

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i =0;
                while (!flag){
                    //不能使用println，因为它是同步方法，执行后就会从本地栈同步flag，这样就和使用volatile的效果是一样的
//                    System.out.println("run");
                    i++;
                }

            }
        }).start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
    }
}
