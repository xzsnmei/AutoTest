package com.course.server;

import com.sun.javafx.collections.MappingChange;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/", description = "MyGetMthod")
public class MyGetMethod {
    /**
     * swagger3注解
     */
    @GetMapping(value = "/getCookies")
    @ApiOperation(value = "GET:获取Cookies", tags = "@ApiOperation: tags=GetTestDemo", httpMethod = "GET")
    public String getCookies(HttpServletResponse response) {
        //HttpServletRequest: 封装请求信息类
        //HttpServletResponse: 封装响应信息类
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        return "请求成功: 恭喜您获得cookie";
    }

    /**
     * swagger2注解
     * 要求客户端携带cookie访问
     * 这是一个需要携带cookies信息才能访问的get请求
     */

    @RequestMapping(value = "/get/with/cookies", method = RequestMethod.GET)
    @ApiOperation(value = "Get:携带cookie", tags = "@ApiOperation: tags=GetTestDemo", httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return "请确认cookie信息已携带并正确传递";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals(("true"))) {
                return "恭喜携带Cookies访问成功";
            }
        }
        return "Cookies信息错误";
    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式url: key=value&key=value
     * 模拟获取商品列表
     */
    @GetMapping(value = "/get/with/param")
    @ApiOperation(value = "GET请求格式1:客户端url携带参数", tags = "@ApiOperation: tags=GetTestDemo", httpMethod = "GET")
    public Map<String, Integer> getList(@ApiParam("list开始") Integer start,
                                        @RequestParam Integer end) {
        // 实际工作中 start和end需要从数据库中取值
        Map<String, Integer> myList = new HashMap<>();

        myList.put("鞋", 400);
        myList.put("干脆面", 5);
        myList.put("衬衫", 300);

        return myList;
    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第二种实现方式uri: ip:port/get/with/param/10/20
     * 模拟获取商品列表
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "Get请求格式2:客户端携带参数访问", tags = "@ApiOperation: tags=GetTestDemo", httpMethod = "GET")
    public Map mygetList(@PathVariable Integer start,
                         @PathVariable Integer end) {
        // 实际工作中 start和end需要从数据库中取值
        Map<String, Integer> myList = new HashMap<>();

        myList.put("鞋子", 400);
        myList.put("干脆面", 5);
        myList.put("衬衫", 300);

        return myList;
    }
}
