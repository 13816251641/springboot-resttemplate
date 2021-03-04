package com.lujieni.resttemplate.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

/**
 * @Package: com.lujieni.resttemplate.util
 * @ClassName: MD5Utils
 * @Author: lujieni
 * @Description:
 * @Date: 2021-03-04 14:44
 * @Version: 1.0
 */
public class MD5Utils {
    /**
     * 签名字符串,MD5加密是不可逆的
     * @param text 需要签名的字符串
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Wrong Charset! Your Charset Is:" + charset);
        }
    }

}