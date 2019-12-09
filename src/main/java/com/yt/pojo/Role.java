package com.yt.pojo;

/**
 * 用户角色实体类
 *
 * @author yt
 * @date 2019/10/3 - 8:17
 */
public class Role {
    private Integer role_id;
    /**
     * 角色名称
     */
    private String role_name;
    /**
     * 备注
     */
    private String remarks;

    public Role() {
    }

    public Role(Integer role_id, String role_name, String remarks) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.remarks = remarks;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Role{" + "role_id=" + role_id + ", role_name='" + role_name + '\'' + ", remarks='" + remarks + '\'' + '}';
    }
}
