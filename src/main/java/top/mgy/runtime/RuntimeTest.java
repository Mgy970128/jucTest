package top.mgy.runtime;

public class RuntimeTest {

    public static void main(String[] args) {
        /**
         * 默认情况下  堆内存占用物理内存的 1/64
         *            最大可占用物理内存的 1/4
         */
        long maxMemory = Runtime.getRuntime().maxMemory();  //返回JVM 堆内存可以使用的最大物理内存
        long totalMemory = Runtime.getRuntime().totalMemory(); //返回JVM 堆内存初始化内存总量

        System.out.println("-Xmx: MAX_MEMORY = "+maxMemory+"字节、"+(maxMemory / (double)1024 / 1024) + "MB");
        System.out.println("-Xms: TOTAL_MEMORY = "+totalMemory+"字节、"+(totalMemory / (double)1024 / 1024) + "MB");
    }
}
