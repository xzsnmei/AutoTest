package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
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
import org.testng.annotations.Test;

import java.io.IOException;
import java.rmi.server.ExportException;

public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue", description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        // 查询数据库case数据
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase", 1);
        if (addUserCase != null) {
            System.out.println(addUserCase.toString());
        } else
            System.out.println("未查询到测试用例：addUserCse");
        System.out.println(TestConfig.addUserUrl);

        // 请求添加用户接口，验证请求结果
        String result = getResult(addUserCase);
        // 等待执行完成
        Thread.sleep(3000);
        if (result.equalsIgnoreCase("true")) {
            //验证返回结果
            User user = session.selectOne("addUser", addUserCase);
            if (user != null) {
                System.out.println(user.toString());
                Assert.assertEquals(addUserCase.getExpected(), result);
            }
        } else throw new ExportException("添加用户信息失败");
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName", addUserCase.getUserName());
        param.put("password", addUserCase.getPassword());
        param.put("age", addUserCase.getAge());
        param.put("sex", addUserCase.getSex());
        param.put("permission", addUserCase.getPermission());
        param.put("isDelete", addUserCase.getIsDelete());
        // 设置头信息
        post.setHeader("Content-type", "application/json");
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        //设置Cookie
        System.out.println("登录Cookie:" + TestConfig.store);
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();

        HttpResponse response = TestConfig.httpClient.execute(post);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println("返回添加用户结果：" + result);
        return result;
    }
}
