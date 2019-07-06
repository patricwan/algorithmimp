package com.pwan.pkg;

import java.util.Stack;


public class MyQueueWithStack {

    Stack<Integer> s1;//用于入队
    Stack<Integer> s2;//用于出队

    /**
     * Initialize your data structure here.
     */
    public MyQueueWithStack() {

        s1 = new Stack<Integer>();
        s2 = new Stack<Integer>();

    }

    public static void main(String[] args) {
        System.out.println("This is the start of main program");
        MyQueueWithStack myQueue = new MyQueueWithStack();
        myQueue.push(3);
        myQueue.push(5);
        myQueue.push(8);

        System.out.println(myQueue.peek());
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        s1.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if(!s2.isEmpty()){//出队栈不为空时，直接从出队栈中移除栈顶元素
            return s2.pop();
        }else{//出队栈为空时，从入队栈中依次将元素放入出队栈
            while(!s1.isEmpty()){
                s2.push(s1.pop());
            }
            return s2.pop();//放完后，从出队栈依次将栈顶元素弹出
        }
    }

    /**
     * Get the front element.
     */
    public int peek() {
        if (!s2.isEmpty()) {//出队栈不为空时，直接从出队栈中得到栈顶元素
            return s2.peek();
        } else {//出队栈为空时，从入队栈中依次将栈顶元素放入出队栈
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            return s2.peek();//放完后，从出队栈得到栈顶元素
        }

    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {

        return s1.isEmpty() && s2.isEmpty();
    }


}
