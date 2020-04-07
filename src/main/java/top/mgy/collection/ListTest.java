package top.mgy.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 1、出现的现象
 * java.util.ConcurrentModificationException  (并发修改异常)
 * <p>
 * 2、导致原因
 * <p>
 * <p>
 * 3、解决方法
 * <p>   new Vector<>();
 * <p>   Collections.synchronizedList()
 *       new CopyOnWriteArrayList<>()
 * 4、优化说明
 */
public class ListTest {

    public static void main(String[] args) {
        //listTest();
        //setTest();
        mapTest();


    }

    /**
     * map 线程安全测试
     */
    private static void mapTest() {
        //Map<String,String> map = new HashMap<>();
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * set 测试
     */
    private static void setTest() {
        //Set<String> set = new HashSet<>();  //set底层使用HashMap的key
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }


    /**
     * 集合线程不安全
     */
    private static void listTest() {
        //final List<String> list = new ArrayList<String>();

        //TODO 解决方案一   使用 Vector   public synchronized boolean add(E e)
        //List<String> list = new Vector<>();
        //TODO 解决方案二    Collections.synchronizedList
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        //TODO 解决方案三   写时复制
        List<String> list = new CopyOnWriteArrayList<>();

        /**
         * 30个线程同时修改 ArrayList 出现并发修改异常    java.util.ConcurrentModificationException
         */
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
