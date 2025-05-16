package com.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 15:47
 */
public class Test4 {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();

        new Thread(() -> {
            bigRoom.sleep();
        }).start();

        new Thread(() -> {
            bigRoom.study();
        }).start();
    }
}

@Slf4j
class BigRoom{
    private Object lockStudy = new Object();
    private Object lockSleep = new Object();

    public void study(){
        synchronized (lockStudy){
            try {
                log.info("study");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sleep(){
        synchronized (lockSleep){
            try {
                log.info("sleep");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}