package com.yt.pojo;

import java.util.Date;

/**
 * 论文实体类
 *
 * @author yt
 * @date 2019/10/3 - 7:54
 */
public class Papers {
    private Integer papers_id;
    /**
     * 学生提交的关于论文的文件名称
     */
    private String thesis_title;
    /**
     * 存储地址
     */
    private String storage_address;
    /**
     * 提交日期
     */
    private Date upload_date;
    /**
     * 学生id
     */
    private Integer student_id;
    /**
     * 对应选题id
     */
    private Integer paper_topic_id;

    public Papers() {
    }

    public Papers(Integer papers_id, String thesis_title, String storage_address,
                  Date upload_date, Integer student_id, Integer paper_topic_id) {
        this.papers_id = papers_id;
        this.thesis_title = thesis_title;
        this.storage_address = storage_address;
        this.upload_date = upload_date;
        this.student_id = student_id;
        this.paper_topic_id = paper_topic_id;
    }

    public Integer getPapers_id() {
        return papers_id;
    }

    public void setPapers_id(Integer papers_id) {
        this.papers_id = papers_id;
    }

    public String getThesis_title() {
        return thesis_title;
    }

    public void setThesis_title(String thesis_title) {
        this.thesis_title = thesis_title;
    }

    public String getStorage_address() {
        return storage_address;
    }

    public void setStorage_address(String storage_address) {
        this.storage_address = storage_address;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
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
        return "Papers{" + "papers_id=" + papers_id + ", thesis_title='" + thesis_title + '\'' +
                ", " + "storage_address='" + storage_address + '\'' + ", upload_date=" + upload_date + ", " + "student_id=" + student_id + ", paper_topic_id=" + paper_topic_id + '}';
    }
}
