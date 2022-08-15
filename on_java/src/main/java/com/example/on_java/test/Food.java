package com.example.on_java.test;

/**
 * @author chenzhiqin
 * @date 2022/8/3 13:48
 * @info XX
 */
public interface Food {
    enum Vegatable implements  Food{
        TOMATO,
        EGGPLANT;
    }
    enum  Meat implements  Food{
        BEEF,
        MUTTON;
    }

}
