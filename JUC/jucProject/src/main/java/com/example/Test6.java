package com.example;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 16:03
 */
public class Test6 {
    public static void main(String[] args) {
        ChopStick c1 = new ChopStick("1");
        ChopStick c2 = new ChopStick("2");
        ChopStick c3 = new ChopStick("3");
        ChopStick c4 = new ChopStick("4");
        ChopStick c5 = new ChopStick("5");

        Philosopher s1 = new Philosopher("s1", c1, c2);
        Philosopher s2 = new Philosopher("s2", c2, c3);
        Philosopher s3 = new Philosopher("s3", c3, c4);
        Philosopher s4 = new Philosopher("s4", c4, c5);
        Philosopher s5 = new Philosopher("s5", c5, c1);

        s1.start();
        s2.start();
        s3.start();
        s4.start();
        s5.start();
    }
}

@Slf4j
class ChopStick extends ReentrantLock {
    private String name;

    public ChopStick(String name) {
        this.name = name;
    }
}

class Philosopher extends Thread{
    private static final Logger log = LoggerFactory.getLogger(Philosopher.class);
    private ChopStick left;
    private ChopStick right;

    public Philosopher(String name, ChopStick left, ChopStick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true){
            if(left.tryLock()){
                try {
                    if(right.tryLock()){
                        try {
                            log.info("{}吃东西...", this.getName());
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } finally {
                            right.unlock();
                        }
                    }
                }finally {
                    left.unlock();
                }
            }
        }
    }
}