package com.mybatis.chapter3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
//
//    public List<SysUser> selectByUser(SysUser sysUser)
//    {
//        return userMapper.selectByUser(sysUser);
//    }
//
//    public List<SysUser> selectByUserWithTagWhere(SysUser sysUser)
//    {
//        return userMapper.selectByUserWithTagWhere(sysUser);
//    }
//
//    public int updateByIdSelective(SysUser sysUser)
//    {
//        return userMapper.updateByIdSelective(sysUser);
//    }
//    public int updateByIdSelectiveWithTagSet(SysUser sysUser)
//    {
//        return userMapper.updateByIdSelectiveWithTagSet(sysUser);
//    }
//    public int insert2(SysUser sysUser)
//    {
//        return  userMapper.insert2(sysUser);
//    }
//    public SysUser selectByUserWithTagChoose(SysUser sysUser)
//    {
//        return userMapper.selectByUserWithTagChoose(sysUser);
//    }
//    public SysUser selectUserAndRoleById(Long id)
//    {
//        return userMapper.selectUserAndRoleById(id);
//    }
//    public SysUser userRoleMap(Long id)
//    {
//        return userMapper.userRoleMap(id);
//    }
    public SysUser selectUserAndRoleById2(Long id)
    {
        return userMapper.selectUserAndRoleById2(id);
    }
}
