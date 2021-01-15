package com.jonyshare.server.dto;


import java.util.List;

public class RoleUserDto {

    /**
     * id
     */
    private String id;

    /**
     * 角色|id
     */
    private String roleId;

    /**
     * 用户|id
     */
    private String userId;

    /**
     * 用户对应的所有roleId
     */
    private List<String> roleIds;

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "RoleUserDto{" +
                "id='" + id + '\'' +
                ", roleId='" + roleId + '\'' +
                ", userId='" + userId + '\'' +
                ", roleIds=" + roleIds +
                '}';
    }

}