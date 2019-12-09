package com.yt.utils;

import com.yt.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * 获取当前用户信息类
 *
 * @author yt
 * @date 2019/9/24 - 10:37
 */
public class SubjectGetUserInformation {
    public static User getUserInformation() {
        // 从session中取出当前用户信息
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        return user;
    }

}
