package com.example;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 15:53
 */
public class Test5 {
    public static void main(String[] args) {
        Object A  = new Object();
        Object B = new Object();
        new Thread(() -> {
            synchronized (A) {
                synchronized (B) {
                    System.out.println("1");
                }
            }
        }, "1").start();

        new Thread(() -> {
            synchronized (B) {
                synchronized (A) {
                    System.out.println("2");
                }
            }
        }, "2").start();
    }
} 