package top.mgy.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 精准唤醒线程
 */
class ShareData{
    private int number = 1;  //A 1   B 2  C 3

    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();

        try {

            //判断
            while (number != 1){
                //wait ...
                c1.await();
            }

            //干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }

            //通知c2，唤醒
            number = 2;
            c2.signal();


        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void print10(){
        lock.lock();

        try {

            //判断
            while (number != 2){
                //wait ...
                c2.await();
            }

            //干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }

            //通知c3，唤醒
            number = 3;
            c3.signal();


        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void print15(){
        lock.lock();

        try {

            //判断
            while (number != 3){
                //wait ...
                c3.await();
            }

            //干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }

            //通知c1，唤醒
            number = 1;
            c1.signal();


        }catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    //三个方法合并
    public void print(int num){
        lock.lock();

        try {
            if(num == 5){
                while (number != 1){
                    c1.await();
                }

                for (int i = 0; i < num; i++) {
                    System.out.println(Thread.currentThread().getName()+"\t"+i);
                }

                //唤醒c2
                number = 2;
                c2.signal();
            }else if(num == 10){
                while (number != 2){
                    c2.await();
                }

                for (int i = 0; i < num; i++) {
                    System.out.println(Thread.currentThread().getName()+"\t"+i);
                }

                //唤醒c3
                number = 3;
                c3.signal();

            }else if(num == 15){
                while (number != 3){
                    c3.await();
                }

                for (int i = 0; i < num; i++) {
                    System.out.println(Thread.currentThread().getName()+"\t"+i);
                }

                //唤醒c1
                number = 1;
                c1.signal();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class ConditionDome {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        new Thread(()->{
            shareData.print(5);
        },"A").start();

        new Thread(()->{
            shareData.print(10);
        },"B").start();

        new Thread(()->{
            shareData.print(15);
        },"C").start();
    }
}
