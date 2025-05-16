package com.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/26 21:49
 */
@Slf4j
public class Test12 {
    private static final Object lock = new Object();
    private static Boolean flag = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock){
                try {
                    while (!flag){
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("t1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock){
                log.info("t2");
                flag = true;
                lock.notify();
            }
        }, "t1");
        t2.start();
        t1.start();
    }
} 