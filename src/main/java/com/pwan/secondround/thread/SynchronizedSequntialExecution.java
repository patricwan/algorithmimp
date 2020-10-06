package com.pwan.secondround.thread;

public class SynchronizedSequntialExecution {

    private Object lockedObject = new Object();

    private int flag = 0;

    public void executeSequential() throws Exception {
        System.out.println("This is the start of the main program");

        //First thread start
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockedObject) {
                    try {
                        while(flag!=0) {
                            lockedObject.wait();
                        }

                        Thread.sleep(2000);
                        System.out.println("First thread executed");
                    } catch(Exception e ) {
                        e.printStackTrace();
                    }
                    flag = 1;
                    lockedObject.notifyAll();
                }


            }
        }).start();

        //Second thread start
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockedObject) {
                    try {
                        while (flag != 1) {
                            lockedObject.wait();
                        }
                        Thread.sleep(1000);
                        System.out.println("Second thread executed");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    lockedObject.notifyAll();
                }

            }
        }).start();


        Thread.sleep(3000);

    }
}
