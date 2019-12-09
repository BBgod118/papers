package com.yt.pojo;

/**
 * 系统所有教师信息类
 * @author yt
 * @date 2019/11/6 - 21:00
 */
public class AllTeacherInformation {
    private String user_name;
    /**
     * 真实姓名
     */
    private String real_name;
    /**
     * 用户状态
     */
    private String state;
    /**
     * 教师职称
     */
    private String teacher_title;

    public AllTeacherInformation() {
    }

    public AllTeacherInformation(String user_name, String real_name, String state, String teacher_title) {
        this.user_name = user_name;
        this.real_name = real_name;
        this.state = state;
        this.teacher_title = teacher_title;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTeacher_title() {
        return teacher_title;
    }

    public void setTeacher_title(String teacher_title) {
        this.teacher_title = teacher_title;
    }

    @Override
    public String toString() {
        return "AllTeacherInformation{" + "user_name='" + user_name + '\'' + ", real_name='" + real_name + '\'' + ", state='" + state + '\'' + ", teacher_title='" + teacher_title + '\'' + '}';
    }
}
