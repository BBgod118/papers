package com.yt.service;

import com.yt.pojo.*;
import com.yt.utils.PageDo;

import java.util.Date;
import java.util.List;

/**
 * @author yt
 * @date 2019/10/10 - 13:52
 */
public interface AdminService {


    /**
     * 学生注册
     *
     * @param studentInformation 学生信息
     * @return
     */
    int studentRegister(StudentInformation studentInformation);

    /**
     * 教师注册
     *
     * @param teacherInformation 教师信息
     * @return
     */
    int teacherRegister(TeacherInformation teacherInformation);

    /**
     * 添加用户——user表插入
     *
     * @param user_name  用户名
     * @param user_pwd   密码
     * @param real_name  真实姓名
     * @param user_phone 电话号码
     * @return
     */
    int addingUsers(String user_name, String user_pwd, String real_name, String user_phone);

    /**
     * 查询用户表中是否已有该用户名——user_name
     *
     * @param user_name 用户名
     * @return
     */
    User userVerification(String user_name);

    /**
     * 查询出对应用户名的用户id
     *
     * @param user_name 用户名
     * @return
     */
    int userIdQuery(String user_name);

    /**
     * 添加用户——用户信息添加（学生）
     *
     * @param grade      年级
     * @param department 系别
     * @param classes    班级
     * @param student_id 学生id
     * @return
     */
    int studentAddingUserInformation(String grade, String department, String classes, int student_id);

    /**
     * 添加用户——用户信息添加（教师）
     *
     * @param teacher_title 教师职称
     * @param teacher_id    教师id
     * @return
     */
    int teacherAddingUserInformation(String teacher_title, int teacher_id);

    /**
     * 给用户添加角色
     *
     * @param user_id 用户id
     * @param role_id 角色id
     * @return
     */
    int addingUserRoles(int user_id, int role_id);

    /**
     * 教师学生分组
     *
     * @param teacher_id 教师id
     * @param student_id 学生id
     * @return
     */
    int group(int teacher_id, int student_id);

    /**
     * 查询该student_id是否已分组
     *
     * @param student_id
     * @return
     */
    Group groupToRepeat(int student_id);

    /**
     * 管理员进行分组
     *
     * @param teacher_name 教师账户
     * @param student_name 学生账户
     * @return
     */
    int adminGroup(String teacher_name, String student_name);

    /**
     * 查询出所有学生用户
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    PageDo<AllStudentInformation> selectAllStudent(int pageNum, int pageSize);

    /**
     * 查询出所有教师用户
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    PageDo<AllTeacherInformation> selectAllTeacher(int pageNum, int pageSize);

    /**
     * 查询出所有分组信息
     * @return
     */
    List<Group> selectAllGroup();

    /**
     * 查询对应id的用户名和真实姓名
     *
     * @param user_id 用户ID
     * @return
     */
    UserInformation selectUserInformation(int user_id);

    /**
     * 将所有分组信息对应教师、学生的信息查询出来，组成更详细的分组信息
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    PageDo<AllGroupInformation> selectUserInformationTwo(int pageNum, int pageSize);

    /**
     * 封禁用户账户
     *
     * @param user_name 用户名
     * @param state 账户状态
     * @return
     */
    int updateUserSate(int state, String user_name);

    /**
     * 删除对应学生分组
     *
     * @param student_id 学生ID
     * @return
     */
    int deleteGroup(int student_id);

    /**
     * 管理员调整分组
     * @param teacher_name  教师账户
     * @param student_name  学生账户
     * @return
     */
    int adjustmentGrouping(String teacher_name, String student_name);

    /**
     * 查询出所有权限路径
     * @return
     */
    List<Permission> selectAllPermission();

    /**
     * 管理员添加权限功能路径
     * @param perms_name 功能说明
     * @param url   权限路径
     * @return
     */
    int insertPermission(String perms_name, String url);

    /**
     * 查询出所有角色权限
     * @return
     */
    List<RolePermissionTwo> selectAllRolePermission();

    /**
     * 管理员给指定角色添加权限功能
     * @param role_id         教师ID
     * @param permission_id   权限路径ID
     * @return
     */
    int insertRolePermission(int role_id, int permission_id);


    /**
     * 公告发布
     */
    int saveNotice(int admin_id, String content, String title, String author, Date time);

    /**
     * 公告查询
     *
     * @param admin_id 管理员id
     * @return
     */
    Notice queryNotice(int admin_id);
}
