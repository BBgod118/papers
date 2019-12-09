package com.yt.dao;

import com.yt.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 教师DAO层接口,注解方式来操作SQL
 *
 * @author yt
 * @date 2019/10/11 - 21:21
 */
public interface TeacherMapper {
    /**
     * 教师给自己组的学生发布论文选题并设置有效时间段
     *
     * @param paper_topic  论文选题
     * @param release_date 发布时间
     * @param closing_date 截至时间
     * @param teacher_id   教师id
     * @return
     */
    @Insert("insert into paper_topic(paper_topic,release_date,closing_date,teacher_id) " +
            "values (#{paper_topic},#{release_date},#{closing_date},#{teacher_id})")
    int thesisSelection(@Param("paper_topic") String paper_topic, @Param("release_date") String release_date, @Param("closing_date") String closing_date, @Param("teacher_id") int teacher_id);

    /**
     * 教师查看已发布的论文选题并进行倒序排序
     *
     * @param teacher_id 教师ID
     * @return
     */
    @Select("select * from paper_topic where teacher_id = #{teacher_id} order by " +
            "paper_topic_id desc")
    List<PaperTopic> selectedTopicsHaveBeenPublished(@Param("teacher_id") int teacher_id);

    /**
     * 查询出已发布论文选题的论文提交情况并进行倒序排序
     *
     * @param paper_topic_id 选题id
     * @return
     */
    @Select("select p.papers_id, p.thesis_title, p.upload_date, p.student_id, " +
            "p.student_name, pr.fraction from papers p LEFT JOIN papers_rating pr on " +
            "p.papers_id = pr.papers_id where p.paper_topic_id = #{paper_topic_id} " +
            "order by p.papers_id desc")
    List<SubmissionOfPapers> submissionOfPapers(@Param("paper_topic_id") int paper_topic_id);


    /**
     * 查询出已发布论文选题的开题或中期报告的提交情况并进行倒序排序
     *
     * @param paper_topic_id 选题ID
     * @param file_type      文件类型（开题报告、中期报告）
     * @return
     */
    @Select("select p.*, u.user_name, u.real_name  from presentation p LEFT JOIN `user` " +
            "u on p.student_id = u.user_id where paper_topic_id = #{paper_topic_id} and " +
            "file_type = #{file_type} order by presentation_id desc")
    List<PresentationTwo> statusOfSubmissionOfReports(@Param("paper_topic_id") int paper_topic_id, @Param("file_type") String file_type);

    /**
     * 更改已失效过期的论文选题状态
     *
     * @param state          状态（是否在有效时间内）
     * @param paper_topic_id 选题id
     * @return
     */
    @Update("UPDATE paper_topic SET state =  #{state} WHERE paper_topic_id = " +
            "#{paper_topic_id}")
    int changeTheStatusOfTheTopic(@Param("state") int state, @Param("paper_topic_id") int paper_topic_id);


    /**
     * 在教师进行评分时，先检查该学生论文是否已进行评分，避免重复评分
     *
     * @param papers_id 论文表中论文文件ID
     * @return
     */
    @Select("SELECT * FROM papers_rating where papers_id = #{papers_id}")
    PapersRating toRepeatScore(@Param("papers_id") int papers_id);

    /**
     * 教师对学生提交的论文进行评分，将评分保存
     *
     * @param fraction  分数
     * @param papers_id 对应论文文件ID
     * @return
     */
    @Insert("insert into papers_rating (fraction,papers_id) value (#{fraction}," +
            "#{papers_id})")
    int paperScoring(@Param("fraction") int fraction, @Param("papers_id") int papers_id);

    /**
     * 更新评分
     *
     * @param newFraction 新成绩分数
     * @param papers_id   对应论文文件ID
     * @return
     */
    @Update("UPDATE papers_rating SET fraction = #{newFraction} WHERE papers_id =" +
            " #{papers_id}")
    int updateScore(@Param("newFraction") int newFraction, @Param("papers_id") int papers_id);

    /**
     * 利用教师ID在分组表中查询出该教师所有学生的ID
     *
     * @param teacher_id 教师ID
     * @return
     */
    @Select("SELECT student_id FROM `group` WHERE teacher_id = #{teacher_id}")
    List<Integer> studentIdSet(@Param("teacher_id") int teacher_id);

    /**
     * 查询出对应student_id的学生信息
     *
     * @param student_id 学生id
     * @return
     */
    @Select("SELECT * FROM `user` u LEFT JOIN student_information s on u.user_id = " +
            "s.student_id WHERE u.user_id = #{student_id}")
    StudentInformation viewingStudentInformationInGroup(@Param("student_id") int student_id);

    /**
     * 将指定学生的开题或中期报告删除
     * @param student_id  学生ID
     * @param file_type    报告类型
     * @return
     */
    @Delete("DELETE FROM presentation WHERE presentation.student_id = #{student_id} and" +
            " presentation.file_type = #{file_type}")
    int deletePresentation(@Param("student_id") int student_id, @Param("file_type") String file_type);

    /**
     * 将指定学生的论文删除
     * @param student_id 学生ID
     * @return
     */
    @Delete("DELETE FROM papers WHERE papers.student_id = #{student_id}")
    int deletePapers(@Param("student_id") int student_id);

    /**
     * 插入论文打回信息
     * @param student_id 学生ID
     * @param file_type  文件类型
     * @return
     */
    @Insert("INSERT into papers_back (student_id, file_type) VALUES (#{student_id},#{file_type})")
    int insertCallBackTheRecord(@Param("student_id") int student_id, @Param("file_type") String file_type);

    /**
     * 查询出指定学生的论文打回信息
     * @param student_id  学生ID
     * @return
     */
    @Select("SELECT file_type FROM papers_back WHERE student_id = #{student_id}")
    List<String> selectCallBackTheRecord(@Param("student_id") int student_id);
}
