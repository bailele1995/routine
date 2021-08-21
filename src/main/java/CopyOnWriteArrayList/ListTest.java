package CopyOnWriteArrayList;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * list并发情况下线程不安全，使用CopyOnWriteArrayList解决
 * ①、CopyOnWriteArrayList，写数组的拷贝，支持高效率并发且是线程安全的,读操作无锁的ArrayList。所有可变操作都是通过对底层数组进行一次新的复制来实现。
 * ②、CopyOnWriteArrayList适合使用在读操作远远大于写操作的场景里，比如缓存。它不存在扩容的概念，每次写操作都要复制一个副本，在副本的基础上修改后改变Array引用。CopyOnWriteArrayList中写操作需要大面积复制数组，所以性能肯定很差。
 * ③、CopyOnWriteArrayList 合适读多写少的场景，不过这类慎用 ，因为谁也没法保证CopyOnWriteArrayList 到底要放置多少数据，万一数据稍微有点多，每次add/set都要重新复制数组，这个代价实在太高昂了。在高性能的互联网应用中，这种操作分分钟引起故障。
 */
public class ListTest {
    public static void main(String[] args){
        List list = new CopyOnWriteArrayList();
        for(int i = 0; i < 10;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            }).start();
        }
    }
}
