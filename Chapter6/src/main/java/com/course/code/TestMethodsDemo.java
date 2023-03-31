package com.course.code;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestMethodsDemo {
    @Test
    public void test1(){
        Assert.assertEquals(1,1);
    }
    @Test
    public void test2(){
        Assert.assertEquals(1,2);
    }
    @Test
    public void test3(){
        Assert.assertEquals("aaa","aaa");
    }
    @Test(expectedExceptions = RuntimeException.class)
    public void logDemo(){
        Reporter.log("这是自定义日志Report");
        throw new RuntimeException("这是自定义运行时异常信息");
    }
}
