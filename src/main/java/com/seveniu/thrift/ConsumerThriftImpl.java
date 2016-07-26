package com.seveniu.thrift;

import com.alibaba.fastjson.JSON;
import com.seveniu.consumer.Consumer;
import com.seveniu.def.TaskStatus;
import com.seveniu.node.Node;
import com.seveniu.task.CrawlTaskStatistic;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by seveniu on 5/26/16.
 * ConsumerImpl
 */
public class ConsumerThriftImpl implements ConsumerThrift.Iface {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10, new ThreadFactory() {
        AtomicInteger count = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "crawl-sdk-data-transfer-" + count.getAndIncrement());
        }
    });

    private Consumer consumer;

    public ConsumerThriftImpl(Consumer consumer) {
        this.consumer = consumer;

    }

    @Override
    public boolean has(String url) {
        return consumer.has(url);
    }

    @Override
    public void done(String node) {
        if (executor.getQueue().size() > 1000) {
            logger.warn("data transfer queue size than 100");
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer.done(JSON.parseObject(node, Node.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void statistic(String statistic) throws TException {
        try {
            consumer.statistic(JSON.parseObject(statistic, CrawlTaskStatistic.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void taskStatusChange(String taskId, TaskStatus status) throws TException {
        consumer.taskStatusChange(taskId, status);
    }


}
