package com.pwan.secondround.thread;

import java.util.concurrent.Semaphore;

public class PhilosophyEatingTest {

    public static void main(String[] args) {
        System.out.println("This is the start of Philosophy dinner test");

        //Create 5 sticks as Semaphore
        Semaphore semaphore[] = new Semaphore[5];
        for(int i=0; i<5; i++) {
            semaphore[i] = new Semaphore(1);
        }

        for (int i=0; i<5; i++) {
            if (i==4) {  //should share sticks with 0
                new Thread(new PhilosophyThread(semaphore[i], semaphore[0],i)).start();
            } else {
                new Thread(new PhilosophyThread(semaphore[i], semaphore[i+1], i)).start();
            }
        }
    }
}
