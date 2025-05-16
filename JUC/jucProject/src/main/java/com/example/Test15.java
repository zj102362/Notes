package com.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/26 22:22
 */
@Slf4j
public class Test15 {
    public static void main(String[] args) {
        SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1,5);

        new Thread(() -> {
            syncWaitNotify.print("a",1,2);
        }).start();

        new Thread(() -> {
            syncWaitNotify.print("b",2,3);
        }).start();

        new Thread(() -> {
            syncWaitNotify.print("c",3,1);
        }).start();
    }
}


@Slf4j
class SyncWaitNotify{
    private int count;
    private int loop;

    public SyncWaitNotify(int count, int loop) {
        this.count = count;
        this.loop = loop;
    }

    public void print(String str,int flag,int nextFlag){
        for (int i = 0; i < loop; i++) {
            synchronized (this){
                while(flag!=count){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.info("{}",str);
                count = nextFlag;
                this.notifyAll();
            }
        }
    }
}