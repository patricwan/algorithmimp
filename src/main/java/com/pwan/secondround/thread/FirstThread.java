package com.pwan.secondround.thread;

public class FirstThread implements  Runnable {

    private String name = null;


    public FirstThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (SequentialThread.flag!=0) {
                //continous wait...
            }
            System.out.println("Thread " + name + " starts to run ");
            Thread.sleep(3000);
            System.out.println("Thread " + name + " is finished");

            SequentialThread.flag = 1;
        } catch (Exception e) {

        }

    }
}
