package com.course.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {
    /**
     * 参数化测试：对象DateProvider
     * 通过方法传递参数
     */
    @Test(dataProvider = "data")
    public void testDataProvider(String name, int age) {
        System.out.println("姓名=" + name + " " + "年龄=" + age);
    }

    @DataProvider(name = "data")
    public Object[][] providerData() {
        Object[][] O = new Object[][]{
                {
                        "张三", 10
                },
                {
                        "李四", 12
                },
                {
                        "王二", 15
                }
        };
        return O;
    }

    // 通过方法名传递参数
    @Test(dataProvider = "methodData")
    public void test1(String name, int age) {
        System.out.println("test111方法：姓名=" + name + " 年龄=" + age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name, int age) {
        System.out.println("test222方法：姓名=" + name + " 年龄=" + age);
    }

    @DataProvider(name = "methodData")
    public Object[][] methodDataTest(Method method) {
        Object[][] result = null;
        if (method.getName().equals("test1")) {
            result = new Object[][]{
                    {"小于", 10},
                    {"小徐", 12}
            };
        } else if (method.getName().equals("test2")) {
            result = new Object[][]{
                    {"小梦", 10},
                    {"小许", 15}
            };
        }
        return result;
    }
}
