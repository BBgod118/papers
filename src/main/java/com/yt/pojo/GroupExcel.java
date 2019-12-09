package com.yt.pojo;

/**
 * 管理员进行excel表批量分组实体类
 *
 * @author yt
 * @date 2019/10/11 - 18:57
 */
public class GroupExcel {
    /**
     * 教师用户名
     */
    private String teacher_name;
    /**
     * 学生用户名
     */
    private String student_name;

    public GroupExcel() {
    }

    public GroupExcel(String teacher_name, String student_name) {
        this.teacher_name = teacher_name;
        this.student_name = student_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    @Override
    public String toString() {
        return "GroupExcel{" + "teacher_name='" + teacher_name + '\'' + ", student_name='" + student_name + '\'' + '}';
    }
}
