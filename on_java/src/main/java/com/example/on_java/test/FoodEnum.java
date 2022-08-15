package com.example.on_java.test;

/**
 * @author chenzhiqin
 * @date 2022/8/4 12:42
 * @info XX
 */
public enum FoodEnum {
    VEGATABLE(Food.Vegatable.class){
        @Override
        void getMessagge() {
            System.out.println("this is vegatable");
        }
    },
    MEAT(Food.Meat.class){
        @Override
        void getMessagge() {
            System.out.println("this is meat");
        }
    };
    private Food[] values;

    FoodEnum(Class<? extends Food> aClass) {
        values = aClass.getEnumConstants();
    }

    abstract void getMessagge();

}
