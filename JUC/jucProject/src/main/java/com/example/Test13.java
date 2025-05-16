package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/26 22:00
 */
@Slf4j
public class Test13 {
    static final ReentrantLock  lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static boolean flag = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                while (!flag){
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.info("t1");
            }finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                flag = true;
                log.info("t2");
                condition.signal();
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
    }
}