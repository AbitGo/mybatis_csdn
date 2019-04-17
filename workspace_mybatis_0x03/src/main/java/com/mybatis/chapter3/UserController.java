package com.mybatis.chapter3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/selectUserAndRoleById2")
    public SysUser selectUserAndRoleByIdSelect()
    {
        return userService.selectUserAndRoleByIdSelect(1L);
    }

    @GetMapping("/selectAllUserAndRoles")
    public List<SysUser> selectAllUserAndRoles()
    {
        return userService.selectAllUserAndRoles();
    }
}
