package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {
    @Test(dependsOnGroups = "loginTrue", description = "更新用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {
        // 查询数据库case数据
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserCase", 1);
        if (updateUserInfoCase != null) {
            System.out.println(updateUserInfoCase.toString());
            int result = getResult(updateUserInfoCase);
            // 线程休息：等待上一条语句执行成功
            Thread.sleep(2000);
            User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
            // 断言查询更新后用户数据不为空
            Assert.assertNotNull(user);
            // 断言接口返回更新数据不为空
            Assert.assertNotNull(result);
        } else
            System.out.println("未查询到测试用例：更新用户信息成功");
        System.out.println(TestConfig.updateUserInfoUrl);
    }

    @Test(dependsOnGroups = "loginTrue", description = "逻辑删除用户信息")
    public void deleteUser() throws IOException, InterruptedException {
        // 查询数据库case数据
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase deleteUserInfoCase = session.selectOne("updateUserCase", 2);
        if (deleteUserInfoCase != null) {
            System.out.println(deleteUserInfoCase.toString());
            int result = getResult(deleteUserInfoCase);
            // 线程休息：等待上一条语句执行成功
            Thread.sleep(2000);
            User user = session.selectOne(deleteUserInfoCase.getExpected(), deleteUserInfoCase);
            System.out.println("用户信息：user=" + user);
            Assert.assertNotNull(user);
            Assert.assertNotNull(result);
        } else
            Reporter.log("未查询到测试用例：删除用户");
        System.out.println(TestConfig.updateUserInfoUrl);
    }

    private int getResult(UpdateUserInfoCase updateUserInfo) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        // 设置header
        post.setHeader("content-type", "application/json");
        // 设置请求入参
        JSONObject param = new JSONObject();
        param.put("id", updateUserInfo.getUserId());
        param.put("userName", updateUserInfo.getUserName());
        param.put("password", updateUserInfo.getPassword());
        param.put("age", updateUserInfo.getAge());
        param.put("sex", updateUserInfo.getSex());
        param.put("permission", updateUserInfo.getPermission());
        param.put("isDelete", updateUserInfo.getIsDelete());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        // 模拟httpclient
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        // 执行post请求，接收返回响应实体
        HttpResponse response = TestConfig.httpClient.execute(post);
        // 提取响应结果: 请求结果接收为字符串，需要将结果转换为Integer
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println("输出更新接口返回结果：" + result);
        // parseInt-返回结果为有符号的整数值；parseUnsignedInt-返回结果为无符号的整数值
        return Integer.parseInt(result);
    }
}
