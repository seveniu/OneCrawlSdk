package com.seveniu.thrift;

import com.seveniu.common.json.Json;
import com.seveniu.consumer.Consumer;
import com.seveniu.node.Node;
import com.seveniu.task.CrawlTaskStatistic;
import org.apache.thrift.TException;

/**
 * Created by seveniu on 5/26/16.
 * ConsumerImpl
 */
public class ConsumerThriftImpl implements ConsumerThrift.Iface {

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
        try {
            consumer.done(Json.toObject(node, Node.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statistic(String statistic) throws TException {
        try {
            consumer.statistic(Json.toObject(statistic, CrawlTaskStatistic.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void taskStatusChange(String taskId, TaskStatus status) throws TException {
        consumer.taskStatusChange(taskId, status);
    }


}
