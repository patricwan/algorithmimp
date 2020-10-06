package com.pwan.secondround.thread;

public class TestSequentialExecutionLock {

    public static void main(String[] args) throws Exception{
        SynchronizedSequntialExecution seq = new SynchronizedSequntialExecution();
        seq.executeSequential();

    }
}
