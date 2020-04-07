package top.mgy.callback;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步回调
 */
public class CallbackDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        //模拟网络请求
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                int a = 10 / 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "GET 200 ok";
        }).whenComplete((t, u) -> {
            //异步返回处理函数  t表示返回成功的返回值  u表示失败的错误信息
            //如果 t不为空说明成功返回  如果u不为空说明 回调失败
            if (t != null) {
                System.out.println("异步回调成功：返回值:" + t);
            }
            if (u != null) {
                System.out.println("异步回调失败：失败原因:" + u);
            }
        }).exceptionally(err -> {
            //失败会进入该函数
            System.out.println("excption:" + err.getMessage());
            return "404";
        }).get();

        System.out.println("最终返回结果: "+result);
    }
}
