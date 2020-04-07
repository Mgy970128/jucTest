package top.mgy.juc;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        //一个池中有5个线程
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //一个池中只有一个线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //一个池中N个线程，根据需求动态扩容
//        ExecutorService threadPool = Executors.newCachedThreadPool();
//
//        try {
//
//            for (int i = 0; i < 10; i++) {
//                threadPool.execute(()->{
//                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
//                });
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            threadPool.shutdown();
//        }


        /**
         *  alibaba规范线程池
         */
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        int coreNum = Runtime.getRuntime().availableProcessors(); //获取cpu核心数

        ExecutorService threadPool = new ThreadPoolExecutor(5,coreNum+1,0L
                ,TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(1024)
                ,nameThreadFactory
                ,new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
