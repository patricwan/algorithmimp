package com.pwan.secondround.thread;

import java.util.concurrent.Semaphore;

public class MySemaphoreThread implements Runnable {

    private volatile Semaphore semaphore;
    private int allowedCount;

    MySemaphoreThread(Semaphore semaphore, int allowedCount) {
        this.semaphore = semaphore;
        this.allowedCount = allowedCount;
    }

    @Override
    public void run() {

        try {
            semaphore.acquire(this.allowedCount);
            System.out.println("thread " + Thread.currentThread().getName() + " started ");
            Thread.sleep(2000);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            semaphore.release(this.allowedCount);
        }


    }
}
