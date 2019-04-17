package com.mybatis.chapter3;

import java.util.Date;
import java.util.List;

public class SysUser {
    private Long id;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userInfo;
    private byte[] headImg;
    private Date create_time;
    private List<SysRole> roleList;


    public Long getId() {
        return id;
    }

    public byte[] getHeadImg() {
        return headImg;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setHeadImg(byte[] headImg) {
        this.headImg = headImg;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}
