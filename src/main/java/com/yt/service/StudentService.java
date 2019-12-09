package com.yt.service;

import com.yt.pojo.*;
import com.yt.utils.PageDo;

import java.util.List;

/**
 * @author yt
 * @date 2019/10/13 - 16:04
 */
public interface StudentService {

    /**
     * 用当前学生ID查询出该学生已选择的选题
     *
     * @param student_id 学生ID
     * @return
     */
    StudentSelectedTopic selectHaveChosenPaperTopic(int student_id);

    /**
     * 查询出学生已选择选题的详细信息
     *
     * @param paper_topic_id 选题ID
     * @return
     */
    PaperTopic selectHaveChosenPaperTopicInformation(int paper_topic_id);

    /**
     * 利用当前学生的ID查询出该学生对应的导师的信息
     *
     * @param student_id 学生ID
     * @return
     */
    Group selectTeacherID(int student_id);

    /**
     * 查询出该teacher_id所发布的论文选题
     *
     * @param teacher_id 学生导师ID
     * @param state      论文状态（只查询出有效期内的选题）
     * @return
     */
    List<PaperTopic> selectPaperTopic(int teacher_id, int state);

    /**
     * 查询出该teacher_id所发布的论文选题并进行分页
     *
     * @param teacher_id 教师ID
     * @param state      选题状态
     * @param pageNum    起始页
     * @param pageSize   每页显示数量
     * @return
     */
    PageDo<PaperTopic> selectPaperTopicPaging(int teacher_id, int state, int pageNum, int pageSize);

    /**
     * 学生选择选题
     *
     * @param student_id     学生ID
     * @param paper_topic_id 选题ID
     * @return
     */
    int studentChoosingTopics(int student_id, int paper_topic_id);

    /**
     * 学生上传论文
     *
     * @param thesis_title    论文名称
     * @param storage_address 存放路径
     * @param upload_date     上传日期
     * @param student_id      学生ID
     * @param paper_topic_id  选题ID
     * @param student_name    用户名
     * @return
     */
    int paperUpload(String thesis_title, String storage_address, String upload_date, int student_id, String student_name, int paper_topic_id);

    /**
     * 查询该学生是否已上传过论文
     *
     * @param student_id 学生ID
     * @return
     */
    Papers papersToRepeat(int student_id);


    /**
     * 学生开题、中期报告上传
     *
     * @param file_name      文件名
     * @param upload_date    上传日期
     * @param file_type      文件类型（开题报告、中期报告）
     * @param student_id     学生ID
     * @param paper_topic_id 选题ID
     * @return
     */
    int permissionUpload(String file_name, String upload_date, String file_type, int student_id, int paper_topic_id);

    /**
     * 查询当前类型报告是否上传过
     *
     * @param student_id 学生ID
     * @param file_type  报告类型
     * @return
     */
    Presentation permissionToRepeat(int student_id, String file_type);


    /**
     * 学生查看论文成绩
     *
     * @param papers_id 学生ID
     * @return
     */
    PapersRating achievementQuery(int papers_id);
}
