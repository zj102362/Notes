package com.six;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/5/16 13:05
 */
public class Test02 {
    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger(0);
        System.out.println(a.incrementAndGet());
        System.out.println(a.getAndIncrement());

        System.out.println(a.addAndGet(100));

        System.out.println(a.updateAndGet(i -> i*i));




    }
} 