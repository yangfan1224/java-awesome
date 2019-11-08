package github.com.yangfan1224.awesome.concurrent.thread;

/**
 * Main线程结束，其他线程也可以立刻结束，当且仅当这些子线程都是守护线程
 *
 * java虚拟机(相当于进程)退出的时机是：虚拟机中所有存活的线程都是守护线程。
 * 只要还有存活的非守护线程虚拟机就不会退出，而是等待非守护线程执行完毕；
 * 反之，如果虚拟机中的线程都是守护线程，那么不管这些线程的死活java虚拟机都会退出。
 * @author yangfan
 */
public class MainWithThreadDaemon {
    public static void main(String [] args) {
        System.out.println("main thread begin ");

        ChildThread t1 = new ChildThread("thread1");
        ChildThread t2 = new ChildThread("thread2");
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();

        System.out.println("main thread over ");
    }
}
