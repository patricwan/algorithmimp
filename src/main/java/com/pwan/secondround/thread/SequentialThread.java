package com.pwan.secondround.thread;

public class SequentialThread {

    public static volatile int flag = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("This is the start of the program");


        Runnable firstThread = new FirstThread("first" );
        Runnable secondThread = new SecondThread("second" );

        secondThread.run();
        firstThread.run();

        Thread.sleep(3000);

    }
}
