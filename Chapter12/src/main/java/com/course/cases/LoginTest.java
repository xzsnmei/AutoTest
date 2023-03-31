package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.EmumInterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import com.course.utils.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.course.utils.ConfigFile.getUrl;

public class LoginTest {

    @BeforeTest(groups = "loginTrue", description = "测试准备工作")
    public void beforeTest() {
        //获取配置接口路径
        TestConfig.loginUrl = getUrl(EmumInterfaceName.LOGIN);
        TestConfig.getUserInfoUrl = getUrl(EmumInterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = getUrl(EmumInterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = getUrl(EmumInterfaceName.ADDUSER);
        TestConfig.updateUserInfoUrl = getUrl(EmumInterfaceName.UPDATEUSERINFO);
        TestConfig.deleteUserUrl = getUrl(EmumInterfaceName.DELETEUSER);
        TestConfig.store = new BasicCookieStore();
//        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
    }

    @Test(groups = "loginTrue", description = "登录接口测试-登录成功")
    public void loginTrue() throws IOException {
        // 查询数据库case数据
        SqlSession session = DatabaseUtil.getSqlSession();
        // 执行sql语句返回LoginCase数据
        LoginCase loginCase = session.selectOne("loginCase", 1);
        if (loginCase != null) {
            System.out.println(loginCase.toString());
            // 发送请求

            String result = getResult(loginCase);
            // 验证结果
            Assert.assertEquals(loginCase.getExpected(), result);
        } else
            System.out.println("未查询到测试用例：登录成功");
        System.out.println(TestConfig.loginUrl);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("userName", loginCase.getUserName());
        param.put("password", loginCase.getPassword());
        // 设置头信息
        post.setHeader("Content-type", "application/json");
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        //设置Cookie
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        String result;//存放结果
        HttpResponse response = TestConfig.httpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(TestConfig.store);
        System.out.println(result);
        return result;
    }

    @Test(groups = "loginFalse", description = "登录接口测试-登录失败")
    public void loginFalse() throws IOException {
        // 查询数据库case数据
        SqlSession session = DatabaseUtil.getSqlSession();
        // 执行sql语句返回LoginCase数据: 登录接口测试用例
        LoginCase loginCase = session.selectOne("loginCase", 2);
        if (loginCase != null) {
            System.out.println(loginCase.toString());
        } else
            System.out.println("未查询到测试用例：登录失败");
        System.out.println(TestConfig.loginUrl);
    }
}