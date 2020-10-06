package com.pwan.secondround.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreObjTest {

    private Semaphore spa = new Semaphore(0);
    private Semaphore spb = new Semaphore(0);

    public void testExecution() {

        //Thread 1 start
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("First thread executed");
                    spa.release();
                } catch( Exception e) {

                }

            }
        }).start();

        //Thread2 start
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    spa.acquire();
                    Thread.sleep(1000);
                    System.out.println("Second thread executed");
                    spb.release();
                } catch( Exception e) {

                }
            }
        }).start();



    }

    public static void main(String[] args) {
        System.out.println("This is the start of the main program");

        SemaphoreObjTest semaphoreObjTest = new SemaphoreObjTest();
        semaphoreObjTest.testExecution();

    }
}
