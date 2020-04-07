package top.mgy.multithreading;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Aricondition{
    private int  number = 0;

    //生产者
    public synchronized void increment() throws Exception{

        //step1 :判断 这里判断必须用while ，否则可能会引起虚假唤醒
        while (number != 0){
            //等待
            this.wait();
        }

        //step2 :干活
        number++;
        System.out.println(Thread.currentThread().getName()+"\t"+number);

        //step3 :通知
        this.notifyAll();
    }

    //消费者
    public synchronized void decrement() throws Exception{
        //step1 :判断
        while (number == 0){
            this.wait();
        }

        //step2 :干活
        number--;
        System.out.println(Thread.currentThread().getName()+"\t"+number);

        //step3 :通知
       this.notifyAll();
    }
}


/**
 * juc方式
 */
class Aricondition2{
    private int  number = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //生产者
    public void increment() throws Exception{

        lock.lock();  //上锁
        try {
            //step1 :判断 这里判断必须用while ，否则可能会引起虚假唤醒
            while (number != 0){
                //等待
                condition.await(); //this.wait();
            }

            //step2 :干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            //step3 :通知
            condition.signalAll(); //this.notifyAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock(); //释放锁
        }
    }

    //消费者
    public void decrement() throws Exception{
        lock.lock();
        try {
            //step1 :判断
            while (number == 0){
                condition.await(); //this.wait();
            }

            //step2 :干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);

            //step3 :通知
            condition.signalAll(); //this.notifyAll();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }
}

/**
 * 生产者/消费者
 *
 * 1 线程操作资源类
 * 2 判断/干活/通知
 * 3 防止虚假唤醒
 */
public class ProdConsumerTest {


    public static void main(String[] args) {

        Aricondition2 aricondition = new Aricondition2();

        new Thread(()->{

            try {
                for (int i = 0; i < 10; i++) {
                    aricondition.increment();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        },"A").start();


        new Thread(()->{

            try {
                for (int i = 0; i < 10; i++) {
                    aricondition.decrement();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        },"B").start();


        new Thread(()->{

            try {
                for (int i = 0; i < 10; i++) {
                    aricondition.increment();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        },"C").start();


        new Thread(()->{

            try {
                for (int i = 0; i < 10; i++) {
                    aricondition.decrement();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        },"D").start();



    }


}
