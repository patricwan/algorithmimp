package com.pwan.secondround.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreCountAlloc {


    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        Semaphore semaphore = new Semaphore(10);


        new Thread(new MySemaphoreThread(semaphore, 3)).start();
        new Thread(new MySemaphoreThread(semaphore, 5)).start();
        new Thread(new MySemaphoreThread(semaphore, 4)).start();
        new Thread(new MySemaphoreThread(semaphore, 2)).start();
        new Thread(new MySemaphoreThread(semaphore, 3)).start();

    }
}
