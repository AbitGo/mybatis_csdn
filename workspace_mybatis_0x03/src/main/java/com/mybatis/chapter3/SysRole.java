package com.mybatis.chapter3;

import java.util.Date;

public class SysRole {
    private Long id;
    private String role_Name;
    private Long enabled;
    private Long create_by;
    private Date create_time;

    public Long getEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public Long getCreate_by() {
        return create_by;
    }

    public String getRole_Name() {
        return role_Name;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setCreate_by(Long create_by) {
        this.create_by = create_by;
    }

    public void setRole_Name(String role_Name) {
        this.role_Name = role_Name;
    }
}
