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
    @GetMapping("/selectByUser")
    public List<SysUser> selectByUser()
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("ad");
        sysUser.setUserEmail("test@mybatis.tk");
        return userService.selectByUser(sysUser);
    }
    @GetMapping("/selectByUserWithTagWhere")
    public List<SysUser> selectByUserWithTagWhere()
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("ad");
        sysUser.setUserEmail("test@mybatis.tk");
        return userService.selectByUserWithTagWhere(sysUser);
    }

    @GetMapping("/selectByUserWithTagChoose")
    public SysUser selectByUserWithTagChoose()
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("admin");
        return userService.selectByUserWithTagChoose(sysUser);
    }

    @GetMapping("/updateByIdSelective")
    public int updateByIdSelective()
    {
        SysUser sysUser = new SysUser();
        sysUser.setId(3L);
        sysUser.setUserEmail("15695203200@163.com");
        userService.updateByIdSelective(sysUser);
        return 1;
    }

    @GetMapping("/updateByIdSelectiveWithTagSet")
    public int updateByIdSelectiveWithTagSet()
    {
        SysUser sysUser = new SysUser();
        sysUser.setId(4L);
        sysUser.setUserEmail("1419561484@qq.com");
        userService.updateByIdSelectiveWithTagSet(sysUser);
        return 1;
    }

    @GetMapping("/insert2")
    public int insert2()
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserEmail("1419561484@qq.com");
        sysUser.setUserName("xxx");
        sysUser.setUserPassword("xaxa");
        userService.insert2(sysUser);
        return 1;
    }

    @GetMapping("/selectUserAndRoleById")
    public SysUser selectUserAndRoleById()
    {
        return userService.selectUserAndRoleById(1L);
    }

    @GetMapping("/selectUserAndRoleById2")
    public SysUser selectUserAndRoleById2()
    {
        return userService.selectUserAndRoleById2(1L);
    }
}
