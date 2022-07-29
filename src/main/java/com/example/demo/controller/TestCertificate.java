package com.example.demo.controller;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;

/**
 * @author chenzhiqin
 * @date 2022/6/20 10:12
 * @info XX
 */
public class TestCertificate {

    public static void main(String[] args) throws Exception {
        /* 取出证书--从文件中取出 */

        PublicKey publicKey1 = TestController.getPublicKey("C:\\Users\\phone\\Desktop\\生产密钥管理\\testpublic.pem");
        PrivateKey privateKey1 = TestController.getPrivateKey("C:\\Users\\phone\\Desktop\\生产密钥管理\\testprivate.pem");
        String prxPath = "C:\\Users\\phone\\Desktop\\公司资料\\文档管理\\银联B2B接入\\网银支付业务证书说明和测试环境证书\\测试环境国际算法机构证书（含私钥）\\encryp.pfx";
        String cerPath="C:\\Users\\phone\\Desktop\\公司资料\\文档管理\\银联B2B接入\\网银支付业务证书说明和测试环境证书\\测试环境国际算法机构证书（含私钥）\\encryp.cer";
        String password = "11111111";
        KeyStore keyStore = getKeyStore(prxPath, password);
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String name = aliases.nextElement();
            X509Certificate certificate = (X509Certificate)keyStore.getCertificate(name);
            System.out.println(certificate.getSigAlgName());
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(name, password.toCharArray());
            String encrypt = rsaEncrypt(password, privateKey);
            System.out.println(encrypt);
            PublicKey publicKey = certificate.getPublicKey();
            String decrypt = rsaDecrypt(encrypt, publicKey);
            System.out.println(decrypt);


        }


    }

    public static String rsaEncrypt(String content, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = doCode(cipher, content.getBytes(StandardCharsets.UTF_8), 117);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String rsaDecrypt(String encrypt, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = doCode(cipher, Base64.getDecoder().decode(encrypt.getBytes(StandardCharsets.UTF_8)), 128);
        return new String(bytes);
    }

    private static byte[] doCode(Cipher cipher, byte[] bytes, int maxBlockSize) throws Exception {
        int inputLength = bytes.length;
        int index = 0;
        byte[] cache;
        int offset = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (inputLength - offset > 0) {
            if (inputLength - offset > maxBlockSize) {
                cache = cipher.doFinal(bytes, offset, maxBlockSize);
            } else {
                cache = cipher.doFinal(bytes, offset, inputLength - offset);
            }
            index++;
            offset = index * maxBlockSize;
            outputStream.write(cache);
        }
        return outputStream.toByteArray();
    }


    public static void getCertificate(String path) throws Exception {
        CertificateFactory instance = CertificateFactory.getInstance("X.509");
        FileInputStream fileInputStream = new FileInputStream(path);
        X509Certificate certificate = (X509Certificate) instance.generateCertificate(fileInputStream);
        String serialNo = certificate.getSerialNumber().toString(16);
        System.out.println(serialNo);
        Principal issuerDN = certificate.getIssuerDN();
        System.out.println(issuerDN);
        String sigAlgName = certificate.getSigAlgName();
        System.out.println(sigAlgName);
        int version = certificate.getVersion();
        System.out.println(version);
        PublicKey publicKey = certificate.getPublicKey();
        //System.out.println(Base64.getEncoder().encodeToString(certificate.getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
    }


    public static KeyStore getKeyStore(String path, String password) throws Exception {
        KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream fileInputStream = new FileInputStream(path);
        instance.load(fileInputStream, password.toCharArray());
        return instance;
    }


}
