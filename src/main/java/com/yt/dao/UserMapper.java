package com.yt.dao;

import com.yt.pojo.StudentInformation;
import com.yt.pojo.TeacherInformation;
import com.yt.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户DAO层接口,注解方式来操作SQL
 *
 * @author yt
 * @date 2019/7/17 - 20:56
 */
public interface UserMapper {

    /**
     * 用户信息查询
     *
     * @param user_name 用户名
     * @return
     */
    @Select("select * from user where user_name=#{user_name}")
    User userInformationQuery(@Param("user_name") String user_name);

    /**
     * 通过user_id在用户角色表中查询出当前用户所有的角色
     *
     * @param user_id
     * @return
     */
    @Select("SELECT role_id FROM user_role WHERE user_id = #{user_id}")
    Integer getUserRoleId(Integer user_id);

    /**
     * 通过user_id在用户角色表中查询出当前用户所有的角色
     *
     * @param user_id
     * @return
     */
    @Select("SELECT role_id FROM user_role WHERE user_id = #{user_id}")
    List<Integer> getUserRoleIds(@Param("user_id") Integer user_id);

    /**
     * 查询出对应user_name的信息——学生
     *
     * @param user_name 用户名
     * @return
     */
    @Select("SELECT * FROM `user` u LEFT JOIN student_information s on u.user_id = " +
            "s.student_id WHERE u.user_name = #{user_name}")
    StudentInformation studentViewInformation(@Param("user_name") String user_name);

    /**
     * 查询出对应user_name的信息——教师
     *
     * @param user_name 用户名
     * @return
     */
    @Select("SELECT * FROM `user` u LEFT JOIN teacher_information t on u.user_id = " +
            "t.teacher_id WHERE u.user_name = #{user_name}")
    TeacherInformation teacherViewInformation(@Param("user_name") String user_name);

    /**
     * 用户密码修改验证，查询出对应用户的旧密码，用于验证用户输入的密码与用户的旧密码是否一致
     *
     * @param user_name 用户名
     * @return 旧密码
     */
    @Select("select user_pwd from user where user_name=#{user_name}")
    String passwordValidation(@Param("user_name") String user_name);

    /**
     * 用户密码修改——当用户输入的旧密码正确时，引导用户进行密码修改
     *
     * @param user_pwd  新密码
     * @param user_name 用户名
     * @return
     */
    @Update("update user set user_pwd =#{user_pwd} where user_name=#{user_name}")
    int changePassword(@Param("user_pwd") String user_pwd, @Param("user_name") String user_name);

    /**
     * 验证邮箱地址是否已被绑定
     *
     * @param user_email 邮箱地址
     * @return
     */
    @Select("select user_name from user where user_email=#{user_email}")
    String emailDuplication(@Param("user_email") String user_email);

    /**
     * 邮箱绑定——当用户要绑定的邮箱地址通过验证之后则引导用户进行邮箱绑定
     *
     * @param user_email 邮箱地址
     * @param user_name  用户账号
     * @return
     */
    @Update("update user set user_email =#{user_email} where user_name=#{user_name}")
    int mailboxBinding(@Param("user_email") String user_email, @Param("user_name") String user_name);

    /**
     * 用户使用邮箱进行密码找回——当用户输入邮箱地址后，先验证该邮箱是否绑定，若该邮箱已绑定，
     * 则发送验证码到用户的邮箱，当用户输入正确的验证码之后引导用户进行密码重置
     *
     * @param user_pwd   新密码
     * @param user_email 邮箱地址
     * @return
     */
    @Update("update user set user_pwd =#{user_pwd} where user_email=#{user_email}")
    int resetPassword(@Param("user_pwd") String user_pwd, @Param("user_email") String user_email);
}
