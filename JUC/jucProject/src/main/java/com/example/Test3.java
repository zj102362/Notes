package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 14:50
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            log.info("park");
            LockSupport.park();
        });
        thread.start();

        Thread.sleep(2000);
        log.info("unpark");
        LockSupport.unpark(thread);
    }
} 