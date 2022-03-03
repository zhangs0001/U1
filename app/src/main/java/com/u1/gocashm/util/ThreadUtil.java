package com.u1.gocashm.util;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;

/**
 * 线程工具类
 *
 * @author LiuFeng
 * @data 2021/3/2 14:37
 */
public class ThreadUtil {
    private static final String TAG = "ThreadUtil";

    private static int maximumThreadSize = 2 * (Runtime.getRuntime().availableProcessors() > 0 ? Runtime.getRuntime().availableProcessors() : 2) + 1;

    private static volatile ThreadUtil instance;
    private static volatile ExecutorService requester;
    private static volatile ScheduledExecutorService scheduler;

    private static Map<Runnable, ScheduledFuture> futures = new ConcurrentHashMap<>();

    private ThreadUtil() {
        super();
    }

    private static ThreadUtil getInstance() {
        if (instance == null) {
            synchronized (ThreadUtil.class) {
                if (instance == null) {
                    instance = new ThreadUtil();
                }
            }
        }
        return instance;
    }

    private ExecutorService getRequester() {
        if (requester == null) {
            synchronized (ThreadUtil.class) {
                if (requester == null) {
                    requester = new ThreadPoolExecutor(maximumThreadSize,
                            maximumThreadSize,
                            0,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(60),
                            new DefaultThreadFactory("Requester"),
                            new DefaultRejectedPolicy());
                }
            }
        }
        return requester;
    }

    private ScheduledExecutorService getScheduler() {
        if (scheduler == null) {
            synchronized (ThreadUtil.class) {
                if (scheduler == null) {
                    scheduler = new ScheduledThreadPoolExecutor((maximumThreadSize / 2) + 1,
                            new DefaultThreadFactory("Scheduler"));
                }
            }
        }
        return scheduler;
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public DefaultThreadFactory(String groupName) {
            namePrefix = "CubeEngine-" + groupName + "-";
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, namePrefix + threadNumber.getAndIncrement());
        }
    }

    /**
     * 默认拒绝策略：直接丢弃最新任务
     */
    private static class DefaultRejectedPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LogUtil.e(TAG, "线程池队列已满，直接丢弃最新任务。" + "\nTask " + r.toString() + " rejected from " + executor.toString());
        }
    }

    /**
     * 请求执行，适用于耗时的操作，线程会先进入队列
     *
     * @param runnable
     */
    public static void request(Runnable runnable) {
        getInstance().getRequester().execute(runnable);
    }

    /**
     * 定时任务，适用于定时的操作
     *
     * @param runnable
     */
    public static void schedule(Runnable runnable, long delay) {
        ScheduledFuture<?> future = getInstance().getScheduler().schedule(runnable, delay, TimeUnit.MILLISECONDS);
        futures.put(runnable, future);
    }

    /**
     * 定时任务，适用于定时的操作
     *
     * @param runnable
     */
    public static void schedule(Runnable runnable, long delay, long period) {
        ScheduledFuture<?> future = getInstance().getScheduler().scheduleWithFixedDelay(runnable, delay, period, TimeUnit.MILLISECONDS);
        futures.put(runnable, future);
    }

    /**
     * 异步转同步
     * 备注：异步任务在子线程执行！！
     *
     * @param task
     */
    public static void submit(Runnable task) {
        getInstance().getRequester().submit(task);
    }

    /**
     * 异步转同步
     * 备注：异步任务可选择调度在主线程、或子线程执行！！
     *
     * @param scheduler
     * @param task
     */
    public static void submit(Scheduler scheduler, Runnable task) {
        // 主线程执行
        if (scheduler == Scheduler.mainThread) {
            if (UIHandler.isMainThread()) {
                task.run();
                return;
            }

            // 闭锁实现异步转同步
            CountDownLatch latch = new CountDownLatch(1);
            Runnable innerTask = () -> {
                task.run();

                // 减小归零，执行完成
                latch.countDown();
            };

            UIHandler.run(innerTask);

            try {
                // 异步等待阻塞
                latch.await();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }

        // 线程池执行
        else {
            submit(task);
        }
    }

    /**
     * 异步任务转同步获取执行结果
     * 备注：异步任务在子线程执行！！
     *
     * @param task
     * @param <T>
     * @return
     */
    public static <T> T get(Callable<T> task) {
        Future<T> future = getInstance().getRequester().submit(task);
        try {
            return future.get();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * 异步转同步
     * 备注：异步任务可选择调度在主线程、或子线程执行！！
     *
     * @param scheduler
     * @param task
     */
    public static <T> T get(Scheduler scheduler, Callable<T> task) {
        // 主线程执行
        if (scheduler == Scheduler.mainThread) {
            if (UIHandler.isMainThread()) {
                try {
                    return task.call();
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    return null;
                }
            }

            // 闭锁实现异步转同步
            CountDownLatch latch = new CountDownLatch(1);
            CallRunnable<T> callRunnable = new CallRunnable<>(task);
            Runnable innerTask = () -> {
                callRunnable.run();

                // 减小归零，执行完成
                latch.countDown();
            };

            UIHandler.run(innerTask);

            try {
                // 异步等待阻塞
                latch.await();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }

            return callRunnable.get();
        }

        // 线程池执行
        else {
            return get(task);
        }
    }

    /**
     * Callable 转 Runnable，获取回调值
     *
     * @param <T>
     */
    private static class CallRunnable<T> implements Runnable {
        T t;
        Callable<T> task;

        CallRunnable(Callable<T> task) {
            this.task = task;
        }

        @Override
        public void run() {
            try {
                t = task.call();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }

        public T get() {
            return t;
        }
    }

    /**
     * 取消定时任务，适用于定时的操作
     *
     * @param runnable
     */
    public static void cancelSchedule(Runnable runnable) {
        if (futures.containsKey(runnable)) {
            ScheduledFuture<?> future = futures.remove(runnable);
            future.cancel(true);
        }
    }

    /**
     * 释放所有定时任务
     */
    public static void releaseSchedules() {
        Iterator<Map.Entry<Runnable, ScheduledFuture>> it = futures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Runnable, ScheduledFuture> r = it.next();
            ScheduledFuture<?> future = r.getValue();
            future.cancel(true);
        }
        futures.clear();
    }


    /**
     * 线程调度
     *
     * @author LiuFeng
     * @data 2020/9/25 18:51
     */
    public enum Scheduler {
        io, mainThread
    }
}
