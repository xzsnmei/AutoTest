package com.course.testng;

import org.testng.annotations.*;

public class BasicAnnotation {
    // 基本注解: 用来把方法标记为测试的一部分
    @Test()
    public void test2() {
        // 打印当前线程Id
        System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());

    }
    @Test
    public void testCace1() {
        System.out.println("这是测试用例1");
    }

    @Test
    public void testCace2() {
        System.out.println("这是测试用例2");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod这是在测试方法之前运行的");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod这是在测试方法之后运行的");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass这是在类之前运行的");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass这是在类之后运行的");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite这是测试套件，类之前运行的");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite这是测试套件，套件内所有类运行后执行");
    }

}
