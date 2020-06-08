package com.wolf.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StreamTest {

    @Test
    public void testPartStream() {

        List<String> employeeIds = new ArrayList<>();
        int i = 0;
        while (i < 100) {
            employeeIds.add("employee_" + i);
            i++;
        }
        long start = System.currentTimeMillis();


        employeeIds.parallelStream().forEach(s -> {
            System.out.println("id:" + s);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");

    }

    @Test
    public void testStream(){

        List<String> employeeIds = new ArrayList<>();
        int i = 0;
        while (i < 100) {
            employeeIds.add("employee_" + i);
            i++;
        }
        long start = System.currentTimeMillis();

        employeeIds.stream().forEach(s -> {
            System.out.println("id:" + s);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");

    }
}
