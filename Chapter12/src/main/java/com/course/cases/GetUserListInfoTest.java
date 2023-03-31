package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUserListInfoTest {
    @Test(dependsOnGroups = "loginTrue", description = "获取性别为男的用户")
    public void getUserListInfo() throws IOException, InterruptedException {
        // 查询数据库case数据
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase", 1);
        if (getUserListCase != null) {
            System.out.println(getUserListCase.toString());
            // 发送请求获取结果
            JSONArray resultJson = getJsonArrayResult(getUserListCase);
            // 线程休息：等待上一条语句执行成功
            Thread.sleep(2000);
            // 验证结果-根据测试case查询预期执行结果：定义 list,泛型为对象User
            List<User> userList = session.selectList(getUserListCase.getExpected(), getUserListCase);
            for (User u : userList) {
                System.out.println("case预期用户: user=" + u.toString());
            }
            // 将userList转成JsonArray
            JSONArray userListJson = new JSONArray(userList);
            System.out.println("case预期用户数为“：" + userListJson.length());
            System.out.println("接口返回的用户数为“：" + resultJson.length());
            // 校验执行添加用户数和添加成功数是否相等: 校验长度是否相同
            Assert.assertEquals(userListJson.length(), resultJson.length());
            // 若断言结果为真继续执行: 断言失败后不会再继续执行
            // 长度相同：取出对象，判断对象是否相同
            for (int i = 0; i < resultJson.length(); i++) {
                JSONObject actual = (JSONObject) resultJson.get(i);
                JSONObject expect = (JSONObject) userListJson.get(i);
                // 校验对象是否一样
                Assert.assertEquals(actual.toString(), expect.toString());
            }
        } else
            System.out.println("未查询到测试用例：查询用户列表信息");
        System.out.println(TestConfig.getUserListUrl);
    }

    private JSONArray getJsonArrayResult(GetUserListCase getUserListCase) throws IOException {

        HttpPost post = new HttpPost(TestConfig.getUserListUrl);

        JSONObject param = new JSONObject();
        param.put("userName", getUserListCase.getUserName());
        param.put("sex", getUserListCase.getSex());
        param.put("age", getUserListCase.getAge());
        post.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println("返回结果List<User>：result=" + result);
        // json对象做Json转换
        JSONArray resultJsonArry = new JSONArray(result);
        System.out.println("返回结果转换为JSONArray：resultJsonArry =" + resultJsonArry);
        return resultJsonArry;
    }
}
