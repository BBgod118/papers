package com.yt.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义的shiro过滤器
 * 配置过滤器在roles[user,admin]这种角色条件下，只要有其中一个角色即可，
 * shiro过滤器默认必须满足同时拥个角色才能访问
 *
 * @author yt
 * @date 2019/9/23 - 0:58
 */
public class MyShiroFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest,
                                      ServletResponse servletResponse,
                                      Object o) throws Exception {

        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;
        //如果roles为空，就是什么角色也没有时
        if (roles == null) {
            return true;
        }
        for (String role : roles) {
            //如果subject中有数组中的任何一个角色则返回true
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
