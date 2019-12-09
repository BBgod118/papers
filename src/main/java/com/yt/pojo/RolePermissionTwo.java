package com.yt.pojo;

/**
 * 管理员查看角色权限列表实体类
 *
 * @author yt
 * @date 2019/11/7 - 0:50
 */
public class RolePermissionTwo {

    private Integer role_id;

    private Integer permission_id;

    public RolePermissionTwo() {
    }

    public RolePermissionTwo(Integer role_id, Integer permission_id) {
        this.role_id = role_id;
        this.permission_id = permission_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(Integer permission_id) {
        this.permission_id = permission_id;
    }

    @Override
    public String toString() {
        return "RolePermissionTwo{" + "role_id=" + role_id + ", permission_id=" + permission_id + '}';
    }
}
