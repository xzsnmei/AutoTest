package com.course.utils;

import com.course.model.EmumInterfaceName;

import java.util.ResourceBundle;

public class ConfigFile {
    // 读取接口或场景配置文件：application.properties文件
    private static ResourceBundle bundle = ResourceBundle.getBundle("application");

    public static String getUrl(EmumInterfaceName name) {
        // 拼接配置文件中的测试基础url和uri
        String address = bundle.getString("test.url");
        String uri = "";
        String testUrl;

        if (name == EmumInterfaceName.LOGIN) {
            uri = bundle.getString("login.uri");
        }
        if (name == EmumInterfaceName.GETUSERINFO) {
            uri = bundle.getString("getUserInfo.uri");
        }
        if (name == EmumInterfaceName.GETUSERLIST) {
            uri = bundle.getString("getUserList.uri");
        }
        if (name == EmumInterfaceName.ADDUSER) {
            uri = bundle.getString("addUser.uri");
        }
        if (name == EmumInterfaceName.UPDATEUSERINFO) {
            uri = bundle.getString("updateUserInfo.uri");
        }
        if (name == EmumInterfaceName.DELETEUSER) {
            uri = bundle.getString("deleteUser.uri");
        }
        testUrl = address + uri;
        return testUrl;
    }

}
