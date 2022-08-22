package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class securityapplicationtests {
    public static void main(String[] args) {
        String te="88";
        priString(te);
        te="99";
        System.out.println(te);



    }

    @Test
    void contextLoads() {
    }

    public static void priString(final  String te){
        System.out.println(te);
    }

}
