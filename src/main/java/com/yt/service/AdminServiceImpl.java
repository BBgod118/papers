package com.yt.service;

import com.yt.dao.AdminMapper;
import com.yt.pojo.*;
import com.yt.utils.Constants;
import com.yt.utils.MD5;
import com.yt.utils.PageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yt
 * @date 2019/10/10 - 13:52
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int studentRegister(StudentInformation sInformation) {
        if (sInformation.getUser_name() != null && !("").equals(sInformation.getUser_name()) && sInformation.getUser_pwd() != null && !("").equals(sInformation.getUser_pwd()) && sInformation.
                getReal_name() != null && !("").equals(sInformation.getReal_name()) && sInformation.getGrade() != null && !("").equals(sInformation.getGrade()) && sInformation.getDepartment() != null && !("").equals(sInformation.getDepartment()) && sInformation.getClasses() != null && !("").equals(sInformation.getClasses()) && sInformation.getUser_phone() != null && !("").equals(sInformation.getUser_phone())) {

            //查询user表中是否已有该用户名
            User user = userVerification(sInformation.getUser_name());
            if (user == null) {
                //对用户输入的密码进行加密
                String password = MD5.cipher_Encryption(sInformation.getUser_name(), sInformation.getUser_pwd());
                //当管理员进行学生添加时，先在user表中添加
                int row = addingUsers(sInformation.getUser_name(), password, sInformation.getReal_name(), sInformation.getUser_phone());
                if (row > 0) {
                    //先查询出上面添加用户的user_id
                    int user_id = userIdQuery(sInformation.getUser_name());
                    //然后再在学生信息表中插入当前学生信息
                    int rowTwo = studentAddingUserInformation(sInformation.getGrade(), sInformation.getDepartment(), sInformation.getClasses(), user_id);
                    if (rowTwo > 0) {
                        //给当前学生添加角色
                        int rowThree = addingUserRoles(user_id, Constants.STUDENT_ROLE);
                        if (rowThree > 0) {
                            return Constants.ADD_INGSUCCESS_TOSTUDENTS;
                        }
                        return Constants.ATABASEOPERATION_EXCEPTION;
                    }
                    return Constants.ATABASEOPERATION_EXCEPTION;
                }
                return Constants.ATABASEOPERATION_EXCEPTION;
            }
            return Constants.USERNAME_REGISTERED;
        }
        return Constants.PARAM_IS_BLANK;
    }

    @Override
    public int teacherRegister(TeacherInformation tInformation) {
        if (tInformation.getUser_name() != null && !("").equals(tInformation.
                getUser_name()) && tInformation.getUser_pwd() != null && !("").equals(tInformation.getUser_pwd()) && tInformation.getReal_name() != null && !("").equals(tInformation.getReal_name()) && tInformation.getUser_phone() != null && !("").equals(tInformation.getUser_phone()) && tInformation.getTeacher_title() != null && !("").equals(tInformation.getTeacher_title())) {
            //查询user表中是否已有该用户名
            User user = userVerification(tInformation.getUser_name());
            if (user == null) {
                //对用户输入的密码进行加密
                String password = MD5.cipher_Encryption(tInformation.getUser_name(), tInformation.getUser_pwd());
                //当管理员进行教师添加时，先在user表中添加
                int row = addingUsers(tInformation.getUser_name(), password, tInformation.getReal_name(), tInformation.getUser_phone());
                if (row > 0) {
                    //先查询出上面添加用户的user_id
                    int user_id = userIdQuery(tInformation.getUser_name());
                    //然后再在教师信息表中插入当前教师信息
                    int rowTwo = teacherAddingUserInformation(tInformation.
                            getTeacher_title(), user_id);
                    if (rowTwo > 0) {
                        //给当前教师添加角色
                        int rowThree = addingUserRoles(user_id, Constants.TEACHER_ROLE);
                        if (rowThree > 0) {
                            return Constants.ADD_EDTEACHER_SUCCESS;
                        }
                        return Constants.ATABASEOPERATION_EXCEPTION;
                    }
                    return Constants.ATABASEOPERATION_EXCEPTION;
                }
                return Constants.ATABASEOPERATION_EXCEPTION;
            }
            return Constants.USERNAME_REGISTERED;
        }
        return Constants.PARAM_IS_BLANK;
    }

    @Override
    public int addingUsers(String user_name, String user_pwd, String real_name, String user_phone) {
        return this.adminMapper.addingUsers(user_name, user_pwd, real_name, user_phone);
    }

    @Override
    public User userVerification(String user_name) {
        return this.adminMapper.userVerification(user_name);
    }

    @Override
    public int userIdQuery(String user_name) {
        return this.adminMapper.userIdQuery(user_name);
    }

    @Override
    public int studentAddingUserInformation(String grade, String department, String classes, int student_id) {
        return this.adminMapper.studentAddingUserInformation(grade, department, classes, student_id);
    }

    @Override
    public int teacherAddingUserInformation(String teacher_title, int teacher_id) {
        return this.adminMapper.teacherAddingUserInformation(teacher_title, teacher_id);
    }

    @Override
    public int addingUserRoles(int user_id, int role_id) {
        return this.adminMapper.addingUserRoles(user_id, role_id);
    }

    @Override
    public int group(int teacher_id, int student_id) {
        return adminMapper.group(teacher_id, student_id);
    }

    @Override
    public Group groupToRepeat(int student_id) {
        return adminMapper.groupToRepeat(student_id);
    }

    @Override
    public int adminGroup(String teacher_name, String student_name) {
        //查询user表中是否有该教师和学生
        User teacher = userVerification(teacher_name);
        User student = userVerification(student_name);
        if (teacher == null || student == null) {
            return Constants.USER_NOT_REGISTERED;
        }
        //通过教师账户和学生账户查询出他们对应的id
        int teacher_id = userIdQuery(teacher_name);
        int student_id = userIdQuery(student_name);
        //查询该学生是否已进行分组
        Group group = groupToRepeat(student_id);
        if (group == null) {
            //对应教师和学生进行分组
            int row = group(teacher_id, student_id);
            if (row > 0) {
                //分组成功
                return Constants.GROUP_SUCCESS;
            }
            return Constants.ATABASEOPERATION_EXCEPTION;
        }
        //该学生已分组
        return Constants.TE_STUDENT_HAS_BEEN_GROUPED;
    }

    @Override
    public PageDo<AllStudentInformation> selectAllStudent(int pageNum, int pageSize) {
        List<AllStudentInformation> list = adminMapper.selectAllStudent();
        //进行分页
        PageDo<AllStudentInformation> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<AllStudentInformation> data = new ArrayList<>();
        if (list.size() > pageSize) {
            for (int i = 0; i < pageSize && startIndex + i < page.getTotalRecord(); i++) {
                data.add(list.get(startIndex + i));
            }
            page.setDatas(data);
        } else {
            page.setDatas(list);
        }
        return page;
    }

    @Override
    public PageDo<AllTeacherInformation> selectAllTeacher(int pageNum, int pageSize) {
        List<AllTeacherInformation> list = adminMapper.selectAllTeacher();
        //进行分页
        PageDo<AllTeacherInformation> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<AllTeacherInformation> data = new ArrayList<>();
        if (list.size() > pageSize) {
            for (int i = 0; i < pageSize && startIndex + i < page.getTotalRecord(); i++) {
                data.add(list.get(startIndex + i));
            }
            page.setDatas(data);
        } else {
            page.setDatas(list);
        }
        return page;
    }


    @Override
    public List<Group> selectAllGroup() {
        return adminMapper.selectAllGroup();
    }

    @Override
    public UserInformation selectUserInformation(int user_id) {
        return adminMapper.selectUserInformation(user_id);
    }

    @Override
    public PageDo<AllGroupInformation> selectUserInformationTwo(int pageNum, int pageSize) {
        List<Group> list = selectAllGroup();
        List<AllGroupInformation> groupList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //查询出当前这次循环中教师和学生的信息
            UserInformation teacher =
                    selectUserInformation(list.get(i).getTeacher_id());
            UserInformation student =
                    selectUserInformation(list.get(i).getStudent_id());
            groupList.add(new AllGroupInformation(teacher.getUser_name(),
                    teacher.getReal_name(), student.getUser_name(),
                    student.getReal_name()));
        }
        //进行分页
        PageDo<AllGroupInformation> page = new PageDo<>(pageNum, pageSize, groupList.size());
        int startIndex = page.getStartIndex();
        List<AllGroupInformation> data = new ArrayList<>();
        if (list.size() > pageSize) {
            for (int i = 0; i < pageSize && startIndex + i < page.getTotalRecord(); i++) {
                data.add(groupList.get(startIndex + i));
            }
            page.setDatas(data);
        } else {
            page.setDatas(groupList);
        }
        return page;
    }

    @Override
    public int updateUserSate(int state, String user_name) {
        return adminMapper.updateUserSate(state, user_name);
    }

    @Override
    public int deleteGroup(int student_id) {
        return adminMapper.deleteGroup(student_id);
    }

    @Override
    public int adjustmentGrouping(String teacher_name, String student_name) {
        //查询user表中是否有该教师和学生
        User teacher = userVerification(teacher_name);
        User student = userVerification(student_name);
        if (teacher == null || student == null) {
            return Constants.USER_NOT_REGISTERED;
        }
        //通过教师账户和学生账户查询出他们对应的id
        int teacher_id = userIdQuery(teacher_name);
        int student_id = userIdQuery(student_name);
        //删除该学生原有的分组记录
        int row = deleteGroup(student_id);
        if (row > 0){
            //对该学生重新进行分组
            int row2 = group(teacher_id, student_id);
            if (row2 > 0){
                //调整分组成功
                return Constants.ADJUSTMENT_GROUPING;
            }
            return Constants.ATABASEOPERATION_EXCEPTION;
        }
        return Constants.ATABASEOPERATION_EXCEPTION;
    }

    @Override
    public List<Permission> selectAllPermission() {
        return adminMapper.selectAllPermission();
    }

    @Override
    public int insertPermission(String perms_name, String url) {
        return adminMapper.insertPermission(perms_name, url);
    }

    @Override
    public List<RolePermissionTwo> selectAllRolePermission() {
        return adminMapper.selectAllRolePermission();
    }

    @Override
    public int insertRolePermission(int role_id, int permission_id) {
        return adminMapper.insertRolePermission(role_id, permission_id);
    }
}
