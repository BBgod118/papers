package com.yt.pojo;

/**
 * 教师信息实体类
 *
 * @author yt
 * @date 2019/10/8 - 14:50
 */
public class TeacherInformation {
    private Integer user_id;
    /**
     * 账户名
     */
    private String user_name;
    private String user_pwd;
    /**
     * 真实姓名
     */
    private String real_name;
    /**
     * 用户邮箱
     */
    private String user_email;
    /**
     * 用户电话号码
     */
    private String user_phone;
    /**
     * 教师职称
     */
    private String teacher_title;

    public TeacherInformation() {
    }

    public TeacherInformation(String user_name, String real_name, String user_phone, String teacher_title) {
        this.user_name = user_name;
        this.real_name = real_name;
        this.user_phone = user_phone;
        this.teacher_title = teacher_title;
    }

    public TeacherInformation(Integer user_id, String user_name, String user_pwd, String real_name, String user_email
            , String user_phone, String teacher_title) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.real_name = real_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.teacher_title = teacher_title;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getTeacher_title() {
        return teacher_title;
    }

    public void setTeacher_title(String teacher_title) {
        this.teacher_title = teacher_title;
    }

    @Override
    public String toString() {
        return "TeacherInformation{" + "user_id=" + user_id + ", user_name='" + user_name + '\'' + ", user_pwd='" + user_pwd + '\'' + ", real_name='" + real_name + '\'' + ", user_email='" + user_email + '\'' + ", user_phone='" + user_phone + '\'' + ", teacher_title='" + teacher_title + '\'' + '}';
    }
}
