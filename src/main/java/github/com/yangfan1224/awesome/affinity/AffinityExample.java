package github.com.yangfan1224.awesome.affinity;

import net.openhft.affinity.AffinityLock;

public class AffinityExample {
    public static void main(String []args) {
        int threadCount = AffinityLock.cpuLayout()
                .sockets() *
                AffinityLock.cpuLayout()
                        .coresPerSocket();
        System.out.println(AffinityLock.cpuLayout().sockets());
        System.out.println(AffinityLock.cpuLayout().coresPerSocket());
        System.out.println(threadCount);
    }
}
