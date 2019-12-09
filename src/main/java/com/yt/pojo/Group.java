package com.yt.pojo;

/**
 * 学生分组实体类
 *
 * @author yt
 * @date 2019/10/2 - 18:06
 */
public class Group {
    private Integer group_id;
    /**
     * 教师id
     */
    private Integer teacher_id;
    /**
     * 学生id
     */
    private Integer student_id;

    public Group() {
    }


    public Group(Integer group_id, Integer teacher_id, Integer student_id) {
        this.group_id = group_id;
        this.teacher_id = teacher_id;
        this.student_id = student_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "Group{" + "group_id=" + group_id + ", teacher_id=" + teacher_id + ", student_id=" + student_id + '}';
    }
}
