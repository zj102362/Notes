package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 22:29
 */
@Slf4j
public class Test10 {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        lock.lock();
        try {
            condition1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }


    }
}