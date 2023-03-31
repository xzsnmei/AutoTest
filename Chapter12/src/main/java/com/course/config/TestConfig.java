package com.course.config;

import com.course.utils.HttpClient;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestConfig {
    // 定义测试接口实体模型：根据配置文件application.properties中的测试接口
    public static String loginUrl;
    public static String getUserInfoUrl;
    public static String getUserListUrl;
    public static String addUserUrl;
    public static String updateUserInfoUrl;
    public static String deleteUserUrl;
    public static CookieStore store;
    public static CloseableHttpClient httpClient;
}
