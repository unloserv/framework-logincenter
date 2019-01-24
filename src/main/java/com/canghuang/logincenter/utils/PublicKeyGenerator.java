package com.canghuang.logincenter.utils;

/**
 * @author cs
 * @date 2019/1/23
 * @description
 */
public class PublicKeyGenerator {

    public static String publicKey = EncryptUtil.md5Encode(System.currentTimeMillis() + "");

    public static void updatePublicKey() {
        publicKey = EncryptUtil.md5Encode(publicKey);
    }

}
