package com.example;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 功能：
 * 作者：张杰
 * 日期：2025/4/25 13:45
 */
@Slf4j
public class Test2 {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);

        new Thread(() -> {
            while (true){
                Message take = queue.take();
                log.info("{}",take);
            }
        },"消费者").start();


        for (int i = 0; i < 2; i++) {
            int id = i;
            new Thread(() -> {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Message message = new Message(id,"值"+id);
                    queue.put(message);
                    log.info("{}",message);
                }
            },"生产者"+i).start();
        }
    }
}

@Slf4j
class MessageQueue{
    private static LinkedList<Message> lists = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take(){
        synchronized (lists){
            while (lists.isEmpty()){
                log.debug("队列空了-------------------");
                try {
                    lists.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = lists.removeFirst();
            lists.notifyAll();
            return message;
        }
    }

    public void put(Message message){
        synchronized (lists){
            while (lists.size() == capacity){
                log.debug("队列满了-------------------");
                try {
                    lists.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            lists.addLast(message);
            lists.notifyAll();
        }
    }
}

final class Message{
    @Getter
    private int id;
    @Getter
    private Object value;
    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}