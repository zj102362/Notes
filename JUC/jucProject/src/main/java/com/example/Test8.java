package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 22:04
 */
@Slf4j
public class Test8 {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        lock.lock();

        Thread t1 = new Thread(() -> {
            try {
                log.info("尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info("没有获取到锁");
                e.printStackTrace();
                return;
            }
            try {
                log.info("获取到锁了");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1");
        t1.start();

        try {
            Thread.sleep(1000);
            log.info("打断t1");
            t1.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
} 