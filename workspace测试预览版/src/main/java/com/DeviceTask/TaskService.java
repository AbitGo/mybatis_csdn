package com.DeviceTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    TaskMapper taskMapper;
    public int addEffectiveDeviceTask(Map<String,Object> param)
    {
        return taskMapper.addEffectiveDeviceTask(param);
    }

    public List<Map<String,Object>> getEffectiveTask(Map<String,Object> param,int page,int limit)
    {
        return taskMapper.getEffectiveTask(param);
    }
    public int updateTaskByTaskCode(List<ChangeStatusPojos> ChangeStatusPojos){
        return taskMapper.updateTaskByTaskCode(ChangeStatusPojos);
    }

    public List<Map<String,Object>> getApplyDeviceTask(Map<String,Object> param,int page,int limit)
    {
        return taskMapper.getApplyDeviceTask(param);
    }

    public int addApplyDeviceTask(Map<String,Object> param)
    {
        return taskMapper.addApplyDeviceTask(param);
    }

    public int changeTaskApplyToEffective(Map<String,Object> param)
    {
        return taskMapper.changeTaskApplyToEffective(param);
    }
    public int addUnlockRecord(Map<String,Object> param)
    {
        return taskMapper.addUnlockRecord(param);
    }

    public Map<String,Object> getTaskByCode(String param)
    {
        return taskMapper.getTaskByCode(param);
    }
    public List<Map<String,Object>> searchUnlockRecord(Map<String,Object> param,int page,int limit)
    {
        return taskMapper.searchUnlockRecord(param);
    }
    public String SearchDianXinCodeByDeviceCode (String DeviceCode){
        return taskMapper.SearchDianXinCodeByDeviceCode(DeviceCode);
    }

}
