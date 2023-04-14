package com.example.consumer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * @author chenzhiqin
 * @date 29/3/2023 15:27
 * @info XX
 */
public class Test {
    public static void main(String[] args) throws Exception {


        String bb="国银种阿凡达发大水！@￥范德国银种阿凡达发大水国银种阿凡达发大水国银种阿凡达发大水国银种阿凡达发大水国银种阿凡达发大水国";
        System.out.println(bb.getBytes(StandardCharsets.UTF_8).length);
        String aa="国银种阿凡达发大水！@#￥%范德aaaaa萨范德萨范11111111111111111111111111德萨发士大夫as联测7777777777777a77777   7777777777   77777131321777777777777777777777777777777777777777777试收款5656546645645456$%^&*^&(*^)&*)*(&)(&*AAAAFDSAS65方名国银联测试收款国aaaaaaaaaaaaaaaaaaaaafdafdafewqrwq银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款国银联测试收款1111abcddafdafd11银联测1111111银联测aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1111111银联测111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
//
//
//
//
//        String lenString = getLenString(aa, 60);
//
//        System.out.println(lenString);
//        System.out.println(lenString.getBytes(StandardCharsets.UTF_8).length);
//
//
//        System.out.println("============================");
//
//        String test = test(aa, 60);
//
//
//        System.out.println(test);
//        System.out.println(test.getBytes(StandardCharsets.UTF_8).length);
    }



    public static String getLenString(String content, int len) {
        if (content == null || content.length() == 0) {
            return content;
        }
        byte[] bytes = content.getBytes();
        if (bytes.length > len) {
            int tempLen = new String(bytes, 0, len).length();
            content = content.substring(0, tempLen);
            // 防止最后一个字符的长度不是一个字节数
            if (content.getBytes().length > len) {
                System.out.println(content+"===");
                System.out.println(content.getBytes(StandardCharsets.UTF_8).length);
                content = content.substring(0, tempLen - 1);
                System.out.println("被执行");
            }
        }
        return content;
    }



    public static String  test(String text,int length){
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        if(bytes.length>length){
            text = new String(bytes, 0, length);
            if(text.getBytes(StandardCharsets.UTF_8).length>length){
                System.out.println("被执行2");
                text =text.substring(0,text.length()-1);
            }
        }

        return text;
    }

}
