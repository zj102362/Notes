package com.five;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/27 14:21
 */
public class Test3 {
    public static void main(String[] args) {

        Account account = new AccountCas(10000);
        Account.demo(account);

    }
}

class AccountUnsafe implements  Account{
    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public synchronized Integer getBalance() {
        return balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }
}


interface Account{
    Integer getBalance();

    void withdraw(Integer amount);

    static void demo(Account account){
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                account.withdraw(10);
            });
            ts.add(thread);
        }

        ts.forEach(Thread::start);
        for (Thread thread : ts) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(account.getBalance());
    }

}

class AccountCas implements Account{
    private AtomicInteger balance;

    public AccountCas(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true){
            System.out.println(".");
            int prec = balance.get();
            int next = balance.get()-amount;
            if(balance.compareAndSet(prec,next)) break;
        }
    }
}