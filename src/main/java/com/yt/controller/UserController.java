package com.yt.controller;

import com.yt.pojo.StudentInformation;
import com.yt.pojo.TeacherInformation;
import com.yt.pojo.User;
import com.yt.service.UserService;
import com.yt.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 *
 * @author yt
 * @date 2019/10/3 - 9:18
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 当用户还未登录时引导用户去登录
     *
     * @return
     */
    @RequestMapping(value = "/goLogin", produces = "application/json;charset=utf-8",
            method = RequestMethod.GET)
    public Result goLogin() {
        return Result.failure(ResultCodeEnum.USER_NOT_LOGGED_IN);
    }

    /**
     * 当前操作的用户的权限不够，提醒用户权限不够
     *
     * @return
     */
    @RequestMapping(value = "/unauthorized",
            produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public Result unauthorized() {
        return Result.failure(ResultCodeEnum.USER_Insufficient_Authority);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/userLogin", produces = "application/json;charset=utf-8",
            method = RequestMethod.POST)
    public Result userLogin(String username, String password) {
        if (username != null && !("").equals(username) && password != null
                && !("").equals(password)) {
            //执行登录
            User user = userService.userLogin(username, password);
            if (user != null) {
                if (user.getState() >= Constants.EFFECTIVE){
                    //查询出当前用户的角色
                    int roleId = userService.getUserRoleId(user.getUser_id());
                    if (roleId == 1) {
                        return Result.failure(ResultCodeEnum.STUDENT_LOGING_SUCCESS, user);
                    }
                    if (roleId == 2) {
                        return Result.failure(ResultCodeEnum.TEACHER_LOGING_SUCCESS, user);
                    }
                    return Result.failure(ResultCodeEnum.ADMIN_LOGING_SUCCESS, user);
                }
                return Result.failure(ResultCodeEnum.USER_LOCKED);
            }
            return Result.failure(ResultCodeEnum.USER_LOGIN_ERROR);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }


    /**
     * 学生查看自己的信息
     *
     * @return
     */
    @RequestMapping(value = "/studentViewInformation",
            produces = "application/json;charset=utf-8", method=RequestMethod.GET)
    public Result studentViewInformation() {
        //获取当前用户的信息
        User user = SubjectGetUserInformation.getUserInformation();
        // 用用户名（username）查询出当前学生的信息
        StudentInformation studentInformation = userService.studentViewInformation(
                user.getUser_name());
        return Result.success(studentInformation);
    }

    /**
     * 教师查看自己的信息
     *
     * @return
     */
    @RequestMapping(value = "/teacherViewInformation",
            produces = "application/json;charset=utf-8", method=RequestMethod.GET)
    public Result teacherViewInformation() {
        //获取当前用户的信息
        User user = SubjectGetUserInformation.getUserInformation();
        // 用用户名（username）查询出当前教师的信息
        TeacherInformation teacherInformation = userService.teacherViewInformation(
                user.getUser_name());
        return Result.success(teacherInformation);
    }

    /**
     * 用户进行密码修改
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @RequestMapping(value = "/userResetPassword",
            produces = "application/json;charset=utf-8", method=RequestMethod.POST)
    public Result userResetPassword(String oldPassword, String newPassword) {
        if (oldPassword != null && !("").equals(oldPassword) && newPassword != null
                && !("").equals(newPassword)) {

            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //将用户输入的旧密码进行MD5加密
            String oldPwd = MD5.cipher_Encryption(user.getUser_name(), oldPassword);
            //将用户输入的新密码进行MD5加密
            String newPwd = MD5.cipher_Encryption(user.getUser_name(), newPassword);
            //查询出当前用户的密码，与用户输入的旧密码进行比对
            String pwd = userService.passwordValidation(user.getUser_name());
            if (oldPwd.equals(pwd)) {
                //进行密码重置
                int row = userService.changePassword(newPwd, user.getUser_name());
                if (row > 0) {
                    return Result.success();
                }
                return Result.failure(ResultCodeEnum.Database_Operation_Exception);
            }
            return Result.failure(ResultCodeEnum.Old_Password_Error);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 用户绑定邮箱验证码发送
     *
     * @param e_mail
     * @param session
     * @return
     */
    @RequestMapping(value = "/mailboxBindingVerificationCodeSending",
            method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result mailboxBindingVerificationCodeSending(String e_mail,
                                                        HttpSession session) {
        if (e_mail != null && !("").equals(e_mail)) {
            //判断当前e_mail是否已被绑定
            String user_name = userService.emailDuplication(e_mail);
            if (user_name == null) {
                //若该邮箱未绑定则发送验证码进行验证,生成邮箱绑定验证码
                String emailRandomCode = VerifyCodeUtil.achieveCode();
                //发送邮箱绑定验证码
                EmailUtil.sendAuthCodeEmail(e_mail, emailRandomCode);
                ///将邮箱绑定验证码放入session
                session.setAttribute("emailRandomCode", emailRandomCode);
                return Result.success();
            }
            return Result.failure(ResultCodeEnum.The_Eailbox_Has_Been_Bound);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 邮箱绑定验证码发送成功后，对用户输入的验证码进行判断，从而进行邮箱绑定
     *
     * @param verificationCode 用户输入的验证码
     * @param e_mail           用户要绑定e_mail的地址
     * @param session
     * @return
     */
    @RequestMapping(value = "/mailboxBinding", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Result mailboxBinding(String verificationCode, String e_mail,
                                 HttpSession session) {

        if (verificationCode != null && !("").equals(verificationCode) && e_mail != null
                && !("").equals(e_mail)) {
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //获取邮箱绑定生成的验证码
            String emailRandomCode = (String) session.getAttribute("emailRandomCode");
            if (verificationCode.equals(emailRandomCode)) {
                //若输入验证码正确,则进行绑定
                int i = userService.mailboxBinding(e_mail, user.getUser_name());
                if (i > 0) {
                    return Result.success();
                }
                return Result.failure(ResultCodeEnum.Database_Operation_Exception);
            }
            return Result.failure(ResultCodeEnum.Verification_Code_Error);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 用户解除绑定邮箱
     */
    @RequestMapping(value = "/releaseOfMailboxBinding", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result releaseOfMailboxBinding() {
        //获取当前用户的信息
        User user = SubjectGetUserInformation.getUserInformation();
        //将邮箱地址更新为空（即未绑定状态）
        int i = userService.mailboxBinding("null", user.getUser_name());
        if (i > 0) {
            return Result.success();
        }
        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
    }

    /**
     * 通过邮箱找回密码——向邮箱发送验证码
     *
     * @param e_mail  找回密码用户绑定的e_mail
     * @param session
     * @return
     */
    @RequestMapping(value = "/passwordRecoveryVerificationCodeSent",
            method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Result passwordRecoveryVerificationCodeSent(String e_mail, HttpSession session) {
        if (e_mail != null && !("").equals(e_mail)) {
            //验证该邮箱是否已经被绑定
            String user_name = userService.emailDuplication(e_mail);
            if (user_name != null) {
                //若已进行绑定则发送验证码进行验证
                String passwordRandomCode = VerifyCodeUtil.achieveCode();
                //将密码找回验证码放入session
                session.setAttribute("passwordRandomCode", passwordRandomCode);
                //将e_mail也放入session中
                session.setAttribute("e_mail", e_mail);
                //向对应邮箱发送密码找回的验证码
                EmailUtil.sendAuthCodeEmailTwo(e_mail, passwordRandomCode);
                return Result.success();
            }
            return Result.failure(ResultCodeEnum.The_Mailbox_Is_Not_Bound);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 通过邮箱找回密码——验证码判断和密码重置
     *
     * @param session
     * @param verificationCode 用户输入的接收到的验证码
     * @param newPassword      新密码
     * @return
     */
    @RequestMapping(value = "/mailboxResetPassword", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Result mailboxResetPassword(HttpSession session, String verificationCode,
                                       String newPassword) {

        if (verificationCode != null && !("").equals(verificationCode) &&
                newPassword != null && !("").equals(newPassword)) {

            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //将用户输入的新密码进行MD5加密
            String newPwd = MD5.cipher_Encryption(user.getUser_name(), newPassword);
            //获取密码找回生成的验证码
            String passwordRandomCode = (String) session.
                    getAttribute("passwordRandomCode");
            //获取当前进行密码找回用户的e_mail
            String e_mail = (String) session.getAttribute("e_mail");
            //将生成的验证码与用户输入的验证码进行比对
            if (verificationCode.equals(passwordRandomCode)) {
                //进行重置密码
                int i = userService.resetPassword(newPwd, e_mail);
                if (i > 0) {
                    return Result.success();
                }
                return Result.failure(ResultCodeEnum.Database_Operation_Exception);
            }
            return Result.failure(ResultCodeEnum.Verification_Code_Error);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }
}
