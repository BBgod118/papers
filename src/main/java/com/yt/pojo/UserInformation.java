package com.yt.pojo;

/**
 * 用户用户名、真实姓名实体类
 *
 * @author yt
 * @date 2019/11/6 - 21:46
 */
public class UserInformation {
    private String user_name;
    private String real_name;

    public UserInformation() {
    }

    public UserInformation(String user_name, String real_name) {
        this.user_name = user_name;
        this.real_name = real_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    @Override
    public String toString() {
        return "UserInformation{" + "user_name='" + user_name + '\'' + ", real_name='" + real_name + '\'' + '}';
    }
}
