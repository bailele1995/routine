package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 一个线程(或者多个)， 等待另外N个线程完成某个事情之后才能执行,底层实现了QAS
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
