package demo2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock锁的等待与唤醒（所有线程）
 */
public class B {
    public static void main(String[] args) {

        Data2 data = new Data2();
        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.increment();
            }
        },"A").start();

        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.decrement();
            }
        },"B").start();

        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.increment();
            }
        },"C").start();

        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.decrement();
            }
        },"D").start();
    }
}

class Data2{

    private int number = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();


    //+1
    public void increment(){
        lock.lock();
        try{
            while(number!=0){
                //等待
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"=>number"+number);
            //通知其它线程
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //-1
    public void decrement() {
        lock.lock();
        try{
            while(number==0){
                //等待
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"=>number"+number);
            //通知其它线程
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
