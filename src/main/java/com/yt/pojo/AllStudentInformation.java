package com.yt.pojo;

/**
 * 系统所有学生信息类
 *
 * @author yt
 * @date 2019/11/6 - 20:56
 */
public class AllStudentInformation {

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

    public AllStudentInformation() {
    }

    public AllStudentInformation(String user_name, String real_name, String state, String grade, String department, String classes) {
        this.user_name = user_name;
        this.real_name = real_name;
        this.state = state;
        this.grade = grade;
        this.department = department;
        this.classes = classes;
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
        return "AllStudentInformation{" + "user_name='" + user_name + '\'' + ", real_name='" + real_name + '\'' + ", state='" + state + '\'' + ", grade='" + grade + '\'' + ", department='" + department + '\'' + ", classes='" + classes + '\'' + '}';
    }
}
