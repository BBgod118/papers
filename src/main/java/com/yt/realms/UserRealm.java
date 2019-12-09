package com.yt.realms;

import com.yt.pojo.User;
import com.yt.service.UserService;
import com.yt.utils.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 用户Realm
 *
 * @author yt
 * @date 2019/9/21 - 4:40
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 登录认证方法
     * token是从登录 subject.login(token)传过来的
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {

        // 从authenticationToken中获取username
        String user_name = (String) authenticationToken.getPrincipal();
        // 调用数据库的方法，从数据库查询出当前username（用户名）对应的信息
        User user = userService.userInformationQuery(user_name);
        // 若用户不存在，则可以抛出UnknownAccountException异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user_name);
        /*
        SimpleAuthenticationInfo里的第一个参数为用户名，第二个参数是利用当前用户名查询出的对应的用户密码，
        第三个参数为加密盐值，第四个父类方法的个体Name(),然后返回。
        */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user_name,
                user.getUser_pwd(), credentialsSalt, getName());
        return info;
    }

    //每次权限验证执行
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        // 1.从session中取出当前用户信息
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        // 2.利用登录的用户的信息中的用户id查询出当前用户的所有角色
        List<Integer> roleIds = userService.getUserRoleIds(user.getUser_id());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (roleIds != null && roleIds.size() > 0) {
            //循环遍历集合中的所有角色，并添加给info
            for (Integer roleId : roleIds) {
                info.addRole(roleId.toString());
            }
            return info;
        }
        return null;
    }

    public static void main(String[] args) {
        String hash = "MD5";
        Object cred = "123";
        Object salt = ByteSource.Util.bytes("admin");
        int hashit = 1024;
        Object result = new SimpleHash(hash, cred, salt, hashit);
        System.out.println(result);
    }
}
