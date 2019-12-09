package com.yt.dao;

import com.yt.pojo.RolePermission;

import java.util.List;

/**
 * @author yt
 * @date 2019/9/25 - 15:07
 */
public interface GetPermsDao {
    /**
     * 查询出数据库中的各个角色可以访问哪些权限地址
     * @return
     */
    List<RolePermission> getPolePermission();
}
