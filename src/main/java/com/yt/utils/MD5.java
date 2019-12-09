package com.yt.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5加密工具类
 *
 * @author yt
 * @date 2019/10/5 - 11:35
 */
public class MD5 {
    public static String cipher_Encryption(String user_name, String user_pwd) {
        //盐值
        Object salt = ByteSource.Util.bytes(user_name);
        Object result = new SimpleHash("MD5", user_pwd, salt, 1024);
        String pwd = result.toString();
        return pwd;
    }
}
