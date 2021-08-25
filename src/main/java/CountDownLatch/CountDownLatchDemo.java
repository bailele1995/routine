package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 一个线程(或者多个)， 等待另外N个线程完成某个事情之后才能执行,底层实现了QAS
 *
 *
 * 1.当我们调用CountDownLatch countDownLatch=new CountDownLatch(6) 时候，
 * 此时会创建一个AQS的同步队列，并把创建CountDownLatch 传进来的计数器赋值给AQS队列的 state，
 * 所以state的值也代表CountDownLatch所剩余的计数次数；
 *
 * 2.当我们调用countDownLatch.wait()的时候，会创建一个节点，加入到AQS阻塞队列，并同时把当前线程挂起。
 *
 * 3.当我们调用countDownLatch.down()方法的时候，会对计数器进行减1操作，AQS内部是通过释放锁的方式，
 * 对state进行减1操作，当state=0的时候证明计数器已经递减完毕，此时会将AQS阻塞队列里的节点线程全部唤醒。
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for(int i = 0;i <= 6;i ++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"Go out");
                countDownLatch.countDown(); //数量-1
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("close door");
    }
}
