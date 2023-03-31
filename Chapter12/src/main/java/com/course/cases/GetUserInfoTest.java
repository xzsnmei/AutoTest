package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
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
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue", description = "获取userId为1的用户")
    public void getUserInfo() throws IOException, InterruptedException {
        // 查询数据库case数据
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 1);
        if (getUserInfoCase != null) {
            System.out.println(getUserInfoCase.toString());

            JSONArray resultJsonArray = getJsonResult(getUserInfoCase);
            // 线程休息
            Thread.sleep(2000);
            System.out.println("result=" + resultJsonArray);
            //取数据库case期望结果，即SQLmapper文件中的id，根据id查询case预期的用户信息
            User user = session.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);
            List userList = new ArrayList();
            userList.add(user);
            JSONArray userJsonArry = new JSONArray(userList);
            System.out.println("userJsonArry=" + userJsonArry);

//            // 方法1：结果的顺序有可能不同：解决方案是取出特定Json
//            JSONArray resultJsonArray1 = new JSONArray(resultJsonArray.getString(0));
//            // 方法1：返回结果顺序和json均相同仍然断言失败：解决方案-转换为String后再比较对否相同
//            Assert.assertEquals(userJsonArry.toString(), resultJsonArray1.toString());

            // 方法2优化：返回结果顺序和json均相同仍然断言失败：解决方案-转换为String后再比较是否相同（仅为单条数据，多条数据需参考方法1）
            Assert.assertEquals(userJsonArry.toString(), resultJsonArray.toString());
        } else
            System.out.println("未查询到测试用例：查询用户信息成功");
        System.out.println(TestConfig.getUserInfoUrl);
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        post.setHeader("Content-Type", "application/json");
        // 根据userId查询用户信息
        param.put("id", getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        TestConfig.httpClient = HttpClients.custom().setDefaultCookieStore(TestConfig.store).build();
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        // 方法1：将String result转换为List,再转换为JsonArry
//        List<String> resultList = Arrays.asList(result);
//        JSONArray resultJsonArry = new JSONArray(resultList);
//        return resultJsonArry;
        // 方法2： 根据接口返回数据类型为List<User>，且仅返回一条时，可直接将String result转换为JSONArray
        JSONArray resultJsonArry = new JSONArray(result);
        return resultJsonArry;

    }
}
