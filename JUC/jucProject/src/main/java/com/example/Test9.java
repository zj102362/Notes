package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 22:10
 */
@Slf4j
public class Test9 {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        lock.lock();
        Thread t1 = new Thread(() ->{
            try {
                if(!lock.tryLock(1, TimeUnit.SECONDS)){
                    log.info("尝试失败");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                log.info("获取到锁了");
            }finally {
                lock.unlock();
            }
        }, "t1");
        t1.start();
        Thread.sleep(2000);
        lock.unlock();
    }
} 