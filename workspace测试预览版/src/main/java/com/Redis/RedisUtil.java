package com.Redis;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private RedisTemplate redisTemplate;

    public void set(String key,String value){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    //生成
    public void setex(String key,String value,int seconds){
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        //这里一定需要加上TimeUnit.SECONDS不然会出现value有/0/0的情况
        valueOperations.set(key, value,seconds,TimeUnit.SECONDS);
    }

    //更新锁的时间
    public void updataLockTime(String key,int seconds){
        //这里一定需要加上TimeUnit.SECONDS不然会出现value有/0/0的情况
        redisTemplate.expire(key,seconds,TimeUnit.SECONDS);
    }

    public String get(String key){
        //这里的key也就是userName
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }


    //Zset有序队列的api
    public Boolean AddZSetKeyAndValue(String TablenName,String key,Double value)
    {
        Boolean result = redisTemplate.opsForZSet().add(TablenName,key,value);
        return result;
    }

    public Set<String> GetZSetKey(String TablenName,Long StartIndex,Long EndIndex)
    {
        Set<String> result = redisTemplate.opsForZSet().range(TablenName,StartIndex,EndIndex);
        return result;
    }

    public Double GetZSetValue(String TablenName,String keyName)
    {
        Double result = redisTemplate.opsForZSet().score(TablenName,keyName);
        return result;
    }

    public Long RemoveZSetValue(String TablenName,String... keyNames)
    {
        Long result = redisTemplate.opsForZSet().remove(TablenName,keyNames);
        return result;
    }

    public Long RemoveZSetRange(String TablenName,Long start,Long end)
    {
        Long result = redisTemplate.opsForZSet().removeRange(TablenName,start,end);
        return result;
    }

    public Long GetZSetKeyIndex(String tableName,String keyName)
    {
        Long result = redisTemplate.opsForZSet().rank(tableName,keyName);
        return result;
    }

    public Set<String> GetKeysRangeByScore(String tableName,double startScore,double endScore)
    {
        Set<String> result = redisTemplate.opsForZSet().rangeByScore(tableName,startScore,endScore);
        return result;
    }


    //HashSet的api
    public Boolean AddHashKeyAndValue(String tableName,Map<String,Object> mapParam)
    {
        //将hashMap全部塞进去
        //因为redis中的hash是使用的hash,所以我可以直接扔进去数据,从而实现刷新
        //表名称为DeviceId:2452a640-19cc-42e1-873a-c91adc9195a3(例子)
        redisTemplate.opsForHash().putAll(tableName,mapParam);
        return true;
    }

    public Map<String,Object> GetHashKeyAndValue(String tableName)
    {
        Map<String,Object> result = redisTemplate.opsForHash().entries(tableName);
        return result;
    }

    public Boolean RemoveHashKeyAndValue(String tableName)
    {
        Boolean  result = redisTemplate.delete(tableName);
        return result;
    }




}
