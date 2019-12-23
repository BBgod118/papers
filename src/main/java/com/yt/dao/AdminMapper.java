package com.yt.dao;

import com.yt.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * 管理员DAO层接口,注解方式来操作SQL
 *
 * @author yt
 * @date 2019/10/8 - 11:30
 */
public interface AdminMapper {

    /**
     * 添加用户——user表插入
     *
     * @param user_name  用户名
     * @param user_pwd   密码
     * @param real_name  真实姓名
     * @param user_phone 电话号码
     * @return
     */
    @Insert("INSERT INTO user (user_name, user_pwd, real_name, user_phone) VALUES " +
            "(#{user_name}, #{user_pwd}, #{real_name}, #{user_phone})")
    int addingUsers(@Param("user_name") String user_name, @Param("user_pwd") String user_pwd, @Param("real_name") String real_name, @Param("user_phone") String user_phone);

    /**
     * 查询用户表中是否已有该用户名——user_name
     *
     * @param user_name 用户名
     * @return
     */
    @Select("select * from user where user_name=#{user_name}")
    User userVerification(@Param("user_name") String user_name);

    /**
     * 查询出对应用户名的用户id
     *
     * @param user_name 用户名
     * @return
     */
    @Select("select user_id from user where user_name=#{user_name}")
    int userIdQuery(@Param("user_name") String user_name);

    /**
     * 添加用户——用户信息添加（学生）
     *
     * @param grade      年级
     * @param department 系别
     * @param classes    班级
     * @param student_id 学生id
     * @return
     */
    @Insert("INSERT INTO student_information (grade, department, classes, student_id) " +
            "VALUES (#{grade}, #{department}, #{classes}, #{student_id})")
    int studentAddingUserInformation(@Param("grade") String grade, @Param("department") String department, @Param("classes") String classes, @Param("student_id") int student_id);

    /**
     * 添加用户——用户信息添加（教师）
     *
     * @param teacher_title 教师职称
     * @param teacher_id    教师id
     * @return
     */
    @Insert("INSERT INTO teacher_information (teacher_title, teacher_id) VALUES " +
            "(#{teacher_title}, #{teacher_id})")
    int teacherAddingUserInformation(@Param("teacher_title") String teacher_title, @Param("teacher_id") int teacher_id);

    /**
     * 给用户添加角色
     *
     * @param user_id 用户id
     * @param role_id 角色id
     * @return
     */
    @Insert("INSERT INTO user_role (user_id, role_id) VALUES (#{user_id}, #{role_id})")
    int addingUserRoles(@Param("user_id") int user_id, @Param("role_id") int role_id);

    /**
     * 查询该student_id是否已分组
     *
     * @param student_id
     * @return
     */
    @Select("select * from `group` where student_id = #{student_id}")
    Group groupToRepeat(@Param("student_id") int student_id);

    /**
     * 教师学生分组
     *
     * @param teacher_id 教师id
     * @param student_id 学生id
     * @return
     */
    @Insert("INSERT INTO `group`" + " (teacher_id, student_id) VALUES (#{teacher_id}," +
            "#{student_id})")
    int group(@Param("teacher_id") int teacher_id, @Param("student_id") int student_id);

    /**
     * 查询出所有学生用户
     *
     * @return
     */
    @Select("SELECT u.user_name, u.real_name, u.state, s.grade, s.department, s.classes  FROM" +
            " `user` u JOIN student_information s on u.user_id = s.student_id")
    List<AllStudentInformation> selectAllStudent();

    /**
     * 查询出所有教师用户
     *
     * @return
     */
    @Select("SELECT u.user_name, u.real_name, u.state, t.teacher_title FROM `user` u JOIN " +
            "teacher_information t on u.user_id = t.teacher_id")
    List<AllTeacherInformation> selectAllTeacher();

    /**
     * 查询出所有分组信息
     *
     * @return
     */
    @Select("SELECT * FROM `group`")
    List<Group> selectAllGroup();

    /**
     * 查询对应id的用户名和真实姓名
     *
     * @param user_id 用户ID
     * @return
     */
    @Select("SELECT user_name, real_name FROM `user` WHERE user_id =  #{user_id}")
    UserInformation selectUserInformation(@Param("user_id") int user_id);

    /**
     * 封禁用户账户
     *
     * @param user_name 用户名
     * @return
     */
    @Update("UPDATE `user` SET state = #{state} WHERE user_name = #{user_name}")
    int updateUserSate(@Param("state") int state, @Param("user_name") String user_name);

    /**
     * 删除对应学生分组
     *
     * @param student_id 学生ID
     * @return
     */
    @Delete("DELETE FROM `group` WHERE student_id = #{student_id}")
    int deleteGroup(@Param("student_id") int student_id);

    /**
     * 查询出所有权限路径
     * @return
     */
    @Select("SELECT * FROM permission")
    List<Permission> selectAllPermission();

    /**
     * 管理员添加权限功能路径
     * @param perms_name 功能说明
     * @param url   权限路径
     * @return
     */
    @Insert("INSERT INTO permission (perms_name, url) VALUES (#{perms_name}, #{url})")
    int insertPermission(@Param("perms_name") String perms_name, @Param("url") String url);

    /**
     * 查询出所有角色权限
     * @return
     */
    @Select("SELECT role_id, permission_id FROM role_permission")
    List<RolePermissionTwo> selectAllRolePermission();

    /**
     * 管理员给指定角色添加权限功能
     * @param role_id         教师ID
     * @param permission_id   权限路径ID
     * @return
     */
    @Insert("INSERT INTO role_permission (role_id, permission_id) VALUES (#{role_id}, #{permission_id})")
    int insertRolePermission(@Param("role_id") int role_id, @Param("permission_id") int permission_id);

    /**
     * 公告发布
     *
     * @param content
     * @param title
     * @param author
     * @param time
     * @return
     */
    @Insert("INSERT INTO notice (admin_id, content, title, author, time) VALUES" +
            " (#{admin_id}, #{content}, #{title}, #{author}, #{time})")
    int saveNotice(@Param("admin_id") int admin_id,@Param("content") String content,
                   @Param("title") String title, @Param("author") String author,
                   @Param("time") Date time);

    /**
     * 公告查询
     *
     * @param admin_id 管理员id
     * @return
     */
    @Select("SELECT * FROM notice WHERE admin_id= #{admin_id}")
    Notice queryNotice(@Param("admin_id") int admin_id);
}
