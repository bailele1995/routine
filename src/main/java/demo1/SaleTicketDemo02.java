package demo1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock加锁
 */
public class SaleTicketDemo02 {
    public static void main(String[] args){
        Ticket02 ticket = new Ticket02();

        new Thread(() ->{
            for(int i = 0 ; i < 60 ; i ++ ){
                ticket.sale();
            }

        },"A").start();

        new Thread(()->{
            for(int i = 0 ; i < 60 ; i ++ ){
                ticket.sale();
            }
        },"B").start();

        new Thread(()->{
            for(int i = 0 ; i < 60 ; i ++ ){
                ticket.sale();
            }
        },"C").start();
    }
}


class Ticket02{

    private int number = 50;

    Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try{
            if(number>0) {
                System.out.println(Thread.currentThread().getName() + "卖出了" + number-- + "，剩余票数：" + number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}