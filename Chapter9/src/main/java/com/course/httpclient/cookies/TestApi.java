package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
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

public class TestApi {
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
    public void loginGetCookies() throws IOException {
        String uri = bundle.getString("test.login");
        String result;
        String username = bundle.getString("test.login.username");
        String password = bundle.getString("test.login.password");
        String login_uri = this.url + uri + "?" + "username=" + username + "&" + "password=" + password;
        HttpPost post = new HttpPost(login_uri);
        // 执行请求前设置CookieStore
        this.store = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
        HttpResponse response = httpClient.execute(post);
        // 读取响应Cookie信息
        List<Cookie> CookieList = store.getCookies();
        int i = CookieList.toArray().length;
        System.out.println("CookieList长度为：" + i);
        for (Cookie cookie : CookieList) {
            String cookie_name = cookie.getName();
            String cookie_value = cookie.getValue();
            System.out.println("cookie_name=" + cookie_name + " cookie_value=" + cookie_value);
        }
        System.out.println("输出CookieList:" + CookieList);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println("响应结果：" + result);

    }

    @Test(dependsOnMethods = "loginGetCookies")
    public void getUserList() throws IOException {
        String uri = bundle.getString("test.getUserList");
        String result;
        String username = bundle.getString("test.login.username");
        String password = bundle.getString("test.login.password");
        String getUserList_uri = this.url + uri;
        HttpPost post = new HttpPost(getUserList_uri);
        // 设置请求头信息: headers
        post.setHeader("Content-Type", "application/json");
        // 添加请求参数json格式：需引入json对象
        JSONObject param = new JSONObject();
        param.put("userName", username);
        param.put("passWord", password);
        //将json入参转换为string，然后添加到post请求中
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(post);
        result = EntityUtils.toString(response.getEntity());
        System.out.println("返回结果：" + result);
    }

    @Test
    public void addEmployee() throws IOException {
        String uri = bundle.getString("test.add.employee");
        String result;
        // (emp_id,emp_no,dept_id,emp_name,emp_age,emp_salary)
        int emp_id = 11;
        String emp_no = "3";
        String dept_id = "12";
        String emp_name = "测试";
        String emp_age = "28";
        float emp_salary = 1200;
        String addEmployee_uri = this.url + uri;
        HttpPost post = new HttpPost(addEmployee_uri);
        // 设置请求头信息: headers
        post.setHeader("Content-Type", "application/json");
        // 添加请求参数json格式：需引入json对象
        JSONObject param = new JSONObject();
        param.put("emp_id", emp_id);
        param.put("emp_no", emp_no);
        param.put("dept_id", dept_id);
        param.put("emp_name", emp_name);
        param.put("emp_age", emp_age);
        param.put("emp_salary", emp_salary);
        //将json入参转换为string，然后添加到post请求中
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(post);
        result = EntityUtils.toString(response.getEntity());
        System.out.println("返回结果：" + result);
    }

    @Test
    public void updateEmployee() throws IOException {
        String uri = bundle.getString("test.update.employee");
        String result;
        // (emp_id,emp_no,dept_id,emp_name,emp_age,emp_salary)
        int emp_id = 10;
        String emp_no = "2";
        String dept_id = "12";
        String emp_name = "测试1";
        String emp_age = "35";
        float emp_salary = 1200;
        String updateEmployee_uri = this.url + uri;
        HttpPost post = new HttpPost(updateEmployee_uri);
        // 设置请求头信息: headers
        post.setHeader("Content-Type", "application/json");
        // 添加请求参数json格式：需引入json对象
        JSONObject param = new JSONObject();
        param.put("emp_id", emp_id);
        param.put("emp_no", emp_no);
        param.put("dept_id", dept_id);
        param.put("emp_name", emp_name);
        param.put("emp_age", emp_age);
        param.put("emp_salary", emp_salary);
        //将json入参转换为string，然后添加到post请求中
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(post);
        result = EntityUtils.toString(response.getEntity());
        System.out.println("返回结果：" + result);
    }

    @Test
    public void deleteEmployee() throws IOException {
        String uri = bundle.getString("test.delete.employee");
        String result;
        int emp_id = 10;
        String deleteEmployee_uri = this.url + uri + "?" + "emp_id=" + emp_id;
        HttpGet get = new HttpGet(deleteEmployee_uri);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(get);
        result = EntityUtils.toString(response.getEntity());
        System.out.println("返回结果：" + result);
    }

}
