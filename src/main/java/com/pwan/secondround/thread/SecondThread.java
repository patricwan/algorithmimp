package com.pwan.secondround.thread;

public class SecondThread implements  Runnable {

    private String name = null;

    public SecondThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while( SequentialThread.flag!=1 ) {
                //wait until the volatile var changed
            }
            System.out.println("Thread " + name + " starts to run ");
            Thread.sleep(500);
            System.out.println("Thread " + name + " is finished");
          } catch (Exception e) {


        }

    }
}
