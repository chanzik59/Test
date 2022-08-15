package com.example.on_java.test;

/**
 * @author chenzhiqin
 * @date 2022/8/3 13:55
 * @info XX
 */

public class Test {
    public static void main(String[] args) {
        String aa="40|绑定关系检查失败[6151230]|||||821393060121002|000100000112|1|01|6226090000000048||12|6226090000000048|2208121800000121220424722633||";
        String bb="";
        for (int i = 0; i < 20000; i++) {
            System.out.println("执行次数"+i);

            bb=bb+aa;
        }
        System.out.println(bb.length());
    }

    public String getHello(String hello) {
        return hello + "123";
    }


    private static String getValueByFieldName(String fieldName, String source) {
        return source.substring(source.lastIndexOf(String.format("<%s>", fieldName)), source.lastIndexOf(String.format("</%s>", fieldName)));
    }


    private static String desensitization(String fieldName, String sources, String regex, String replacement) {
        String format = String.format("((<%s><!\\[CDATA\\[)%s(\\]\\]></%s>))", fieldName, regex, fieldName);
        return sources.replaceAll(format, replacement);
    }
}
