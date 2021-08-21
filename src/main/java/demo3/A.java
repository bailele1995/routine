package demo3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized锁加锁在类上、方法上
 */
public class A {
    public static void main(String[] args) {

        Data data = new Data();
        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.sendSms();
            }
        },"A").start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.callPhone();
            }
        },"B").start();


    }
}

class Data{

    public synchronized static void sendSms(){
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    public synchronized void callPhone() {
        System.out.println(Thread.currentThread().getName()+"打电话");
    }
}
