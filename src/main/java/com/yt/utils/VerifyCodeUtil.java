package com.yt.utils;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 随机生成5位验证码工具类
 * @author yt
 * @date 2019/8/2 - 16:40
 */
public class VerifyCodeUtil {
    /**
     * 随机验证码，由于数字 1 、 0 和字母 O 、l 有时分不清楚，所以，没有数字 1 、 0
     */
    public static String achieveCode() {
        String[] beforeShuffle= new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        //将数组转换为集合
        List list = Arrays.asList(beforeShuffle);
        //打乱集合顺序
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            //将集合中的内容添加到sb
            sb.append(list.get(i));
        }
        //截取字符串第4到8
        return sb.toString().substring(3, 8);
    }

    public static void main(String[] args) {
        String a = achieveCode();
        System.out.println(a);
        EmailUtil.sendAuthCodeEmail("1435533812@qq.com",a);
    }
}
