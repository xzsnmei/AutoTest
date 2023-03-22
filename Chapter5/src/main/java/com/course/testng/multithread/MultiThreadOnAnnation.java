package com.course.testng.multithread;

import org.testng.annotations.Test;

public class MultiThreadOnAnnation {
    /**
     * 多线程测试: 通过注解实现多线程
     * 线程数量：invocationCount
     * 线程池数量：threadPoolSize=3
     * 注1：多线程时执行测试用例时可以提高执行效率，但无法顺序无法控制
     * 注2：使用多线程时不要有跨线程关联，如果有需要加锁，难度大
     */
    @Test(invocationCount = 10, threadPoolSize = 3)
    public void test() {
        System.out.println(1);
        // 打印当前线程Id
        System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());

    }
}
