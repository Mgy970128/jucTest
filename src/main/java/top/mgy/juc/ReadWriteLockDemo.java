package top.mgy.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁   写的时候保证只能有一个线程进入，读的时候可并发读取
 */

class Mycache{
    private volatile Map<String,Object> map= new HashMap<>();

    //读写锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();



    public void put(String key,Object value){

        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t开始写入"+key);
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            }catch (Exception e){
                e.printStackTrace();
            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t写入完成"+key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }



    }

    public void get(String key){


        readWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()+"\t开始读取");
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            }catch (Exception e){
                e.printStackTrace();
            }
            Object res = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成："+res);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }


    }
}

public class ReadWriteLockDemo {


    public static void main(String[] args) {

        Mycache mycache = new Mycache();

        for (int i = 0; i < 5; i++) {
            final int tempkey = i;
            new Thread(()->{
                mycache.put(tempkey+"",tempkey+"");
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempkey = i;
            new Thread(()->{
                mycache.get(tempkey+"");
            },String.valueOf(i)).start();
        }

    }
}
