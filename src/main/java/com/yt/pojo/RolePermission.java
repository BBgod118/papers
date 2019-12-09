package com.yt.pojo;

import java.util.List;

/**
 * 角色权限实体类
 *
 * @author yt
 * @date 2019/9/25 - 1:03
 */
public class RolePermission {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限地址
     */
    private String url;
    /**
     * 对应的一个权限有哪些角色id可以访问
     */
    private List<Integer> roleIds;

    public RolePermission() {
    }

    public RolePermission(Integer id, String url, List<Integer> roleIds) {
        this.id = id;
        this.url = url;
        this.roleIds = roleIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "RolePermission{" + "id=" + id + ", url='" + url + '\'' + ", roleIds=" + roleIds + '}';
    }
}
