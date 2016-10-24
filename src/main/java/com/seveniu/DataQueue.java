package com.seveniu;

import com.alibaba.fastjson.JSON;
import com.seveniu.consumer.Consumer;
import com.seveniu.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by seveniu on 10/11/16.
 * *
 */
public class DataQueue {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String PREFIX = "data-";
    private String host;
    private int port;
    private String key;
    private Consumer consumer;
    private int threadNum = 20;
    private Connection connection;

//    public DataQueue(String host, int port, String key, Consumer consumer) {
//        this.host = host;
//        this.port = port;
//        this.key = key;
//        this.consumer = consumer;
//    }


    public DataQueue(String key) {
        this.key = key;
    }

    private ThreadPoolExecutor threadPoolExecutor;

    public void start() {
        init();
        Jedis jedis = new Jedis(host, port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        PreparedStatement pstmt = connection.prepareStatement("select `data` from queue where `name` = ? limit ?");
                        pstmt.setString(1, key);
                        pstmt.setInt(2, threadNum);
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next()) {
                            String data = rs.getString(1);
                            threadPoolExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    consumer.done(JSON.parseObject(data, Node.class));
                                    connection.
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(data);
                                }
                            });
                        }
                        if(rs.getFetchSize() == 0) {
                            TimeUnit.SECONDS.sleep(5);
                        }

//                        String data = jedis.blpop(0, PREFIX + key).get(1);
//                        threadPoolExecutor.execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                consumer.done(JSON.parseObject(data, Node.class));
//                            }
//                        });
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

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://10.211.55.3:3306/data-queue", "xxx", "password");
//here sonoo is database name, root is username and password
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
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
