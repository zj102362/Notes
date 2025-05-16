package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/26 22:53
 */
public class Test17 {
    static Thread t1= null;
    static Thread t2 = null;
    static Thread t3= null;

    public static void main(String[] args) {
        SyncPark park = new SyncPark(5);

        t1 = new Thread(() -> {
            park.print("a",t2);
        });

        t2 = new Thread(() -> {
            park.print("b",t3);
        });

        t3 = new Thread(() -> {
            park.print("c",t1);
        });

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}

@Slf4j
class SyncPark{
    private int loop;

    public SyncPark(int loop) {
        this.loop = loop;
    }

    public void print(String str,Thread next){
        for(int i=0;i<loop;i++){
            LockSupport.park();
            log.info("{}",str);
            LockSupport.unpark(next);
        }
    }
}