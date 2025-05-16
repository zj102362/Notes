package com.example;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 16:37
 */
public class Test7 {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        try {
            lock.lockInterruptibly();
            System.out.println("m1");
            m2();
        }catch (Exception e) {
            e.printStackTrace();
            lock.unlock();
        }

    }

    public static void m2() {
        lock.lock();
        try {
            System.out.println("m2");
        }catch (Exception e) {
            e.printStackTrace();
            lock.unlock();
        }
    }
}