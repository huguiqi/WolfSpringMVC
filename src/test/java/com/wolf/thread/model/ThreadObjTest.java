package com.wolf.thread.model;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sam on 2017/7/11.
 */
public class ThreadObjTest {

    @Test
    public void testObjThread(){

        FileStream obj = new FileStream();

        Thread t1 = new Thread(new RunableObj1(obj));
        Thread t2 = new Thread(new RunableObj2(obj));
        t1.start();
        t2.start();

    }

    @Test
    public void testObjThreadForLock(){

        FileStream obj = new FileStream();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread t1 = new Thread(new RunableObjLock1(obj,lock,condition));
        Thread t2 = new Thread(new RunableObjLock2(obj,lock,condition));
        t1.start();
        t2.start();

    }

    @Test
    public void testObjThreadForLock2(){

        FileStream2 obj = new FileStream2();
        Thread t1 = new Thread(new RunableObjLock3(obj));
        Thread t2 = new Thread(new RunableObjLock4(obj));
        t1.start();
        t2.start();

    }

}

class FileStream{

    private byte[] files;

    public byte[] getFiles() {
        return files;
    }

    public void setFiles(byte[] files) {
        this.files = files;
    }

    public void readFile(){
                for (byte fileb : files){
                    System.out.println("files content:"+fileb);
                }
    }


    //假设读写操作都是异步操作
    public void writeFile(){
            files = new byte[12];
            for (int i=0;i<files.length;i++){
                files[i] = (byte) ('a' + i);
            }
    }
}

class FileStream2{

    private byte[] files;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public byte[] getFiles() {
        return files;
    }

    public void setFiles(byte[] files) {
        this.files = files;
    }

    public void readFile(){
        if (lock.tryLock()){
            try {
                condition.await();
                System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁。。。。");
        for (byte fileb : files){
            System.out.println("files content:"+fileb);
        }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }


    //假设读写操作都是异步操作
    public void writeFile(){
        lock.lock();
        try {
        files = new byte[12];
        for (int i=0;i<files.length;i++){
            files[i] = (byte) ('a' + i);
        }
            condition.signal();
            System.out.println("线程"+Thread.currentThread().getName()+"调用了notify");
        }finally {
            lock.unlock();
        }
    }
}

class RunableObj1 implements Runnable{

    private FileStream obj;

    public RunableObj1(FileStream obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        //这是obj的一个对象锁
        synchronized (obj){
            try {
                //将线程1的对象锁让出，让线程1等待被通知唤醒
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //因为直接读会发现还没有写入文件，需要等待写入后再读
            obj.readFile();
            System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁。。。。");
        }
    }
}


class RunableObj2 implements Runnable{

    private FileStream obj;

    public RunableObj2(FileStream obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        synchronized (obj){
            //写完后调用notyfy释放锁，通知其它在等待的线程继续操作
            obj.writeFile();
            obj.notify();
            System.out.println("线程"+Thread.currentThread().getName()+"调用了notify");
        }
        System.out.println("线程"+Thread.currentThread().getName()+"释放了锁");
    }
}

class RunableObjLock1 implements Runnable{

    private FileStream obj;
    private Lock lock;
    private Condition condition;

    public RunableObjLock1(FileStream obj,Lock lock,Condition condition) {
        this.obj = obj;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
            lock.lock();
            try {
//                将线程1的对象锁让出，让线程1等待被通知唤醒
                condition.await();
                //因为直接读会发现还没有写入文件，需要等待写入后再读
                obj.readFile();
                System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁。。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
    }
}

class RunableObjLock2 implements Runnable{

    private FileStream obj;
    private Lock lock;
    private Condition condition;

    public RunableObjLock2(FileStream obj,Lock lock,Condition condition) {
        this.obj = obj;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            //写完后调用notyfy释放锁，通知其它在等待的线程继续操作
            obj.writeFile();
//            obj.notify();
            condition.signal();
            System.out.println("线程"+Thread.currentThread().getName()+"调用了notify");
        }finally {
            lock.unlock();
        }
    }
}



class RunableObjLock3 implements Runnable{

    private FileStream2 obj;

    public RunableObjLock3(FileStream2 obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
            obj.readFile();
    }
}


class RunableObjLock4 implements Runnable{

    private FileStream2 obj;

    public RunableObjLock4(FileStream2 obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
            obj.writeFile();
    }
}
