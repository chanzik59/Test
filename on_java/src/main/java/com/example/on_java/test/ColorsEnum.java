package com.example.on_java.test;

import java.util.function.Supplier;

/**
 * @author chenzhiqin
 * @date 2022/8/2 13:31
 * @info XX
 */
public enum ColorsEnum implements Supplier {

    RED("red", "红色"),
    YELLOW("yellow", "黄色"),
    GREEN("green", "绿色");

    ColorsEnum(String color, String describe) {
        this.color = color;
        this.describe = describe;
    }

    private String color;
    private String describe;

    @Override
    public String toString() {
        return "ColorsEnum{" +
                "color='" + color + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }

    @Override
    public Object get() {
        return null;
    }
}
