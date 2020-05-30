package com.example.mooc25hw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner_prov,spinner_city;
    private TextView textView;
    private LinearLayout linearLayout;
    private OkHttpUtils okHttpUtils;

    //作为省的初始ID
    private String provinceId = "10101";
    private String cityID = "01";

    //作为省名字的数组
    private Map<String,String> provinceMap= new HashMap<>();
    private ArrayList<String> provinceArray=new ArrayList<>();

    //作为市区名字的数组
    private Map<String,String> cityMap = new HashMap<>();
    private ArrayList<String> cityArray = new ArrayList<>();

    //获取省的HTTP请求链接
    final String searchPorvURI = "http://www.weather.com.cn/data/city3jdata/china.html";
    //获取市区的HTTP请求
    //需要使用 searchCityURI+provionceID+searchCityURI_suffix进行请求
    final String searchCityURI = "http://www.weather.com.cn/data/city3jdata/provshi/";
    final String searchCityURI_suffix = ".html";

    final String searchWeatherURI = "http://www.weather.com.cn/data/sk/";
    final String searchWeatherURI_suffix = ".html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        //进行省的HTTP即可
        getHTTPLink(searchPorvURI,0);
    }

    /**
     * 名称：获取HTTP连接
     * 参数：URI获取JSON的链接、operateId为操作码
     * 返回值：无
     */
    public void getHTTPLink(String URI,int operateId){
        //获得初始化之后的Https连接
        okHttpUtils = OkHttpUtils.getInstance();
        okHttpUtils.getRequest(URI, new Callback() {
            //无操作
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                switch (operateId){
                    case 0:
                        //讲数据传输至省数据更新函数
                        UpdateProvinceData(json);
                        break;
                    case 1:
                        UpdateCityData(json);
                        break;
                    case 2:
                        UpdateWeatherData(json);
                        break;
                }
            }
        });
    }
    /***
     * 名称：更新所在市区的天气
     * 参数：HTTP传回的JSON数据
     * 返回值：无
     * 功能说明：通过解析JSON更新市区天气数据
     * @param JsonData
     * "weatherinfo": {
     *         "city": "上海",
     *         "cityid": "101020100",
     *         "temp": "23.5",
     *         "WD": "东北风",
     *         "WS": "小于3级",
     *         "SD": "80%",
     *         "AP": "1006.4hPa",
     *         "njd": "2903",
     *         "WSE": "<3",
     *         "time": "17:00",
     *         "sm": "1.1",
     *         "isRadar": "1",
     *         "Radar": "JC_RADAR_AZ9210_JB"
     *     }
     */
    public void UpdateWeatherData(String JsonData){
        runOnUiThread(() -> {
            try {
                //进行JSON数据的反序列化
                JSONObject param1 = JSONObject.parseObject(JsonData);
                JSONObject param2 =JSONObject.parseObject(param1.get("weatherinfo").toString());
                String result = "城市："+param2.getString("city")+"\n"
                        +"温度："+param2.getString("temp")+"\n"
                        +"湿度："+param2.getString("SD")+"\n"
                        +"气压："+param2.getString("AP")+"\n"
                        +"天气："+param2.getString("WD")+"\n";
                textView.setText(result);

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this,"JSON解析失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /***
     * 名称：更新市区信息
     * 参数：HTTP传回的JSON数据
     * 返回值：无
     * 功能说明：通过解析JSON更新市区数据
     * @param JsonData {"01": "南京","02": "无锡"}
     */
    public void UpdateCityData(String JsonData){
        runOnUiThread(() -> {
            try {
                //进行JSON数据的反序列化
                JSONObject param = JSONObject.parseObject(JsonData);
                //将CityID编号提取出来
                Set<String> cityKey = param.keySet();
                Toast.makeText(MainActivity.this,"JSON市区信息获取成功",Toast.LENGTH_SHORT).show();
                //数据更新，防止省的市区进行叠加
                cityArray.clear();
                cityMap.clear();
                for(String key:cityKey){
                    //将数据以key:provinceName存储至provinceList
                    String cityName = param.getString(key);
                    cityMap.put(cityName,key);
                    cityArray.add(cityName);
                }
                //进行适配器的操作
                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_dropdown_item_1line, cityArray);
                spinner_city.setAdapter(arrayAdapter);
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this,"JSON解析失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 名称：更新省信息
     * 参数：HTTP传回的JSON数据
     * 返回值：无
     * 功能说明：通过解析JSON信息更逊省信息
     * @param JsonData JSON数据 {"10101": "北京","10102": "上海"}
     */
    public void UpdateProvinceData(String JsonData){
        runOnUiThread(() -> {
            try {
                //进行JSON数据的反序列化
                JSONObject param = JSONObject.parseObject(JsonData);
                //将ProvinceID编号提取出来
                Set<String> provinceKey = param.keySet();
                Toast.makeText(MainActivity.this,"JSON省信息获取成功",Toast.LENGTH_SHORT).show();

                for(String key:provinceKey){
                    //将数据以key:provinceName存储至provinceList
                    String provinceName = param.getString(key);
                    provinceMap.put(provinceName,key);
                    provinceArray.add(provinceName);
                }
                //进行适配器的操作
                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_dropdown_item_1line, provinceArray);
                spinner_prov.setAdapter(arrayAdapter);
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this,"JSON解析失败",Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * 名称：组件初始化函数
     * 参数：无
     * 返回值：无
     */
    public void initView(){
        spinner_city = findViewById(R.id.spiner1_city);
        spinner_prov = findViewById(R.id.spiner1_province);
        textView = findViewById(R.id.tv_weather);
        linearLayout = findViewById(R.id.bg_control);

        //设定spinner_prov的点击事件
        spinner_prov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取比如    "北京"的省名称，并获得于此对应的ProvinceID
                provinceId = provinceMap.get(parent.getItemAtPosition(position).toString());
                getHTTPLink(searchCityURI+provinceId+searchCityURI_suffix,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取比如    "北京"的市区名称，并获得于此对应的CityID
                String cityName = parent.getItemAtPosition(position).toString();
                //直辖市以及非直辖市的获取区别需要重视
                cityID = cityMap.get(cityName);
                //通过拼接的方式进行所在市区的天气查询
                //直辖市=searchWeatherURI+"01"+cityID+searchWeatherURI_suffix
                //非直辖市=searchWeatherURI+cityID+"01"+searchWeatherURI_suffix
                String link = "";
                if(cityName.equals("北京")||cityName.equals("上海")||cityName.equals("重庆")||cityName.equals("天津")){
                    link = searchWeatherURI+provinceId+"01"+cityID+searchWeatherURI_suffix;
                    getHTTPLink(link,2);
                }else{
                    link=searchWeatherURI+provinceId+cityID+"01"+searchWeatherURI_suffix;
                    getHTTPLink(link,2);
                }
                Toast.makeText(MainActivity.this,link,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


}
