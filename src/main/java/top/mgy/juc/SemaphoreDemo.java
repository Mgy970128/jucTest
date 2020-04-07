package top.mgy.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 在信号量上定义两种操作：
 *  acquire (获取):当一个线程去调用acquire 操作时，它要么通过成功获取信号量(信号量减1),
 *                  要么一直等待下去，直到信号量释放，或者超时。
 *  release (释放):实际信号量的值会加1，然后唤醒等待的线程
 *
 *  信号量主要用于两个目的：一是用于临界资源的互斥访问，另一个是用于并发线程数的控制
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);  //模拟三个车位

        //6个车去抢
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {

                    semaphore.acquire();  //获取临界资源(车位)
                    System.out.println(Thread.currentThread().getName()+"\t抢到了车位");

                    try {
                        TimeUnit.SECONDS.sleep(3); //3秒后释放车位
                        System.out.println(Thread.currentThread().getName()+"\t离开了车位");
                    }finally {
                        semaphore.release();  //释放车位
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }


    }
}
