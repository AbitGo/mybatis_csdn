package com.Redis;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@Service("tokenService")
public class RedisService {
    @Resource
    private RedisUtil redisUtil;

    //把token存到redis中,并设置定时时间为60s
    public void save(String UserName, String token) {
        redisUtil.set(UserName,token);
    }
    //将指定的key时间刷新
    public void Updata(String UserName)
    {
        redisUtil.updataLockTime(UserName, 60*60);
    }
    //把token存到redis中
    public String get(String UserName) {
        return redisUtil.get(UserName);
    }


    //ZSet的api
    public Boolean AddZSetKeyAndValue(String TableName,String key,Double value)
    {
        return redisUtil.AddZSetKeyAndValue(TableName,key,value);
    }
    public Set<String> GetZSetKey(String TableName,Long StartIndex,Long EndIndex)
    {
        Set<String> result1 = redisUtil.GetZSetKey(TableName,StartIndex,EndIndex);
        return result1;
    }
    public Double GetZSetValue(String TableName,String KeyName)
    {
        Double result1 = redisUtil.GetZSetValue(TableName,KeyName);
        return result1;
    }
    public Long RemoveZSetValue(String TableName,String... KeyNames)
    {
        Long result1 = redisUtil.RemoveZSetValue(TableName,KeyNames);
        return result1;
    }
    public Long GetZSetKeyIndex(String TableName,String KeyName)
    {
        Long result1 = redisUtil.GetZSetKeyIndex(TableName,KeyName);
        return result1;
    }
    public Long RemoveZSetRange(String TableName,Long start,Long end)
    {
        Long result1 = redisUtil.RemoveZSetRange(TableName,start,end);
        return result1;
    }

    public Set<String> GetKeysRangeByScore(String TableName,double startScore,double endScore)
    {
        Set<String> result1 = redisUtil.GetKeysRangeByScore(TableName,startScore,endScore);
        return result1;
    }

    public Boolean AddHashKeyAndValue(String tableName,Map<String,Object> mapParam)
    {
        return redisUtil.AddHashKeyAndValue(tableName,mapParam);
    }
    public Map<String, Object> GetHashKeyAndValue(String tableName)
    {
        return redisUtil.GetHashKeyAndValue(tableName);
    }
    public Boolean RemoveHashKeyAndValue(String tableName)
    {
        return redisUtil.RemoveHashKeyAndValue(tableName);
    }



}