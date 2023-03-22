package com.course.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {
    @Test(groups = "server")
    public void test1() {
        System.out.println("服务端组:测试方法test1");
    }

    @Test(groups = "server")
    public void test2() {
        System.out.println("服务端组:测试方法test2");
    }

    @Test(groups = "client")
    public void test3() {
        System.out.println("客户端组:测试方法test3");
    }

    @Test(groups = "client")
    public void test4() {
        System.out.println("客户端组:测试方法test4");
    }

    @BeforeGroups("server")
    public void beforeGroupsOnServer() {
        System.out.println("服务端组运行之前执行:beforeGroupsOnServer");
    }

    @AfterGroups("server")
    public void afterGroupsOnServer() {
        System.out.println("服务端组运行之后执行:afterGroupsOnServer");
    }

    @BeforeGroups("client")
    public void beforeGroupsOnClient() {
        System.out.println("客户端组运行之前执行:beforeGroupsOnClient");
    }

    @AfterGroups("client")
    public void afterGroupsOnClient() {
        System.out.println("客户端组运行之后执行:afterGroupsOnClient");
    }
}
