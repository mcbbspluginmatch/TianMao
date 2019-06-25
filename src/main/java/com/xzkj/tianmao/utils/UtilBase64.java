package com.xzkj.tianmao.utils;

import java.io.IOException;

public class UtilBase64 {
    public static String encode(byte[] content) {
        return new sun.misc.BASE64Encoder().encode(content);
    }
    public static byte[] decode(String source) {
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            return decoder.decodeBuffer(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
