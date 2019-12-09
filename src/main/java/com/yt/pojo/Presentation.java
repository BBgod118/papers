package com.yt.pojo;

/**
 * 开题、中期报告实体类
 *
 * @author yt
 * @date 2019/10/11 - 23:31
 */
public class Presentation {
    private Integer presentation_id;
    /**
     * 报告文件名
     */
    private String file_name;
    /**
     * 提交日期
     */
    private String upload_date;
    /**
     * 提交文件的类型（开题报告、中期报告）
     */
    private String file_type;
    /**
     * 学生id
     */
    private Integer student_id;
    /**
     * 对应选题id
     */
    private Integer paper_topic_id;

    public Presentation() {
    }

    public Presentation(Integer presentation_id, String file_name, String upload_date,
                        String file_type, Integer student_id, Integer paper_topic_id) {
        this.presentation_id = presentation_id;
        this.file_name = file_name;
        this.upload_date = upload_date;
        this.file_type = file_type;
        this.student_id = student_id;
        this.paper_topic_id = paper_topic_id;
    }

    public Integer getPresentation_id() {
        return presentation_id;
    }

    public void setPresentation_id(Integer presentation_id) {
        this.presentation_id = presentation_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
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
        return "Presentation{" + "presentation_id=" + presentation_id + ", file_name='" + file_name + '\'' + ", " +
                "upload_date='" + upload_date + '\'' + ", file_type='" + file_type + '\'' + ", student_id=" + student_id + ", paper_topic_id=" + paper_topic_id + '}';
    }
}
