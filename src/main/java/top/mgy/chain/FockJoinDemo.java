package top.mgy.chain;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 分支合并框架
 */
public class FockJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(0,100);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ForkJoinTask<Integer> result = forkJoinPool.submit(myTask);

        System.out.println(result.get());

        forkJoinPool.shutdown();
    }
}


/**
 * 多线程计算任务
 */
class MyTask extends RecursiveTask<Integer>{


    //分支的最小粒度
    private final static Integer ADJUST_VALUE = 10;

    private int begin;
    private int end;
    private int result;


    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        //任务执行
        if(end - begin <= ADJUST_VALUE){
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        }else {
            //拆分任务
            int middle = (end + begin) / 2;

            MyTask myTask1 = new MyTask(begin,middle);
            MyTask myTask2 = new MyTask(middle+1,end);
            myTask1.fork();
            myTask2.fork();

            //结果合并
            result = myTask1.join() + myTask2.join();

        }
        return result;
    }
}
