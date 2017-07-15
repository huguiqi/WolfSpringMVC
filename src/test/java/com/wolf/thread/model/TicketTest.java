package com.wolf.thread.model;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by sam on 2017/7/10.
 */
 class Ticket {

    private int num;


    public Ticket(){
        super();
    }

    public Ticket(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public  void setNum(int num) {
        this.num = num;
    }
}

class Ticket2 {

    private int num;

    Lock lock = new ReentrantLock();

    public Ticket2(){
        super();
    }

    public Ticket2(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public  void setNum(int num) {
        this.num = num;
    }
}

class Ticket3 {

     //Atomic类所有的操作都是原子性的
    private AtomicInteger num;


    public Ticket3(){
        super();
    }

    public Ticket3(AtomicInteger num) {
        this.num = num;
    }

    public AtomicInteger getNum() {
        return num;
    }

    public  void setNum(AtomicInteger num) {
        this.num = num;
    }
}


public class TicketTest{

    @Test
    public void testSall(){
        final Ticket ticket = new Ticket(10);
            //分四个线程去卖票，直到票卖完
            new Thread(new TicketRunnable(ticket)).start();
            new Thread(new TicketRunnable(ticket)).start();
            new Thread(new TicketRunnable(ticket)).start();
            new Thread(new TicketRunnable(ticket)).start();
            new Thread(new TicketRunnable(ticket)).start();


    }

    @Test
    public void testSall2() throws InterruptedException {
        final Ticket2 ticket = new Ticket2(100);
        //分四个线程去卖票，直到票卖完

        new Thread(new TicketRunnable2(ticket)).start();
        new Thread(new TicketRunnable2(ticket)).start();
        new Thread(new TicketRunnable2(ticket)).start();
        new Thread(new TicketRunnable2(ticket)).start();
        //让主线程睡眠10毫秒，等待子线程执行完毕
        TimeUnit.SECONDS.sleep(10);

    }

    @Test
    public void testSall3() throws InterruptedException {
        final Ticket3 ticket = new Ticket3(new AtomicInteger(100));
        //分四个线程去卖票，直到票卖完

        new Thread(new TicketRunnable3(ticket)).start();
        new Thread(new TicketRunnable3(ticket)).start();
        new Thread(new TicketRunnable3(ticket)).start();
        new Thread(new TicketRunnable3(ticket)).start();
        //让主线程睡眠10毫秒，等待子线程执行完毕
        TimeUnit.SECONDS.sleep(10);

    }
}


class TicketRunnable implements Runnable{

    private Ticket ticket;

    public TicketRunnable(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {

               while (true){
                   synchronized (ticket){
                       if (ticket.getNum() > 0) {
                           ticket.setNum(ticket.getNum() - 1);
                           System.out.println(Thread.currentThread().getName() + ".....sale...." + ticket.getNum());
                       }
                   }
               }
            }
}


class TicketRunnable2 implements Runnable{

    private Ticket2 ticket;

    public TicketRunnable2(Ticket2 ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true){
                    try {
                        ticket.lock.lock();
                        if (ticket.getNum() > 0) {
                            ticket.setNum(ticket.getNum() - 1);
                            System.out.println(Thread.currentThread().getName() + ".....sale...." + ticket.getNum());
                        }
                    }finally {
                        ticket.lock.unlock();
                    }
        }
    }
}


class TicketRunnable3 implements Runnable{

    private Ticket3 ticket;

    public TicketRunnable3(Ticket3 ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true){
                if (ticket.getNum().get() > 0) {
                    System.out.println(Thread.currentThread().getName() + ".....sale...." + ticket.getNum().decrementAndGet());
                }
        }
    }
}