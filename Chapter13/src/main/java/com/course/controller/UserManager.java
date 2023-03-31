package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "v1.0", description = "用户管理系统")
// 访问路径
@RequestMapping("v1")
public class UserManager {
    @Autowired //自动new对象：批量处理数据
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口", tags = "用户管理系统", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    // 登录接口需返回Cookie
    public boolean login(HttpServletResponse response, @RequestBody User user) {
        // 查询数据库是否存在用户
        int i = template.selectOne("login", user);
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        log.info("查询结果是：" + i);
        if (i == 1) {
            // 若查询到用户，日志记录登录用户名
            log.info("登录的用户是：" + user.getUserName());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户接口")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    // 判断用户是否添加成功，成功则返回true，否则返回false: 如何校验添加用户数量和信息？
    public boolean addUser(HttpServletRequest request, @RequestBody User user) {
        // 验证Cookies是否通过: 封装校验
        Boolean x = verifyCookies(request);
        // 查询数据库数据是否存在，不存在则插入，存在则不插入
        List<User> users = template.selectList("getUserInfo", user);
        System.out.println("users=" + users);
        if ((null != users) && (users.size() == 0)) {// 定义添加用户总数
            int result = 0;
            if (x == true) {
                result = template.insert("addUser", user);
            }
            // 判断添加是否成功: 添加数量>0即添加成功
            if (result > 0) {
                // 日志记录：添加用户数量
                log.info("添加用户的数量是：" + result);
                return true;
            }
        }
        System.out.println("数据库已存在改数据");
        return false;
    }

    @ApiOperation(value = "获取用户/列表信息接口", tags = "用户管理系统")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    // 返回查询到的用户信息：如何校验返回多个用户信息和数量是否正确？
    public List<User> getUserInfo(HttpServletRequest request, @RequestBody User user) {
        //验证Cookies是否正确
        Boolean c = verifyCookies(request);
        if (c == true) {
            // 实现获取多个用户或单个用户场景
            List<User> users = template.selectList("getUserInfo", user);
            System.out.println("返回数据：" + users);
            log.info("getUserInfo获取到的用户数量是" + users.size());
            return users;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "更新/删除用户", tags = "用户管理系统")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    // 返回更新用户数：如何校验更新用户和实际落库数量是否正确？
    public int updateUser(HttpServletRequest request, @RequestBody User user) {
        Boolean c = verifyCookies(request);
        // 定义查询数据库更新用户数量
        int i = 0;
        if (c == true) {
            i = template.update("updateUserInfo", user);
            log.info("更新数据条目数为：" + i);
            // 更新成功，返回更新数据
            return i;
        }
        // 0:表示未更新失败
        return 0;
    }


    private Boolean verifyCookies(HttpServletRequest request) {
        // 封装校验：返回为Boolean
        Cookie[] cookies = request.getCookies();
        // 判断1：Cookie是否为空
        if (Objects.isNull(cookies)) {
            log.info("Cookies为空");
            return false;
        }
        // 判:2：cookie值是否正确
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                log.info("Cookies验证通过");
                return true;
            }
        }
        // 未进入判2：视为校验失败
        return false;
    }
}
