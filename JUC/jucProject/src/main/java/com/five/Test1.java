package com.five;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/27 10:44
 */
@Slf4j
public class Test1 {
    static boolean flag = true;
    static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true){
                synchronized (lock){
                    if (!flag){
                        break;
                    }
                }
            }
        }).start();

        Thread.sleep(1000);
        synchronized (lock){
            flag = false;
        }
        log.info("停止");
    }
}