package com.yt.pojo;

/**
 * 角色权限实体类
 *
 * @author yt
 * @date 2019/10/3 - 8:15
 */
public class Permission {
    private Integer permission_id;
    /**
     * 权限名称
     */
    private String perms_name;
    /**
     * 权限功能url
     */
    private String url;

    public Permission() {
    }

    public Permission(Integer permission_id, String perms_name, String url) {
        this.permission_id = permission_id;
        this.perms_name = perms_name;
        this.url = url;
    }

    public Integer getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(Integer permission_id) {
        this.permission_id = permission_id;
    }

    public String getPerms_name() {
        return perms_name;
    }

    public void setPerms_name(String perms_name) {
        this.perms_name = perms_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Permission{" + "permission_id=" + permission_id + ", perms_name='" + perms_name + '\'' + ", url='" + url + '\'' + '}';
    }
}
