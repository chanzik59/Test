package com.example.demo;

import java.util.concurrent.RecursiveAction;

/**
 * @author chenzhiqin
 * @date 2022/6/23 14:26
 * @info XX
 */
public class MyRecursiveAction extends RecursiveAction {
    private static final int Max = 20;
    private int start;
    private int end;

    public MyRecursiveAction(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < Max) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "  " + i);
            }
        } else {
            int middle = (end + start) / 2;
            MyRecursiveAction left = new MyRecursiveAction(start, middle);
            MyRecursiveAction right = new MyRecursiveAction(middle, end);
            left.fork();
            right.fork();
        }

    }
}
