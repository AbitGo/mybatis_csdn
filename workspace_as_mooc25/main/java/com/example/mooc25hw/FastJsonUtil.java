package com.example.mooc25hw;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by zy on 08/25/19
 */
public class FastJsonUtil {
    //take json format data to java bean
    public static <T> T toBean(String jsonData, Class<T> tClass) {
        return JSON.parseObject(jsonData, tClass);
    }
    //take arbitrary object to json format data
    public static String ToJson(Object obj) {
        return JSON.toJSONString(obj);
    }
    //get json string one of single values
    public static Object getSingleValue(String jsonData, String key) {
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        return jsonObject.get(key);
    }
    //get multiple values in the json string
    public static List<String> getValue(String jsonData, String...key){
        List<String> jsonList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        for (int i = 0; i < key.length; i ++){
            jsonList.add(String.valueOf(jsonObject.get(key[i])));
        }
        return jsonList;
    }
    //take json string to Java List Array
    public static <T> List<T> toList(String jsonData, Class<T> tClass) {
        return JSON.parseArray(jsonData, tClass);
    }
    //take json string to Java Map Array
    public static Map<String, Object> toMap(String jsonData){
        return JSON.parseObject(jsonData,new TypeReference<Map<String, Object>>(){});
    }
    //take json string to List<Map<String,Object>> Array
    public static List<Map<String, Object>> toListMap(String jsonData){
        return JSON.parseObject(jsonData,new TypeReference<List<Map<String, Object>>>(){});
    }
}
