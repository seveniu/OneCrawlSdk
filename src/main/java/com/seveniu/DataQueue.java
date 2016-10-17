package com.seveniu;

import com.alibaba.fastjson.JSON;
import com.seveniu.consumer.Consumer;
import com.seveniu.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by seveniu on 10/11/16.
 * *
 */
public class DataQueue {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            0, 20, 10, TimeUnit.MINUTES, new SynchronousQueue<>(),
            new ThreadFactory() {
                AtomicInteger count = new AtomicInteger();

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "data-process-thread-" + count.getAndIncrement());
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    if (!executor.isShutdown()) {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            // should not be interrupted
                        }
                    }
                }
            });

    public void start(String host, int port, final String key, Consumer consumer) {
        Jedis jedis = new Jedis(host, port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String data = jedis.blpop(0, key).get(1);
                        threadPoolExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                consumer.done(JSON.parseObject(data, Node.class));
                            }
                        });
                    } catch (Exception e) {
                        logger.error("consumer data error : {}", e.getMessage());
                    }
                }
            }
        }, "get-data-from-queue-thread").start();

    }

}
