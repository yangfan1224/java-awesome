package github.com.yangfan1224.awesome.concurrent.executor;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangfan
 */
public class CustomThreadFactory implements ThreadFactory {
    private final String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger(1);

    CustomThreadFactory(String groupFeature) {
        namePrefix = "CustomThreadFactory's " + groupFeature + "-Worker-";
    }

    @Override
    public Thread newThread(@NotNull Runnable task) {
        String name = namePrefix + nextId.getAndIncrement();
        Thread thread = new Thread(task, name);
        System.out.println(thread.getName());
        return thread;
    }
}
