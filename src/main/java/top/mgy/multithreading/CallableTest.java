package top.mgy.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {

        System.out.println("this is call function");

        return "我是返回值";
    }
}

public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //FutureTask futureTask = new FutureTask(new MyThread());
        FutureTask futureTask = new FutureTask(()->{
            System.out.println("this is lambda expression");
            return "我是返回值";
        });

        new Thread(futureTask,"A").start();

        System.out.println("返回值："+futureTask.get());
    }
}
