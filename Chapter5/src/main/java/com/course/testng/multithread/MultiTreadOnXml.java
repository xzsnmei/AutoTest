package com.course.testng.multithread;

import org.testng.annotations.Test;

public class MultiTreadOnXml {
    /**
     * 多线程测试：通过xml配置文件实现
     */
    @Test()
    public void test1() {
        // 打印当前线程Id
        System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());

    }

    @Test()
    public void test2() {
        // 打印当前线程Id
        System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());

    }

    @Test()
    public void test3() {
        // 打印当前线程Id
        System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());

    }
}
