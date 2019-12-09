package com.yt.filter;

import com.yt.pojo.RolePermission;
import com.yt.service.UserService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yt
 * @date 2019/10/3 - 9:05
 */
public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    @Autowired
    private UserService userService;

    private static final String ROLE_STRING = "roles[{0}]";
    //设置filterChainDefinitions来保存默认的权限配置，不然在动态添加权限配置调用update()方法后，原来的
    //默认的权限配置将被清空
    private String filterChainDefinitions;

    @Override
    public void setFilterChainDefinitions(String definitions) {
        filterChainDefinitions = definitions;
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection("urls");
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection("");
        }
        //super.setFilterChainDefinitions(definitions);

        //从数据库中查询出所有权限配置
        List<RolePermission> list = userService.getPolePermission();
        //System.out.println("权限集合"+list);
        if (list != null) {
            //遍历集合中的权限配置
            for (RolePermission rolePermission : list) {
                //取出访问当前这个地址的所有角色id
                List<Integer> roleIds = rolePermission.getRoleIds();
                //如果当前的请求地址不为空
                if (StringUtils.hasLength(rolePermission.getUrl()) && roleIds != null
                        && roleIds.size() > 0) {

                    //字符串拼接url和角色
                    StringBuilder stringBuilder = new StringBuilder();
                    //循环取出每一个角色ID
                    for (Integer role : roleIds) {
                        stringBuilder.append(role).append(",");
                    }
                    String str = stringBuilder.substring(0, stringBuilder.length() - 1);
                    //封装为roles[test,guest]
                    section.put(rolePermission.getUrl(),
                            MessageFormat.format(ROLE_STRING, str));
                }
            }
        }
        //安全验证,添加没有指定权限认证的url都需要登录后才能访问
        section.put("/**", "authc");
        this.setFilterChainDefinitionMap(section);
    }

    /**
     * 动态添加权限配置方法
     */
    public void update() {
        synchronized (this) {
            try {
                AbstractShiroFilter shiroFilter = (AbstractShiroFilter) this.getObject();
                PathMatchingFilterChainResolver resolver =
                        (PathMatchingFilterChainResolver) shiroFilter.
                                getFilterChainResolver();
                DefaultFilterChainManager manager =
                        (DefaultFilterChainManager) resolver.getFilterChainManager();
                //清除原来的权限验证配置
                manager.getFilterChains().clear();
                this.getFilterChainDefinitionMap().clear();
                //清空后重新添加默认的权限配置
                this.setFilterChainDefinitions(filterChainDefinitions);
                //可动态加载新的权限验证信息，如从数据库中更新


                Map<String, String> chains = this.getFilterChainDefinitionMap();
                if (!CollectionUtils.isEmpty(chains)) {
                    Iterator var12 = chains.entrySet().iterator();
                    while (var12.hasNext()) {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) var12.next();
                        String url = entry.getKey();
                        String chainDefinition = entry.getValue();
                        manager.createChain(url, chainDefinition);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
