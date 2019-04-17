package com.mybatis.chapter3;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    SysUser selectUserAndRoleByIdSelect(Long id);
    SysRole selectRolebyId(Long id);
    List<SysUser> selectAllUserAndRoles();
}
