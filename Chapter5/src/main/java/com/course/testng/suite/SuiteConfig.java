package com.course.testng.suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class SuiteConfig {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("before Suite 运行了");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("after Suite 运行了");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("before Test 运行了");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("after Test 运行了");
    }
}
