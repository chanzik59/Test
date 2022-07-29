package com.example.demo;

import java.util.concurrent.RecursiveTask;

/**
 * @author chenzhiqin
 * @date 2022/6/23 14:26
 * @info XX
 */
public class MyRecursiveTask extends RecursiveTask<Integer> {
    private static final int Max = 20;
    private int start;
    private int end;

    public MyRecursiveTask(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        if (end - start < Max) {
            Integer sum = 0;
            for (int i = start; i < end; i++) {
                sum = sum + i;
            }
            return sum;
        }
        int middle = (start + end) / 2;
        MyRecursiveTask left = new MyRecursiveTask(start, middle);
        MyRecursiveTask right = new MyRecursiveTask(middle, end);
        left.fork();
        right.fork();
        return left.join() + right.join();
    }
}
