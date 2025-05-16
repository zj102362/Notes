package com.six;

import sun.misc.Unsafe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/5/16 13:21
 */
interface DecimalAccount {
    // 获取余额
    BigDecimal getBalance();
    // 取款
    void withdraw(BigDecimal amount);
    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }
}

class DecimalAccountSafe implements DecimalAccount {
    private AtomicReference<BigDecimal> balance;

    public DecimalAccountSafe(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true){
            BigDecimal prev = this.getBalance();
            BigDecimal next = prev.subtract(amount);
            if(balance.compareAndSet(prev, next)){
                break;
            }
        }
    }
}



public class Test03 {
    public static void main(String[] args) {
        DecimalAccount account = new DecimalAccountSafe(new BigDecimal(10000));
        DecimalAccount.demo(account);

    }
}