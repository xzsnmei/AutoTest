package com.course.testng;

import org.testng.annotations.Test;
import sun.font.TrueTypeFont;

/**
 * 忽略测试: enable 值控制是否执行测试用例
 */
public class IgnoreTest {
    @Test
    public void ignoreTest1() {
        System.out.println("Ignore test1 执行！");
    }

    @Test(enabled = false)
    public void ignoreTest2() {
        System.out.println("Ignore test2 执行！");
    }

    @Test(enabled = true)
    public void ignoreTest3() {
        System.out.println("Ignore test3 执行！");
    }
}
