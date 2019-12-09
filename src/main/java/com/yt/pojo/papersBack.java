package com.yt.pojo;

/**
 * 论文、报告打回实体类
 *
 * @author yt
 * @date 2019/11/7 - 14:14
 */
public class papersBack {
    private Integer paper_back_id;

    private String student_name;

    private String file_type;

    public papersBack() {
    }

    public papersBack(Integer paper_back_id, String student_name, String file_type) {
        this.paper_back_id = paper_back_id;
        this.student_name = student_name;
        this.file_type = file_type;
    }

    public Integer getPaper_back_id() {
        return paper_back_id;
    }

    public void setPaper_back_id(Integer paper_back_id) {
        this.paper_back_id = paper_back_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    @Override
    public String toString() {
        return "papersBack{" + "paper_back_id=" + paper_back_id + ", student_name='" + student_name + '\'' + ", file_type='" + file_type + '\'' + '}';
    }
}
