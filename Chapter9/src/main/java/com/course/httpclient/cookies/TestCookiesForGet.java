package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestCookiesForGet {
    //读取配置文件信息：application.properties
    private String url;
    private ResourceBundle bundle;
    // 声明私有变量存储Cookie信息
    private Cookie cookie;
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
        //存储cookies信息变量
        this.store = new BasicCookieStore();
        //设置cookies信息
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
        HttpGet get = new HttpGet(testUrl);
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);

        //获取cookies信息
        List<Cookie> cookieList = store.getCookies();

        System.out.println("获取到的Cookie信息：" + cookieList);

        for (Cookie cookie : cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            this.cookie = cookie;
            System.out.println("cookie name = " + name
                    + ";  cookie value = " + value);
            System.out.println("cookie信息：" + this.cookie);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        String uri = bundle.getString("test.getWithCookies");
        //拼接最终的测试地址
        String testUrl = this.url + uri;
//        this.store = new BasicCookieStore();

//        CookieStore cookieStore=new BasicCookieStore();
//        cookieStore.addCookie(this.cookie);

//        this.store.addCookie(this.cookie);
        //声明一个post的方法
        HttpGet get = new HttpGet(testUrl);

//        //添加参数
//        JSONObject param = new JSONObject();
//        param.put("name", "huhansan");
//        param.put("age", "18");

        //设置请求头信息,设置header
        get.setHeader("content-type", "application/json");

//        //将参数信息添加到方法中
//        StringEntity entity = new StringEntity(param.toString(), "utf-8");
//        httppost.setEntity(entity);

        //声明一个client对象，用来进行方法的执行,并设置cookies信息
//        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.store).build();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.store).build();

        //执行post的方法并得到响应结果
        CloseableHttpResponse response3 = httpclient.execute(get);

        //就是判断返回结果是否符合预期
        int statusCode = response3.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);

        String result = EntityUtils.toString(response3.getEntity(), "utf-8");

        if (statusCode == 200) {
            System.out.println(result);
        } else {
            System.out.println("访问/get/with/cookies接口失败");
        }

//        //将返回的响应结果字符串转化为json对象
//        JSONObject resultjson = new JSONObject(result);
//
//        //获取到结果值
//        String success = (String) resultjson.get("huhansan");
//        System.out.println(success);
//        Assert.assertEquals("success", success);

    }

    @Test()
    public void testGetWithParam1() throws IOException {
        String uri = bundle.getString("test.getWithParam");
        String start = bundle.getString("test.param.start");
        String end = bundle.getString("test.param.end");
        //拼接最终的测试地址
        String testUrl = this.url + uri + "?start=" + start + "&end=" + end;
        //声明一个get的方法
        HttpGet get = new HttpGet(testUrl);
        //设置请求头信息,设置header
        get.setHeader("content-type", "application/json");

        //声明一个client对象，用来进行方法的执行,并设置cookies信息
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //执行post的方法并得到响应结果
        CloseableHttpResponse response = httpclient.execute(get);

        //就是判断返回结果是否符合预期
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        if (statusCode == 200) {
            System.out.println(result);
        } else {
            System.out.println("访问/get/with/cookies接口失败");
        }
    }

    @Test()
    public void testGetWithParam2() throws IOException {
        String uri = bundle.getString("test.getWithParam");
        String start = bundle.getString("test.param.start");
        String end = bundle.getString("test.param.end");
        //拼接最终的测试地址
        String testUrl = this.url + uri + "/" + start + "/" + end;
        //声明一个get的方法
        HttpGet get = new HttpGet(testUrl);
//        //设置请求头信息,设置header
//        get.setHeader("content-type", "application/json");

        //声明一个client对象，用来进行方法的执行,并设置cookies信息
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //执行post的方法并得到响应结果
        CloseableHttpResponse response = httpclient.execute(get);

        //就是判断返回结果是否符合预期
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        if (statusCode == 200) {
            System.out.println(result);
        } else {
            System.out.println("访问/get/with/cookies接口失败");
        }
    }

}
