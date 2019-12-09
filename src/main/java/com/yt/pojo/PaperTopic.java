package com.yt.pojo;

/**
 * 论文选题实体类
 *
 * @author yt
 * @date 2019/10/2 - 18:08
 */
public class PaperTopic {
    private Integer paper_topic_id;
    /**
     * 论文选题
     */
    private String paper_topic;
    /**
     * 发布日期
     */
    private String release_date;
    /**
     * 截至日期
     */
    private String closing_date;
    /**
     * 教师id
     */
    private Integer teacher_id;
    /**
     * 选题状态
     */
    private Integer state;

    public PaperTopic() {
    }

    public PaperTopic(Integer paper_topic_id, String paper_topic, String release_date,
                      String closing_date, Integer teacher_id, Integer state) {
        this.paper_topic_id = paper_topic_id;
        this.paper_topic = paper_topic;
        this.release_date = release_date;
        this.closing_date = closing_date;
        this.teacher_id = teacher_id;
        this.state = state;
    }

    public Integer getPaper_topic_id() {
        return paper_topic_id;
    }

    public void setPaper_topic_id(Integer paper_topic_id) {
        this.paper_topic_id = paper_topic_id;
    }

    public String getPaper_topic() {
        return paper_topic;
    }

    public void setPaper_topic(String paper_topic) {
        this.paper_topic = paper_topic;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getClosing_date() {
        return closing_date;
    }

    public void setClosing_date(String closing_date) {
        this.closing_date = closing_date;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PaperTopic{" + "paper_topic_id=" + paper_topic_id + ", paper_topic='" + paper_topic + '\'' + ", " +
                "release_date='" + release_date + '\'' + ", closing_date='" + closing_date + '\'' + ", teacher_id=" + teacher_id + ", state=" + state + '}';
    }
}
