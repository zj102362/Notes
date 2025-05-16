package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/26 22:08
 */
@Slf4j
public class Test14 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.info("t1");
            LockSupport.park();
        });


        Thread t2 = new Thread(() -> {
            log.info("t2");
            LockSupport.unpark(t1);
        });

        t1.start();
        t2.start();
    }
}