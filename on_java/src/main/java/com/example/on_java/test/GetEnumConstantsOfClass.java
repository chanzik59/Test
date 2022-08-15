package com.example.on_java.test;

/**
 * @author chenzhiqin
 * @date 2022/8/2 13:35
 * @info XX
 */
// Java program to demonstrate the example
// of T[]  getEnumConstants () method of Class

// Enum Definition
enum Fruits {
    Apple("11","11"),
    Orange("22","22"),
    Banana("33","33"),
    Grapes("44","44");
    private String fruit;
    private String fruit1;

    Fruits(String fruit, String fruit1) {
        this.fruit = fruit;
        this.fruit1 = fruit1;
    }
}
public class GetEnumConstantsOfClass {
    public static void main(String[] args) {
        Enum red = ColorsEnum.RED;
        Class<? extends Enum> aClass = red.getClass();

        // Get class
        Class cl = ColorsEnum.class;

        // Copying enum constants one by one in Object
        for (Object o: aClass.getEnumConstants())
            System.out.println(o);
    }
}

