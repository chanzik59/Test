package com.example.demo.controller;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenzhiqin
 * @date 2022/6/8 11:50
 * @info XX
 */
@Controller
@RequestMapping(value = "/gateway")
public class TestController {
    @RequestMapping(value = "/trx", method = {RequestMethod.POST, RequestMethod.GET})
    public String doTest(HttpServletRequest request) {
        System.out.println("111111");
        String method = request.getMethod();
        System.out.println(method);
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String[]> parameterMap = request.getParameterMap();

        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = (String) params.nextElement();
            System.out.println(request.getParameter(key));
        }


        return null;

    }

    /*public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException {
        Cipher des = Cipher.getInstance("AES");
        KeyGenerator aes = KeyGenerator.getInstance("AES");
        SecureRandom sha1PRNG = SecureRandom.getInstance("SHA1PRNG");
        sha1PRNG.setSeed("1234567".getBytes(StandardCharsets.UTF_8));
        aes.init(128, sha1PRNG);
        SecretKey secretKey = aes.generateKey();

        SecretKeySpec des1 = new SecretKeySpec(secretKey.getEncoded(), "AES");
        des.init(Cipher.ENCRYPT_MODE, des1);
        byte[] bytes = des.doFinal("4561455555555555555555555555555555555111".getBytes(StandardCharsets.UTF_8));
        String s = Base64.getEncoder().encodeToString(bytes);
        System.out.println(s);
        des.init(Cipher.DECRYPT_MODE, des1);
        byte[] bytes1 = des.doFinal(Base64.getDecoder().decode(s));
        System.out.println(new String(bytes1, StandardCharsets.UTF_8));
        String keyString = FileUtils.readFileToString(new File(""), StandardCharsets.UTF_8.name());
        byte[] decode = Base64.getDecoder().decode(keyString.getBytes(StandardCharsets.UTF_8));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode);
        PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);


        KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
        keyPair.initialize(1024);
        KeyPair keyPair1 = keyPair.generateKeyPair();
        PrivateKey aPrivate = keyPair1.getPrivate();
        PublicKey aPublic = keyPair1.getPublic();
        byte[] encoded = aPrivate.getEncoded();
        String s = Base64.getEncoder().encodeToString(encoded);
        System.out.println(s);
        byte[] encoded1 = aPublic.getEncoded();
        String s1 = Base64.getEncoder().encodeToString(encoded1);
        System.out.println(s1);


    }*/


    public static void makeRsaKey() throws Exception {
        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(1024);
        KeyPair keyPair = rsa.generateKeyPair();
        PrivateKey aPrivate = keyPair.getPrivate();
        PublicKey aPublic = keyPair.getPublic();
        byte[] encoded = aPrivate.getEncoded();
        String encrypt = Base64.getEncoder().encodeToString(encoded);
        String decrypt = Base64.getEncoder().encodeToString(aPublic.getEncoded());
        FileUtils.writeStringToFile(new File("C:\\Users\\phone\\Desktop\\生产密钥管理\\testprivate.pem"), encrypt, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File("C:\\Users\\phone\\Desktop\\生产密钥管理\\testpublic.pem"), decrypt, StandardCharsets.UTF_8);

    }

    private final static String algorithm = "AES/CBC/PKCS5Padding";


    public static void main(String[] args) throws Exception {
      /*  String content = "123456789";
        PublicKey publicKey = getPublicKey("C:\\Users\\phone\\Desktop\\生产密钥管理\\testpublic.pem");
        String encrypt = rsaEncrypt(content, publicKey);
        System.out.println(encrypt);
        PrivateKey privateKey = getPrivateKey("C:\\Users\\phone\\Desktop\\生产密钥管理\\testprivate.pem");
        String decrypt = rsaDecrypt(encrypt, privateKey);
        System.out.println(decrypt);
        String sign = getSign(content, privateKey);
        System.out.println(sign);
        Boolean aBoolean = validSign(sign, content, publicKey);
        System.out.println(aBoolean);*/
        String password="11111111";




        KeyStore keyStore = getKeyStore("C:\\Users\\phone\\Desktop\\公司资料\\文档管理\\银联B2B接入\\网银支付业务证书说明和测试环境证书\\测试环境国际算法机构证书（含私钥）\\encryp.pfx", password);
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()){
            String name = aliases.nextElement();
            System.out.println(name);
            Key key = keyStore.getKey(name, password.toCharArray());
            X509Certificate certificate = (X509Certificate)keyStore.getCertificate(name);
            System.out.println(certificate.getSerialNumber().toString(16));
            String s = Base64.getEncoder().encodeToString(certificate.getEncoded());
            System.out.println(s);

          /*  System.out.println(key instanceof  PrivateKey);
            System.out.println(key.getAlgorithm());*/
        }
    }

    public static String getSign(String content, PrivateKey privateKey) throws Exception {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(bytes);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    public static Boolean validSign(String sign, String content, PublicKey publicKey) throws Exception {
        Signature sha256withRSA = Signature.getInstance("SHA256withRSA");
        sha256withRSA.initVerify(publicKey);
        sha256withRSA.update(content.getBytes(StandardCharsets.UTF_8));
        return sha256withRSA.verify(Base64.getDecoder().decode(sign));
    }


    public static String rsaEncrypt(String content, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = doCodes(cipher, content.getBytes(StandardCharsets.UTF_8), 117);
        return Base64.getEncoder().encodeToString(bytes);

    }

    public static String rsaDecrypt(String enContent, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = doCodes(cipher, Base64.getDecoder().decode(enContent), 128);
        return new String(bytes);
    }

    private static byte[] doCodes(Cipher cipher, byte[] bytes, int maxBlockSize) throws Exception {
        int inputLength = bytes.length;
        int offset = 0;
        byte[] cache;
        int index = 0;
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


    public static PublicKey getPublicKey(String path) throws Exception {
        String keyString = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(keyString));
        return factory.generatePublic(x509EncodedKeySpec);
    }

    public static PrivateKey getPrivateKey(String path) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        String privateString = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateString));
        return factory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * 测试摘要算法
     *
     * @param content
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String digest(String content) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("sha-512");
        md5.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5.digest();
        return Hex.encodeHexString(digest);
    }

    public static String mac(String content, String key) throws Exception {
        Mac hmacMD5 = Mac.getInstance("HmacMD5");
        SecretKeySpec hmacMD51 = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacMD5");
        hmacMD5.init(hmacMD51);
        byte[] bytes = hmacMD5.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(bytes);
    }


    public static String desEncrypt(String content, String key, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom sha1PRNG = SecureRandom.getInstance("SHA1PRNG");
        sha1PRNG.setSeed(key.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(128, sha1PRNG);
        SecretKey secretKey = keyGenerator.generateKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("1234567812345678".getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String desDecrypt(String content, String key, String algorithm) throws Exception {
        byte[] decode = Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(algorithm);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom sha1PRNG = SecureRandom.getInstance("SHA1PRNG");
        sha1PRNG.setSeed(key.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(128, sha1PRNG);
        SecretKey secretKey = keyGenerator.generateKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("1234567812345678".getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] bytes = cipher.doFinal(decode);
        return new String(bytes);
    }


    public static KeyStore getKeyStore(String path, String password) {
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            instance.load(fileInputStream, password.toCharArray());
            return instance;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  null;
    }


}
