package com.example.rabbitmq.rabbitmqhello;

import java.io.UnsupportedEncodingException;

/**
 * @author chenzhiqin
 * @date 2022/7/12 17:20
 * @info XX
 */

public class Test {
    /**
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String name = getName("6212143000000000011");
        System.out.println(name);
    }


    private static String getName(String cardno){
        String n = "0";
        final String cn = cardno.replaceAll("[^0-9]","");
        if(!"".equals(cn) && cn.length()>=5){
            String endcard = cn.substring(cn.length()-5);
            try{
                int num = Integer.parseInt(endcard);
                int mod = num%20;
                n = String.valueOf(mod);
            }catch(Exception e){
            }
        }
        if(n.length()==1){
            n = "0"+n;
        }
        return n;
    }
}
