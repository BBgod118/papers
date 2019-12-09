package com.yt.pojo;

/**
 * 学生选择选题实体类
 *
 * @author yt
 * @date 2019/10/2 - 18:35
 */
public class StudentSelectedTopic {
    private Integer student_selected_topic_id;
    /**
     * 学生id
     */
    private Integer student_id;
    /**
     * 选题id
     */
    private Integer paper_topic_id;

    public StudentSelectedTopic() {
    }

    public StudentSelectedTopic(Integer student_selected_topic_id, Integer student_id, Integer paper_topic_id) {
        this.student_selected_topic_id = student_selected_topic_id;
        this.student_id = student_id;
        this.paper_topic_id = paper_topic_id;
    }

    public Integer getStudent_selected_topic_id() {
        return student_selected_topic_id;
    }

    public void setStudent_selected_topic_id(Integer student_selected_topic_id) {
        this.student_selected_topic_id = student_selected_topic_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getPaper_topic_id() {
        return paper_topic_id;
    }

    public void setPaper_topic_id(Integer paper_topic_id) {
        this.paper_topic_id = paper_topic_id;
    }

    @Override
    public String toString() {
        return "StudentSelectedTopic{" + "student_selected_topic_id=" + student_selected_topic_id + ", student_id=" + student_id + ", paper_topic_id=" + paper_topic_id + '}';
    }
}
