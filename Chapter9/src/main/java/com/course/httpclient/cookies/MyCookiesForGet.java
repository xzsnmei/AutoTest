package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
    //读取配置文件信息：application.properties
    private String url;
    private ResourceBundle bundle;
    // 声明私有变量存储Cookie信息
    private CookieStore store;

    @BeforeTest
    public void beforeTest() {
        Reporter.log("从配置文件中获取base_url");
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        Reporter.log("读取配置文件，拼接获取Cookies信息接口url");
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url + uri;

        Reporter.log("声明client对象：模拟client端请求获取Cookies接口");
        HttpGet get = new HttpGet(testUrl);
//        HttpClient client = new DefaultHttpClient();
        // HttpClient 对象不提供获取Cookie信息方法
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);

        //获取Cookie信息
        this.store = client.getCookieStore();
        //泛型 List<Cookie>
        List<Cookie> CookieList = store.getCookies();

        // 先设置一个与CookieList里的元素类型相同的变量cookie，该变量初始值为CookieList[0],然后进入循环体
        // 第二次循环cookie=CookieList[1]，进入循环体，以此类推
        for (Cookie cookie : CookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie_name=" + name + " cookie_value=" + value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})//依赖获取Cookie信息接口
    public void testGetWithCookies() throws IOException {
        String uri = bundle.getString("test.getWithCookies");
        String testUrl = this.url + uri;
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(this.store);
        // client访问get请求
        HttpResponse response = client.execute(get);
        // 获取响应的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("响应状态码：statusCode=" + statusCode);

        if (statusCode == 200) {
            // 获取响应结果实体信息并转换为String类型
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }

    }
}
