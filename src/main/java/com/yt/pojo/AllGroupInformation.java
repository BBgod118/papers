package com.yt.pojo;

/**
 * 系统所有分组信息
 *
 * @author yt
 * @date 2019/11/6 - 21:41
 */
public class AllGroupInformation {
    /**
     * 教师工号
     */
    private String teacherUserName;
    /**
     * 教师真实姓名
     */
    private String teacherRealName;
    /**
     * 学生工号
     */
    private String studentUserName;
    /**
     * 学生真实姓名
     */
    private String studentRealName;

    public AllGroupInformation() {
    }

    public AllGroupInformation(String teacherUserName, String teacherRealName, String studentUserName, String studentRealName) {
        this.teacherUserName = teacherUserName;
        this.teacherRealName = teacherRealName;
        this.studentUserName = studentUserName;
        this.studentRealName = studentRealName;
    }

    public String getTeacherUserName() {
        return teacherUserName;
    }

    public void setTeacherUserName(String teacherUserName) {
        this.teacherUserName = teacherUserName;
    }

    public String getTeacherRealName() {
        return teacherRealName;
    }

    public void setTeacherRealName(String teacherRealName) {
        this.teacherRealName = teacherRealName;
    }

    public String getStudentUserName() {
        return studentUserName;
    }

    public void setStudentUserName(String studentUserName) {
        this.studentUserName = studentUserName;
    }

    public String getStudentRealName() {
        return studentRealName;
    }

    public void setStudentRealName(String studentRealName) {
        this.studentRealName = studentRealName;
    }

    @Override
    public String toString() {
        return "AllGroupInformation{" + "teacherUserName='" + teacherUserName + '\'' + ", teacherRealName='" + teacherRealName + '\'' + ", studentUserName='" + studentUserName + '\'' + ", studentRealName='" + studentRealName + '\'' + '}';
    }
}