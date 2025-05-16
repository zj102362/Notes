package com.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/26 22:39
 */
public class Test16 {
    public static void main(String[] args) {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition conditionA = awaitSignal.newCondition();
        Condition conditionB = awaitSignal.newCondition();
        Condition conditionC = awaitSignal.newCondition();

        new Thread(() ->  {
            awaitSignal.print("a",conditionA,conditionB);
        }).start();

        new Thread(() ->  {
            awaitSignal.print("b",conditionB,conditionC);
        }).start();

        new Thread(() ->  {
            awaitSignal.print("c",conditionC,conditionA);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        awaitSignal.lock();
        try {
            conditionA.signal();
        }finally {
            awaitSignal.unlock();
        }
    }
}

@Slf4j
class AwaitSignal extends ReentrantLock{
    private int loop;

    public AwaitSignal(int loop) {
        this.loop = loop;
    }

    public void print(String str, Condition cur,Condition next){
        for (int i = 0; i < 5; i++) {
            this.lock();
            try {
                cur.await();
                log.info(str);
                next.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                this.unlock();
            }
        }
    }

}