package com.cc.security;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
    /**
     * 使用Base64加密字符串
     *
     * @return 加密之后的字符串
     * @throws Exception
     */
    public static String encode(byte[] data) {
        Base64 base64 = new Base64();
        String encodedData = base64.encodeAsString(data);
        return encodedData;
    }

    /**
     * 使用Base64解密
     *
     * @return 解密之后的字符串
     * @throws Exception
     */
    public static byte[] decode(String data) {
        Base64 base64 = new Base64();
        byte[] decodedData = base64.decodeBase64(data);
        return decodedData;
    }
}