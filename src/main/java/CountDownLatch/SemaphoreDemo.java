package CountDownLatch;

import java.util.concurrent.Semaphore;

/**
 * Semaphore:信号量
 * 原理：
 * 当调用semaphore.acquire()方法时
 * 1、当前线程会尝试去同步队列获取一个令牌，获取令牌的过程也就是使用原子的操作去修改同步队列的state ,获取一个令牌则修改为state=state-1。
 *
 * 2、 当计算出来的state<0，则代表令牌数量不足，此时会创建一个Node节点加入阻塞队列，挂起当前线程。
 *
 * 3、当计算出来的state>=0，则代表获取令牌成功。
 *
 *
 * 当调用semaphore.release() 方法时
 *
 * 1、线程会尝试释放一个令牌，释放令牌的过程也就是把同步队列的state修改为state=state+1的过程
 *
 * 2、释放令牌成功之后，同时会唤醒同步队列中的一个线程。
 *
 * 3、被唤醒的节点会重新尝试去修改state=state-1 的操作，如果state>=0则获取令牌成功，否则重新进入阻塞队列，挂起线程。
 */
public class SemaphoreDemo {

    public static void main(String[] args){

        Semaphore semaphore = new Semaphore(3);

        for(int i = 0 ; i <= 6 ; i ++ ){
            new Thread(()->{
                try {
                    if(semaphore.availablePermits()==0){
                        System.out.println("车位不足，请耐心等待");
                    }
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"抢到车位了");
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }

    }
}
