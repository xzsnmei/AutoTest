package com.course.testng;

import org.testng.annotations.Test;

public class TimeOut {
    /**
     * 超时测试
     */
    @Test(timeOut = 3000) //单位为毫秒
    public void testSuccess() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test(timeOut = 2000) //单位为毫秒:期望超时2秒，结果超时3秒，故测试结果失败
    public void testFailed() throws InterruptedException {
        Thread.sleep(3000);
    }
}
