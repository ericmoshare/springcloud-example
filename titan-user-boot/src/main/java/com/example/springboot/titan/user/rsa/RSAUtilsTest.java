package com.example.springboot.titan.user.rsa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by sheng on 2020/6/16.
 */
public class RSAUtilsTest {

    @Test
    public void testDecrypt() throws Exception {

        Map<String, String> keyMap = RSAUtils.createKeys(1024);

        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        System.out.println("公钥: " + publicKey + "\n");
        System.out.println("私钥：" + privateKey + "\n");

        String str = "这是一段test明文";
        System.out.println("明文：" + str + "\n");

        String encodedData = RSAUtils.publicEncrypt(str, RSAUtils.getPublicKey(publicKey));
        System.out.println("密文：" + encodedData + "\n");

        String decodedData = RSAUtils.privateDecrypt(encodedData, RSAUtils.getPrivateKey(privateKey));
        System.out.println("解密后文字: " + decodedData + "\n");


    }

    @Test
    public void testSign() throws Exception {

        Map<String, String> keyMap = RSAUtils.createKeys(1024);

        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        System.out.println("公钥: " + publicKey + "\n");
        System.out.println("私钥：" + privateKey + "\n");

        String data = "这是一段test明文";

        // RSA签名
        String sign = RSAUtils.sign(data, RSAUtils.getPrivateKey(privateKey));

        // RSA验签
        boolean result = RSAUtils.verify(data, RSAUtils.getPublicKey(publicKey), sign);
        System.out.print("验签结果: " + result + "\n");


    }


}

