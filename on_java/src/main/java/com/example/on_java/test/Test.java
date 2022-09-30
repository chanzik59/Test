package com.example.on_java.test;

import javafx.util.Pair;
import lombok.Builder;
import org.springframework.beans.factory.DisposableBean;

import java.io.File;
import java.io.IOException;

/**
 * @author chenzhiqin
 * @date 2022/8/3 13:55
 * @info XX
 */

@Builder
public class Test implements DisposableBean {
    public static void main(String[] args) throws IOException {
        Pair<String, String> pair = new Pair<>("S", "v");
        System.out.println(pair.getKey());
        System.out.println(pair.getValue());

    }


    private static void setFile(File file, String cc) {
        file = new File("C:\\Users\\phone\\Desktop\\ucp_trxing_record20220828.txt");
        cc = "123";
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

    @Override
    public void destroy() throws Exception {

    }
}
