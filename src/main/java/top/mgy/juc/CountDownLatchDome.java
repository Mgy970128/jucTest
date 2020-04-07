package top.mgy.juc;


import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch  java.util.concurrent包中用于保证线程执行顺序的工具类
 */
public class CountDownLatchDome {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(8);

        for (int i = 0; i < 8; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"放学回家");
                countDownLatch.countDown();  //线程执行完毕减 1
            }).start();
        }

        countDownLatch.await();   //主线程先进入等待状态，等待上面线程减完了，再执行下面的方法
        System.out.println(Thread.currentThread().getName()+ "班长锁门");
    }
}
