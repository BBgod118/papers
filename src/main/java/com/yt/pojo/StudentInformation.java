package com.yt.pojo;

/**
 * 学生信息实体类
 *
 * @author yt
 * @date 2019/10/3 - 8:20
 */
public class StudentInformation {
    private Integer user_id;
    /**
     * 账户名（学号）
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
     * 年级
     */
    private String grade;
    /**
     * 系别
     */
    private String department;
    /**
     * 班级
     */
    private String classes;

    public StudentInformation() {
    }

    public StudentInformation(String user_name, String real_name, String user_phone, String grade, String department,
                              String classes) {
        this.user_name = user_name;
        this.real_name = real_name;
        this.user_phone = user_phone;
        this.grade = grade;
        this.department = department;
        this.classes = classes;
    }

    public StudentInformation(Integer user_id, String user_name, String user_pwd, String real_name, String user_email
            , String user_phone, String grade, String department, String classes) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.real_name = real_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.grade = grade;
        this.department = department;
        this.classes = classes;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "StudentInformation{" + "user_id=" + user_id + ", user_name='" + user_name + '\'' + ", user_pwd='" + user_pwd + '\'' + ", real_name='" + real_name + '\'' + ", user_email='" + user_email + '\'' + ", user_phone='" + user_phone + '\'' + ", grade='" + grade + '\'' + ", department='" + department + '\'' + ", classes='" + classes + '\'' + '}';
    }
}
