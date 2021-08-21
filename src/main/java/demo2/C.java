package demo2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock锁的等待与唤醒（指定线程）
 */
public class C {
    public static void main(String[] args) {

        Data3 data = new Data3();
        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.printA();
            }
        },"A").start();

        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.printB();
            }
        },"B").start();

        new Thread(()->{
            for(int i = 0;i < 30;i++){
                data.printC();
            }
        },"C").start();
    }
}

class Data3{

    private int number = 1;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void printA(){
        lock.lock();
        try{
            if(number!=1){
                //等待
                condition.await();
            }
            System.out.println(Thread.currentThread().getName()+"打印A");
            number++;
            //通知其它线程
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try{
            if(number!=2){
                //等待
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+"打印B");
            number++;
            //通知其它线程
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try{
            if(number!=3){
                //等待
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+"打印C");
            number=1;
            //通知其它线程
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
