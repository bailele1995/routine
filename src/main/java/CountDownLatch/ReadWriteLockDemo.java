package CountDownLatch;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 原理：
 *
 * 在AQS中，通过int类型的全局变量state来表示同步状态，即用state来表示锁。
 * ReentrantReadWriteLock也是通过AQS来实现锁的，但是ReentrantReadWriteLock有两把锁：读锁和写锁，
 * 它们保护的都是同一个资源，那么如何用一个共享变量来区分锁是写锁还是读锁呢？答案就是按位拆分。
 *
 * 由于state是int类型的变量，在内存中占用4个字节，也就是32位。
 * 将其拆分为两部分：高16位和低16位，其中高16位用来表示读锁状态，低16位用来表示写锁状态。
 * 当设置读锁成功时，就将高16位加1，释放读锁时，将高16位减1；当设置写锁成功时，就将低16位加1，释放写锁时，将第16位减1。
 *
 * https://zhuanlan.zhihu.com/p/91408261
 */
public class ReadWriteLockDemo {
        public static void main(String[] args) {
            final Queue q = new Queue();
            for(int i=0;i<3;i++)
            {
                new Thread(){
                    public void run(){
                        while(true){
                            q.get();
                        }
                    }
                }.start();

                new Thread(){
                    public void run(){
                        while(true){
                            q.put(new Random().nextInt(10000));
                        }
                    }
                }.start();
            }
        }
    }

class Queue{
    private Object data = null;//共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
    ReadWriteLock rwl = new ReentrantReadWriteLock();
    public void get(){
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " be ready to read data!");
            Thread.sleep((long)(Math.random()*1000));
            System.out.println(Thread.currentThread().getName() + "have read data :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            rwl.readLock().unlock();
        }
    }

    public void put(Object data){
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " be ready to write data!");
            Thread.sleep((long)(Math.random()*1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName() + " have write data: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            rwl.writeLock().unlock();
        }
    }
}

