package com.mybatis.chapter3;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    //最简单的查询
    List<SysUser> selectByUser(SysUser sysUser);
    //使用where标签查询
    List<SysUser> selectByUserWithTagWhere(SysUser sysUser);
    //使用choose标签查询
    SysUser selectByUserWithTagChoose(SysUser sysUser);

    //最简单的修改
    int updateByIdSelective(SysUser sysUser);
    //使用set标签修改
    int updateByIdSelectiveWithTagSet(SysUser sysUser);

    //使用resttype进行高级结果映射<一对一>
    SysUser selectUserAndRoleById(Long id);
    int insert2(SysUser sysUser);


    SysUser userRoleMap(Long id);
    //使用restset进行高级结果映射<一对一>
    SysUser selectUserAndRoleById2(Long id);
}
