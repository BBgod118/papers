package com.yt.pojo;

/**
 * 对应选题论文提交情况实体类
 *
 * @author yt
 * @date 2019/10/12 - 0:12
 */
public class SubmissionOfPapers {
    private Integer papers_id;
    /**
     * 学生提交的关于论文的文件名称
     */
    private String thesis_title;
    /**
     * 提交日期
     */
    private String upload_date;
    /**
     * 学生id
     */
    private Integer student_id;
    /**
     * 真实姓名
     */
    private String student_name;
    /**
     * 论文得分
     */
    private Integer fraction;

    public SubmissionOfPapers() {
    }

    public SubmissionOfPapers(Integer papers_id, String thesis_title, String upload_date, Integer student_id,
                              String student_name, Integer fraction) {
        this.papers_id = papers_id;
        this.thesis_title = thesis_title;
        this.upload_date = upload_date;
        this.student_id = student_id;
        this.student_name = student_name;
        this.fraction = fraction;
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

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Integer getFraction() {
        return fraction;
    }

    public void setFraction(Integer fraction) {
        this.fraction = fraction;
    }

    @Override
    public String toString() {
        return "SubmissionOfPapers{" + "papers_id=" + papers_id + ", thesis_title='" + thesis_title + '\'' + ", " +
                "upload_date='" + upload_date + '\'' + ", student_id=" + student_id + ", student_name='" + student_name + '\'' + ", fraction=" + fraction + '}';
    }
}
