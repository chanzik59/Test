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
        String aa = "P0001|PA00002|2207261200000007262475890911|022207261200000007262475890911|1310|09600|200290000013618|00000001|CT000016|01000000|6215824510002357606|0|0|200|0|0|05|0000|交易成功||20220726120001|20220726120002|0|||ucp.100.01|bnk.trx.pay.imm.out|通联支付VSP|通联支付VSP|杜瑞理|||山东|JiNan|200||资金结算|中国电信水寨店|1|215500685|通联支付网络服务股份有限公司|1|2475220726890911|||0|||00|成功[00000000000]|||||172.16.6.59|http://172.16.189.68:8080/tltnotice/ucptrxrst.do";
        String[] split = aa.split("\\|", -1);
        for (int i = 0; i < split.length; i++) {
            System.out.println(i + ":" + split[i]);
        }
    }
}
