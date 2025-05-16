package com.five;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/27 11:08
 */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        TPTInterrupt tptInterrupt = new TPTInterrupt();
        tptInterrupt.start();
        tptInterrupt.start();
        Thread.sleep(5000);
        tptInterrupt.stop();
    }
}


@Slf4j
class TPTInterrupt {
    private Thread thread;
    volatile private boolean stop;
    private boolean start;
    public void start(){
        synchronized (this){
            if (start == true){
                return;
            }
            start = true;
        }
        thread = new Thread(() -> {
            while(true) {
                if(stop){
                    break;
                }else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    log.info("将结果保存");
                }
            }
        },"监控线程");
        thread.start();
    }
    public void stop() {
        stop = true;
        thread.interrupt();
    }
}