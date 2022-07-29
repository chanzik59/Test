package com.example.demo.controller;

import java.util.Optional;
import java.util.Random;

/**
 * @author chenzhiqin
 * @date 2022/6/20 13:57
 * @info
 */
public class TestJava8 {
    public static void main(String[] args) throws InterruptedException {
        Optional.ofNullable("1").ifPresent(System.out::println);
    }


    private static String getPrice() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(10) * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(random.nextDouble());
    }
}
