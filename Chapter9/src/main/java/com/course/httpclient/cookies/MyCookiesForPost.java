package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    //读取配置文件信息：application.properties
    private String url;
    private ResourceBundle bundle;
    // 声明私有变量存储Cookie信息
    private CookieStore store;

    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        //从配置文件中获取base_url
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        // 从配置文件中获取Cookies信息接口的相对路径：get请求的url，拼接
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url + uri;

        //测试逻辑代码书写：模拟client端请求获取Cookies接口
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
    public void testPOSTWithCookies() throws IOException {
        String uri = bundle.getString("test.postWithCookies");
        // 拼接最终的测试地址
        String testUrl = this.url + uri;

        //声明client对象，
        DefaultHttpClient client = new DefaultHttpClient();

        // 声明请求方法：POST
        HttpPost post = new HttpPost(testUrl);

        // 添加请求参数json格式：需引入json对象
        JSONObject param = new JSONObject();
        param.put("name", "huhansan");
        param.put("age", "45");

        // 设置请求头信息: headers
        post.setHeader("content-type", "application/json");
        //将json入参转换为string，然后添加到post请求中
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        // 设置Cookies信息
        client.setCookieStore(this.store);
        // 声明对象，存储响应结果
        String result;
        // client执行post请求
        HttpResponse response = client.execute(post);

        // 获取响应的状态码
        Reporter.log("获取响应状态码");
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("响应状态码：statusCode=" + statusCode);

        // 获取响应头信息
        Reporter.log("获取响应头信息");
        String headers = Arrays.toString(response.getHeaders("Content-Type"));
        System.out.println("响应头信息：headers=" + headers);

        // 获取响应结果
        Reporter.log("获取响应结果");
        result = EntityUtils.toString(response.getEntity());
        System.out.println("响应结果：result=" + result);

        //处理结果：判断返回结果是否符合预期
        // 将返回的响应结果字符串转化为json对象
        JSONObject resultToJson = new JSONObject(result);

        // 获取到结果值
        String success = (String) resultToJson.get("huhansan");
        String status = (String) resultToJson.get("status");
        //具体的判断返回结果的值
        Reporter.log("断言返回结果");
        Assert.assertEquals("success", success);
        Assert.assertEquals("1", status);
    }
}
