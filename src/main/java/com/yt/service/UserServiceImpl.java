package com.yt.service;

import com.yt.dao.GetPermsDao;
import com.yt.dao.UserMapper;
import com.yt.pojo.RolePermission;
import com.yt.pojo.StudentInformation;
import com.yt.pojo.TeacherInformation;
import com.yt.pojo.User;
import com.yt.utils.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yt
 * @date 2019/10/3 - 9:05
 */
@Service("userService")
public class UserServiceImpl implements UserService, GetPermsDao {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GetPermsDao getPermsDao;

    @Override
    public User userLogin(String user_name, String user_pwd) {
        Subject subject = SecurityUtils.getSubject();
        //把用户名和密码封装为UsernamePasswordToken对象
        UsernamePasswordToken token = new UsernamePasswordToken(user_name, user_pwd);
        try {
            subject.login(token);
            //创建shiro session 将登录的用户信息放入session中
            Session session = subject.getSession();
            User user = userInformationQuery(user_name);
            session.setAttribute(Constants.SESSION_USER, user);
            //将登录成功的用户信息返回给控制器
            return user;
        } catch (AuthenticationException e) {
            return null;
        }
    }


    @Override
    public User userInformationQuery(String user_name) {
        return userMapper.userInformationQuery(user_name);
    }

    @Override
    public Integer getUserRoleId(Integer user_id) {
        return userMapper.getUserRoleId(user_id);
    }

    @Override
    public List<Integer> getUserRoleIds(Integer user_id) {
        return userMapper.getUserRoleIds(user_id);
    }

    @Override
    public List<RolePermission> getPolePermission() {
        return getPermsDao.getPolePermission();
    }

    @Override
    public StudentInformation studentViewInformation(String user_name) {
        return userMapper.studentViewInformation(user_name);
    }

    @Override
    public TeacherInformation teacherViewInformation(String user_name) {
        return userMapper.teacherViewInformation(user_name);
    }


    @Override
    public String passwordValidation(String user_name) {
        return userMapper.passwordValidation(user_name);
    }

    @Override
    public int changePassword(String user_pwd, String user_name) {
        return userMapper.changePassword(user_pwd, user_name);
    }

    @Override
    public String emailDuplication(String user_email) {
        return userMapper.emailDuplication(user_email);
    }

    @Override
    public int mailboxBinding(String user_email, String user_name) {
        return userMapper.mailboxBinding(user_email, user_name);
    }

    @Override
    public int resetPassword(String user_pwd, String user_email) {
        return userMapper.resetPassword(user_pwd, user_email);
    }
}
