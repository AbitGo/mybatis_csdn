package com.mybatis.chapter3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public SysUser selectUserAndRoleByIdSelect(Long id)
    {
        return userMapper.selectUserAndRoleByIdSelect(id);
    }

    public SysRole selectRolebyId(Long id)
    {
        return userMapper.selectRolebyId(id);
    }
    public List<SysUser> selectAllUserAndRoles()
    {
        return userMapper.selectAllUserAndRoles();
    }

}
