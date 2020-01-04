package com.DeviceTask;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskMapper {
    //添加有效任务(管理员添加
    int addEffectiveDeviceTask(Map<String,Object> param);
    //添加申请任务(用户添加
    int addApplyDeviceTask(Map<String,Object> param);
    //获取有效任务
    List<Map<String,Object>> getEffectiveTask(Map<String,Object> param);
    //获取申请的任务
    List<Map<String,Object>> getApplyDeviceTask(Map<String,Object> param);
    //获取任务(只获取一个
    Map<String,Object> getTaskByCode(String param);
    //通过taskcode去改变任务的有效性
    int updateTaskByTaskCode(List<ChangeStatusPojos> ChangeStatusPojos);
    //通过taskcode去改变任务的有状态值
    int changeTaskApplyToEffective(Map<String,Object> param);
    //添加开锁记录
    int addUnlockRecord(Map<String,Object> param);

    List<Map<String,Object>> searchUnlockRecord(Map<String,Object> param);
    String SearchDianXinCodeByDeviceCode (String DeviceCode);
}
