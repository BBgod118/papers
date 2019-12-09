package com.yt.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * QQ邮箱验证码工具类
 *
 * @author yt
 * @date 2019/8/2 - 16:21
 */
public class EmailUtil {
    /**
     * 用户绑定邮箱验证码发送
     */
    public static void sendAuthCodeEmail(String email, String authCode) {
        try {
            SimpleEmail mail = new SimpleEmail();
            //发送邮件的服务器
            mail.setHostName("smtp.qq.com");
            //刚刚记录的授权码，是开启SMTP的密码
            mail.setAuthentication("1435533812@qq.com", "ggcfxnwngekthadf");
            //发送邮件的邮箱和发件人
            mail.setFrom("1435533812@qq.com", "时光荏苒");
            //使用安全链接
            mail.setSSLOnConnect(true);
            //接收的邮箱
            mail.addTo(email);
            //设置邮件的主题
            mail.setSubject("邮箱绑定");
            //设置邮件的内容
            mail.setMsg("你好!\n 您的验证码为:" + authCode + "\n" + "(有效期为3分钟)");
            mail.send();//发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户密码找回验证码发送
     */
    public static void sendAuthCodeEmailTwo(String email, String authCode) {
        try {
            SimpleEmail mail = new SimpleEmail();
            //发送邮件的服务器
            mail.setHostName("smtp.qq.com");
            //刚刚记录的授权码，是开启SMTP的密码
            mail.setAuthentication("1435533812@qq.com", "ggcfxnwngekthadf");
            //发送邮件的邮箱和发件人
            mail.setFrom("1435533812@qq.com", "时光荏苒");
            //使用安全链接
            mail.setSSLOnConnect(true);
            //接收的邮箱
            mail.addTo(email);
            //设置邮件的主题
            mail.setSubject("密码找回");
            //设置邮件的内容
            mail.setMsg("你好!\n 您正在进行密码找回，验证码为:" + authCode + "\n" + "(有效期为3分钟)");
            mail.send();//发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
