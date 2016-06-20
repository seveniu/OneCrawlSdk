package com.seveniu.thrift;

import com.seveniu.common.json.Json;
import com.seveniu.consumer.Consumer;
import com.seveniu.node.Node;
import com.seveniu.task.CrawlTaskStatistic;
import org.apache.thrift.TException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by seveniu on 5/26/16.
 * ConsumerImpl
 */
public class ConsumerProxy implements ConsumerThrift.Iface {

    private Consumer consumer;
    private ExecutorService processService;

    public ConsumerProxy(Consumer consumer) {
        this.consumer = consumer;
        this.processService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                r -> {
                    return new Thread(r, "consumer-process-thread");
                }
        );
    }

    @Override
    public boolean has(String url) throws TException {
        return consumer.has(url);
    }

    @Override
    public void done(String node) throws TException {
        processService.execute(() -> {
            try {
                consumer.done(Json.toObject(node, Node.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void statistic(String statistic) throws TException {
        processService.execute(() -> {
            try {
                consumer.statistic(Json.toObject(statistic, CrawlTaskStatistic.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<Task> getTasks() throws TException {
        return consumer.getTasks();
    }
}
