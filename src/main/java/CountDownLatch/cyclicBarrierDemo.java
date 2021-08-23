package CountDownLatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 加计数方式,计数达到指定值时释放所有等待线程,计数达到指定值时，计数置为0重新开始
 * 调用await()方法计数加1，若加1后的值不等于构造方法的值，则线程阻塞,可重复利用
 * 底层基于Condition实现
 */
public class cyclicBarrierDemo {
    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
            System.out.println("召唤神龙！");
        });
        for(int i = 0 ; i <= 5; i++){
            final int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集第"+temp+"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
