package com.pwan.secondround;

import java.util.*;

public class MyQueueWithStack {

    Stack<Integer> inStack = new Stack<Integer>();
    Stack<Integer> outStack = new Stack<Integer>();

    MyQueueWithStack() {
    }

    //Add one element
    public void add(int data) {
        inStack.push(data);
    }

    //Pop one element
    public int pop() {
        while(!inStack.isEmpty()) {
            int element = inStack.pop();
            outStack.push(element);
        }
       int elementRet = outStack.pop();

        inStack = outStack;
        outStack.clear();
        inStack.push(elementRet);

        return elementRet;
    }

    public static void main(String[] args) {
        System.out.println("This is the start of the program");

        MyQueueWithStack myQueue = new MyQueueWithStack();
        myQueue.add(1);
        myQueue.add(3);
        myQueue.add(5);

        System.out.println("pop one element " + myQueue.pop());
    }
}
