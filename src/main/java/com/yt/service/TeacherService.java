package com.yt.service;

import com.yt.pojo.*;
import com.yt.utils.PageDo;

import java.util.List;

/**
 * @author yt
 * @date 2019/10/11 - 21:22
 */
public interface TeacherService {
    /**
     * 教师给自己组的学生发布论文选题并设置有效时间段
     *
     * @param paper_topic  论文选题
     * @param release_date 发布时间
     * @param closing_date 截至时间
     * @param teacher_id   教师id
     * @return
     */
    int thesisSelection(String paper_topic, String release_date, String closing_date, int teacher_id);

    /**
     * 教师查看已发布的论文选题并进行倒序排序
     *
     * @param teacher_id 教师ID
     * @return
     */
    List<PaperTopic> selectedTopicsHaveBeenPublished(int teacher_id);

    /**
     * 教师查看已发布的论文选题并进行分页处理排序
     *
     * @param teacher_id 教师ID
     * @param pageNum    当前显示页面
     * @param pageSize   每页显示数量
     * @return
     */
    PageDo<PaperTopic> selectedTopicsHaveBeenPublishedTwo(int teacher_id, int pageNum, int pageSize);

    /**
     * 查询出已发布论文选题的论文提交情况并进行倒序排序
     *
     * @param paper_topic_id 选题id
     * @return
     */
    List<SubmissionOfPapers> submissionOfPapers(int paper_topic_id);


    /**
     * 查询出已发布论文选题的论文提交情况并进行倒序排序并分页
     *
     * @param paper_topic_id 选题ID
     * @param pageNum        起始页
     * @param pageSize       每页显示数量
     * @return
     */
    PageDo<SubmissionOfPapers> submissionOfPapersTwo(int paper_topic_id, int pageNum, int pageSize);

    /**
     * 查询出已发布论文选题的开题或中期报告的提交情况并进行倒序排序
     *
     * @param paper_topic_id 选题ID
     * @param file_type      文件类型（开题报告、中期报告）
     * @return
     */
    List<PresentationTwo> statusOfSubmissionOfReports(int paper_topic_id, String file_type);


    /**
     * 查询出已发布论文选题的开题或中期报告的提交情况并进行倒序排序和分页
     *
     * @param paper_topic_id 选题ID
     * @param file_type      文件类型（开题报告、中期报告）
     * @param pageNum        起始页
     * @param pageSize       每页显示数量
     * @return
     */
    PageDo<PresentationTwo> statusOfSubmissionOfReportsTwo(int paper_topic_id, String file_type, int pageNum, int pageSize);

    /**
     * 更改已失效过期的论文选题状态
     *
     * @param state          状态（是否在有效时间内）
     * @param paper_topic_id 选题id
     * @return
     */
    int changeTheStatusOfTheTopic(int state, int paper_topic_id);


    /**
     * 在教师进行评分时，先检查该学生论文是否已进行评分，避免重复评分
     *
     * @param papers_id 论文表中论文文件ID
     * @return
     */
    PapersRating toRepeatScore(int papers_id);

    /**
     * 教师对学生提交的论文进行评分，将评分保存
     *
     * @param fraction  分数
     * @param papers_id 对应论文文件ID
     * @return
     */
    int paperScoring(int fraction, int papers_id);

    /**
     * 更新评分
     *
     * @param newFraction 新成绩分数
     * @param papers_id   对应论文文件ID
     * @return
     */
    int updateScore(int newFraction, int papers_id);

    /**
     * 利用教师ID在分组表中查询出该教师所有学生的ID
     *
     * @param teacher_id 教师ID
     * @return
     */
    List<Integer> studentIdSet(int teacher_id);

    /**
     * 查询出对应student_id的学生信息
     *
     * @param student_id 学生id
     * @return
     */
    StudentInformation viewingStudentInformationInGroup(int student_id);

    /**
     * 查询出对应student_id的学生信息并进行分页
     *
     * @param teacher_id 教师id
     * @param pageNum    起始页
     * @param pageSize   每页显示数量
     * @return
     */
    PageDo<StudentInformation> viewingStudentInformationInGroupTwo(int teacher_id, int pageNum, int pageSize);

    /**
     * 将指定学生的开题或中期报告删除
     * @param student_id  学生ID
     * @param file_type    报告类型
     * @return
     */
    int deletePresentation(int student_id, String file_type);

    /**
     * 将指定学生的论文删除
     * @param student_id 学生ID
     * @return
     */
    int deletePapers(int student_id, int papers_id);

    /**
     * 插入论文打回信息
     * @param student_id 学生ID
     * @param file_type  文件类型
     * @return
     */
    int insertCallBackTheRecord(int student_id, String file_type);

    /**
     * 查询出指定学生的论文打回信息
     * @param student_id  学生ID
     * @return
     */
    List<String> selectCallBackTheRecord(int student_id);
}
