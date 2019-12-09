package com.yt.dao;

import com.yt.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 学生DAO层接口,注解方式来操作SQL
 *
 * @author yt
 * @date 2019/10/13 - 14:35
 */
public interface StudentMapper {

    /**
     * 用当前学生ID查询出该学生已选择的选题
     *
     * @param student_id 学生ID
     * @return
     */
    @Select("SELECT * FROM student_selected_topic WHERE student_id = #{student_id}")
    StudentSelectedTopic selectHaveChosenPaperTopic(@Param("student_id") int student_id);

    /**
     * 查询出学生已选择选题的详细信息
     *
     * @param paper_topic_id 选题ID
     * @return
     */
    @Select("SELECT * FROM paper_topic WHERE paper_topic_id =  #{paper_topic_id}")
    PaperTopic selectHaveChosenPaperTopicInformation(@Param("paper_topic_id") int paper_topic_id);

    /**
     * 利用当前学生的ID查询出该学生对应的导师的信息
     *
     * @param student_id 学生ID
     * @return
     */
    @Select("select * from `group` where student_id=#{student_id}")
    Group selectTeacherID(@Param("student_id") int student_id);

    /**
     * 查询出该teacher_id所发布的论文选题
     *
     * @param teacher_id 学生导师ID
     * @param state      论文状态（只查询出有效期内的选题）
     * @return
     */
    @Select("select * from paper_topic where teacher_id=#{teacher_id} and state = " +
            "#{state} order by paper_topic_id desc")
    List<PaperTopic> selectPaperTopic(@Param("teacher_id") int teacher_id, @Param("state") int state);

    /**
     * 学生选择选题
     *
     * @param student_id     学生ID
     * @param paper_topic_id 选题ID
     * @return
     */
    @Insert("INSERT INTO student_selected_topic (student_id, paper_topic_id) VALUES " +
            "(#{student_id}, #{paper_topic_id})")
    int studentChoosingTopics(@Param("student_id") int student_id, @Param("paper_topic_id") int paper_topic_id);

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
    @Insert("INSERT INTO papers (thesis_title,storage_address,upload_date,student_id,student_name, paper_topic_id) VALUES (#{thesis_title}, #{storage_address},#{upload_date}, #{student_id},#{student_name}, #{paper_topic_id})")
    int paperUpload(@Param("thesis_title") String thesis_title, @Param("storage_address") String storage_address, @Param("upload_date") String upload_date, @Param("student_id") int student_id, @Param("student_name") String student_name, @Param("paper_topic_id") int paper_topic_id);

    /**
     * 查询该学生是否已上传过论文
     *
     * @param student_id 学生ID
     * @return
     */
    @Select("select * from papers WHERE student_id = #{student_id}")
    Papers papersToRepeat(@Param("student_id") int student_id);

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
    @Insert("INSERT INTO presentation (file_name,upload_date,file_type," +
            "student_id,paper_topic_id) VALUES (#{file_name},#{upload_date}," +
            "#{file_type},#{student_id}, #{paper_topic_id})")
    int permissionUpload(@Param("file_name") String file_name, @Param("upload_date") String upload_date, @Param("file_type") String file_type, @Param("student_id") int student_id, @Param("paper_topic_id") int paper_topic_id);

    /**
     * 查询当前类型报告是否上传过
     *
     * @param student_id 学生ID
     * @param file_type  报告类型
     * @return
     */
    @Select("SELECT * FROM presentation WHERE student_id = #{student_id} and file_type = " +
            "#{file_type}")
    Presentation permissionToRepeat(@Param("student_id") int student_id, @Param("file_type") String file_type);

    /**
     * 学生查看论文成绩
     *
     * @param papers_id 论文文件ID
     * @return
     */
    @Select("SELECT p.thesis_title, p.upload_date, pr.fraction FROM papers_rating pr" +
            " LEFT JOIN papers p on pr.papers_id = p.papers_id WHERE pr.papers_id " +
            "= #{papers_id}")
    PapersRating achievementQuery(@Param("papers_id") int papers_id);
}
