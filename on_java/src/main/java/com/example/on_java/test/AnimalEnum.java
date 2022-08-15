package com.example.on_java.test;

/**
 * @author chenzhiqin
 * @date 2022/8/12 11:41
 * @info XX
 */
public enum AnimalEnum {
    dog(1),
    pig(2),
    monkey{
        @Override
        int amount() {
            throw new RuntimeException("不可选用");
        }
    }
    ;


    AnimalEnum(int count) {
        this.count = count;
    }

    AnimalEnum() {
    }

    private int count;

    int amount(){
        return  count;
    }



}
