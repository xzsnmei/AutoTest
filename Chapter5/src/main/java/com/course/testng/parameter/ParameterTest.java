package com.course.testng.parameter;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParameterTest {
    /**
     * xml参数化测试
     */
    @Test
    @Parameters({"name", "age"})
    public void parameterTest1(String name, int age) {
        System.out.println("姓名=" + name + " " + "年龄=" + age);
    }
}
