package com.tutorial.threadpoolwrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class TaskExecutor {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final List<Future<?>> futures;

    public TaskExecutor(
            int coreThread, int maxThread, int aliveTime, TimeUnit timeUnit,
            BlockingQueue<Runnable> blockingQueue) {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                coreThread, maxThread, aliveTime, timeUnit, blockingQueue
        );
        this.futures = Collections.synchronizedList(new ArrayList<>());
    }

    public Future<?> submit(Runnable runnable) {
        while (threadPoolExecutor.getQueue().remainingCapacity() == 0) {
            //  Wait until queue is free
        }
        Future<?> future = threadPoolExecutor.submit(runnable);
        futures.add(future);
        return future;
    }

    /**
     * Wait until all task is complete
     */
    public void waitAllTask() {
        while (futures.stream().anyMatch(future -> !future.isDone() && !future.isCancelled())) {
            //  Wait until all task is complete or cancel
        }
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

}
