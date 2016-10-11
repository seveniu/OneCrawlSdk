package com.seveniu;

import com.alibaba.fastjson.JSON;
import com.seveniu.consumer.Consumer;
import com.seveniu.node.Node;
import redis.clients.jedis.Jedis;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by seveniu on 10/11/16.
 * *
 */
public class DataQueue {


    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            0, 20, 10, TimeUnit.MINUTES, new SynchronousQueue<>(),
            new ThreadFactory() {
                AtomicInteger count = new AtomicInteger();

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "data-process-thread-" + count.getAndIncrement());
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy());

    public void start(String host, int port, final String key, Consumer consumer) {
        Jedis jedis = new Jedis(host, port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String data = jedis.blpop(0, key).get(1);
                    threadPoolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            consumer.done(JSON.parseObject(data, Node.class));
                        }
                    });
                }
            }
        }, "get-data-from-queue-thread").start();

    }

}
