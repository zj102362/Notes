package com.example;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

@Slf4j
class GuardedObject {
    @Getter
    private int id;

    public GuardedObject(int id) {
        this.id = id;
    }

    private Object response;

    public Object get(long timeount){
        synchronized (this){
            long begin = System.currentTimeMillis();
            long passedTime = 0;
            while (null == response){
                long waitTime = timeount - passedTime;
                if(waitTime <= 0){
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }
    public void complete(Object response){
        synchronized (this){
            this.response = response;
            this.notifyAll();
        }
    }
}


class MailBoxes{
    private static Map<Integer,GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;

    private static synchronized int getnerateId(){
        return id++;
    }

    public static GuardedObject createGuardedObject(){
        GuardedObject go = new GuardedObject(getnerateId());
        boxes.put(go.getId(), go);
        return  go;
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }

    public static GuardedObject getGuardedObject(int id){
        return boxes.remove(id);
    }

}

@Slf4j
class People extends Thread{
    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        log.info("开始收信：{}",guardedObject.getId());
        Object o = guardedObject.get(5090);
        log.info("收到信{},内容{}",guardedObject.getId(),o);
    }
}

@Slf4j
class PostMan extends Thread{
    private int mailId;
    private String mail;

    public PostMan(int mailId, String mail) {
        this.mailId = mailId;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.getGuardedObject(mailId);
        log.info("送信{}，内容{}",mailId,mail);
        guardedObject.complete(mail);
    }
}

@Slf4j
public class Test{
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            People people = new People();
            people.start();
        }

        Thread.sleep(1000);
        Set<Integer> ids = MailBoxes.getIds();
        for (Integer id : ids) {
            PostMan postMan = new PostMan(id,"abc");
            postMan.start();
        }

    }
}