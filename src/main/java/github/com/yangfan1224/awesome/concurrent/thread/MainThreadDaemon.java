package github.com.yangfan1224.awesome.concurrent.thread;

/**
 * Main线程是个非守护线程，不能设置成守护线程
 * 对于运行中的线程，调用Thread.setDaemon()会抛出异常
 * @author yangfan
 */
public class MainThreadDaemon {
    public static void main(String [] args) {
        System.out.println(" main thread begin ");
        Thread.currentThread().setDaemon(true);
    }
}
