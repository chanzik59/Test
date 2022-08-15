package com.example.on_java.test;

/**
 * @author chenzhiqin
 * @date 2022/8/3 14:03
 * @info XX
 */
public class EnumTool {
    public static <T extends Enum<T>> T[] getEnum(Class<T> tClass) {
        return tClass.getEnumConstants();
    }
}
