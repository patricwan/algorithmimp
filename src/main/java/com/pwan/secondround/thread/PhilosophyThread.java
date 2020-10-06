package com.pwan.secondround.thread;

import java.util.concurrent.Semaphore;

public class PhilosophyThread implements Runnable {

    private volatile  Semaphore semaphoreLeft;
    private volatile  Semaphore semaphoreRight;
    private int num;

    public PhilosophyThread(Semaphore semaphoreLeft, Semaphore semaphoreRight, int num) {
        this.semaphoreLeft = semaphoreLeft;
        this.semaphoreRight = semaphoreRight;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            if (num % 2==0) {
                this.semaphoreLeft.acquire();
                System.out.println("Philosophy " + num + " got left sticks");
                this.semaphoreRight.acquire();
                System.out.println("Philosophy " + num + " got right sticks");
                System.out.println("Philosophy " + num + " starts to eat");

                Thread.sleep(3000);
                this.semaphoreLeft.release();
                System.out.println("Philosophy " + num + " free left sticks");
                this.semaphoreRight.release();
                System.out.println("Philosophy " + num + " free right sticks");
                System.out.println("Philosophy " + num + " Ate all ");
            } else {

                this.semaphoreRight.acquire();
                System.out.println("Philosophy " + num + " got right sticks");
                this.semaphoreLeft.acquire();
                System.out.println("Philosophy " + num + " got left sticks");

                System.out.println("Philosophy " + num + " starts to eat");

                Thread.sleep(3000);

                this.semaphoreRight.release();
                System.out.println("Philosophy " + num + " free right sticks");

                this.semaphoreLeft.release();
                System.out.println("Philosophy " + num + " free left sticks");

                System.out.println("Philosophy " + num + " Ate all ");
            }
        } catch(Exception e) {

        } finally {

        }
    }
}
