package com.yt.service;

import com.yt.pojo.RolePermission;
import com.yt.pojo.StudentInformation;
import com.yt.pojo.TeacherInformation;
import com.yt.pojo.User;
import java.util.List;

/**
 * @author yt
 * @date 2019/10/3 - 9:03
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param user_name 用户名
     * @param user_pwd  密码
     * @return
     */
    User userLogin(String user_name, String user_pwd);

    /**
     * 用户信息查询
     *
     * @param user_name 用户名
     * @return
     */
    User userInformationQuery(String user_name);


    /**
     * 通过user_id在用户角色表中查询出当前用户所有的角色
     *
     * @param user_id
     * @return
     */
    Integer getUserRoleId(Integer user_id);

    /**
     * 通过user_id在用户角色表中查询出当前用户所有的角色
     *
     * @param user_id
     * @return
     */
    List<Integer> getUserRoleIds(Integer user_id);

    /**
     * 查询出数据库中的各个角色可以访问哪些权限地址
     *
     * @return
     */
    List<RolePermission> getPolePermission();

    /**
     * 查询出对应user_name的信息——学生
     *
     * @param user_name 用户名
     * @return
     */
    StudentInformation studentViewInformation(String user_name);

    /**
     * 查询出对应user_name的信息——教师
     *
     * @param user_name 用户名
     * @return
     */
    TeacherInformation teacherViewInformation(String user_name);

    /**
     * 用户密码修改验证，查询出对应用户的旧密码，用于验证用户输入的密码与用户的旧密码是否一致
     *
     * @param user_name 用户名
     * @return 旧密码
     */
    String passwordValidation(String user_name);

    /**
     * 用户密码修改——当用户输入的旧密码正确时，引导用户进行密码修改
     *
     * @param user_pwd  新密码
     * @param user_name 用户名
     * @return
     */
    int changePassword(String user_pwd, String user_name);

    /**
     * 验证邮箱地址是否已被绑定
     *
     * @param user_email 邮箱地址
     * @return
     */
    String emailDuplication(String user_email);

    /**
     * 邮箱绑定——当用户要绑定的邮箱地址通过验证之后则引导用户进行邮箱绑定
     *
     * @param user_email 邮箱地址
     * @param user_name  用户账号
     * @return
     */
    int mailboxBinding(String user_email, String user_name);

    /**
     * 用户使用邮箱进行密码找回——当用户输入邮箱地址后，先验证该邮箱是否绑定，若该邮箱已绑定，
     * 则发送验证码到用户的邮箱，当用户输入正确的验证码之后引导用户进行密码重置
     *
     * @param user_pwd   新密码
     * @param user_email 邮箱地址
     * @return
     */
    int resetPassword(String user_pwd, String user_email);
}
