package com.seveniu;

import com.alibaba.fastjson.JSON;
import com.seveniu.consumer.Consumer;
import com.seveniu.node.Node;
import com.seveniu.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by seveniu on 10/11/16.
 * *
 */
public class DataQueue {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String PREFIX = "data-";
    private String key;
    private Consumer consumer;
    private int threadNum = 20;

    public DataQueue(String key, Consumer consumer) {
        this.key = key;
        this.consumer = consumer;
    }

    private ThreadPoolExecutor threadPoolExecutor;

    public void start() {
        init();
        try {
            DBUtil.openConnection();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (threadPoolExecutor.getActiveCount() >= threadPoolExecutor.getMaximumPoolSize()) {
                            TimeUnit.SECONDS.sleep(2);
                            logger.info("executor is all active");
                            continue;
                        }
                        List<Map<String, Object>> mapList = DBUtil.queryMapList("select id,`data` from queue where `name` = ? limit ?", key, threadNum);
                        Map<String, Object> map = DBUtil.queryMap("select id,`data` from queue where `name` = ? limit 1", key);
                        if (map == null) {
                            TimeUnit.SECONDS.sleep(10);
                            logger.info("queue : {} is empty", key);
                            continue;
                        }
                        List<Integer> ids = new LinkedList<>();

                        int id = ((Long) map.get("id")).intValue();
                        ids.add(id);
                        String data = (String) map.get("data");
                        threadPoolExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                consumer.done(JSON.parseObject(data, Node.class));
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(data);
                            }
                        });
                        DBUtil.update("delete from queue where id =?", id);
                    } catch (Exception e) {
                        logger.error("consumer data error : {}", e.getMessage());
                        e.printStackTrace();
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }, "get-data-from-queue-thread").start();
    }

    private void init() {
        if (threadPoolExecutor == null) {
            this.threadPoolExecutor = new ThreadPoolExecutor(
                    0, threadNum, 10, TimeUnit.MINUTES, new SynchronousQueue<>(),
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
                    }
            );

        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }
}
