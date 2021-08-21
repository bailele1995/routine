package demo2;

/**
 * synchronized的等待与唤醒
 */
public class A {
    public static void main(String[] args){

        Data data = new Data();
        new Thread(()->{
            for(int i = 0; i < 20; i ++){
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for(int i = 0; i < 20; i ++){
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for(int i = 0; i < 20; i ++){
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for(int i = 0; i < 20; i ++){
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}

class Data{

    private int number = 0;

    //+1
    public synchronized void increment() throws InterruptedException {

        //synchronized中线程唤醒时使用if判断会存在虚假唤醒，因此务必使用while做判断
        while(number!=0){
            //等待
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"=>number"+number);
        //通知其它线程
        this.notifyAll();
    }

    //-1
    public synchronized void decrement() throws InterruptedException {
        while(number==0){
            //等待
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"=>number"+number);
        //通知其它线程
        this.notifyAll();
    }
}
