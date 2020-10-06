package com.pwan.secondround.thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchObj {

    private CountDownLatch countDownLatch1 = new CountDownLatch(1);

    private CountDownLatch countDownLatch2 = new CountDownLatch(1);

    public void testExecution() {
        System.out.println("This is the start of test execution");


        //Thread 1 start
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("First thread executed");
                    countDownLatch1.countDown();
                } catch( Exception e) {

                }

            }
        }).start();

        //Thread2 start
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch1.await();
                    Thread.sleep(1000);
                    System.out.println("Second thread executed");
                    countDownLatch2.countDown();
                } catch( Exception e) {

                }
            }
        }).start();



    }

    public static void main(String[] args) {
        System.out.println("This is the start of the main program");

        CountDownLatchObj countDownLatchObj = new CountDownLatchObj();

        countDownLatchObj.testExecution();

    }
}
