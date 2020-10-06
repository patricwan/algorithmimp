package com.pwan.secondround.thread;

import java.util.concurrent.*;


public class H2OTest {

    public static void main(String[] args) {
        System.out.println("This is the start of H20 Test");

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);;
        Semaphore hydrogen = new Semaphore(2);
        Semaphore oxygen = new Semaphore(1);

        //H2
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    hydrogen.acquire();
                    cyclicBarrier.await();
                    Thread.sleep(1000);

                    hydrogen.release();
                    System.out.println("Produced One H ");
                } catch (Exception e) {

                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    hydrogen.acquire();
                    cyclicBarrier.await();
                    Thread.sleep(1000);

                    hydrogen.release();
                } catch (Exception e) {

                }
            }
        }).start();

        //O
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    oxygen.acquire();
                    cyclicBarrier.await();
                    Thread.sleep(2000);

                    oxygen.release();
                    System.out.println("Produced One O ");
                } catch (Exception e) {

                }
            }
        }).start();


    }
}
