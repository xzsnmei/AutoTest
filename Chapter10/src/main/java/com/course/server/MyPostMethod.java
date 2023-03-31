package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "MyPostMethod")
@RequestMapping("/v1")
public class MyPostMethod {
    //接收cookies信息
    private static Cookie cookie;

    //用户登录成功获取到cookiew,然后再访问其它接口获取列表
    @RequestMapping(value = "/login", name = "登录")
    @ApiOperation(value = "登录接口，获取cookies信息", httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password) {
        // 实际业务：用户和密码从数据库获取
        if (username.equals("张三") && password.equals("1234")) {
            cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "恭喜您成功获取cookie!!!";
        }
        return "获取cookie失败：用户名或密码错误";
    }
    // 返回用户列表：对象User
    // 1、登录成功获取列表：登录过期校验-通过cookie验证session是否过期
    // 2、管理员才可以获取列表：身份校验-验证用户y
    // 什么是Cookie？答：为了辨别用户身份,进行Session跟踪而储存在用户本地终端上的数据(通常经过加密)
    /**
     * swagger3注解
     */
    @PostMapping(value = "/getUserList", name = "获取用户列表")
    @ApiOperation(value = "获取用户列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                              @RequestBody User u) {
        User user;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            // 注意"=="和.equals的区别
            if (c.getName().equals("login")
                    && c.getValue().equals("true")
                    && u.getUserName().equals("张三")
                    && u.getPassWord().equals("1234")) {
                user = new User();
                user.setUserName("张三");
                user.setAge("15");
                user.setSex("男");
                user.setPassWord("1234");
                return user.toString();
            }
        }
        return "参数不合法";
    }
}
