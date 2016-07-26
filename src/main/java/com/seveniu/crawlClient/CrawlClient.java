package com.seveniu.crawlClient;

import com.seveniu.def.TaskStatus;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;

/**
 * Created by seveniu on 7/3/16.
 * CrawlClient
 */
public class CrawlClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static CrawlClient crawlClient = new CrawlClient();
    private String uuid;

    private CrawlClient() {

    }

    public static CrawlClient get() {
        return crawlClient;
    }

    private CrawlThrift.Client thriftClient;

    public CrawlThrift.Client build(String host, int port) throws TTransportException, ConnectException {

        TTransport transport = new TSocket(host, port);
        transport.open();

        logger.info("crawl server connect /{}:{}", host, port);

        TProtocol protocol = new TBinaryProtocol(transport);
        this.thriftClient = new CrawlThrift.Client(protocol);
        return this.thriftClient;
    }

    public CrawlThrift.Client getThriftClient() {
        return thriftClient;
    }

    private static final Object lock = new Object();

    public void reg(ConsumerConfig consumerConfig) throws TException {
        if (consumerConfig != null) {
            synchronized (lock) {
                this.uuid = thriftClient.reg(consumerConfig);
            }
        } else {
            throw new NullPointerException("consumer config is null");
        }

    }

    public String getRunningTasks() throws TException {
        if (uuid == null) {
            throw new NullPointerException("uuid is null");
        }
        synchronized (lock) {
            return thriftClient.getRunningTasks(uuid);
        }

    }

    public ResourceInfo getResourceInfo() throws TException {
        if (uuid == null) {
            throw new NullPointerException("uuid is null");
        }
        synchronized (lock) {
            return thriftClient.getResourceInfo(uuid);
        }

    }

    public TaskStatus addTask(TaskInfo taskInfo) throws TException {
        if (uuid == null) {
            throw new NullPointerException("uuid is null");
        }
        if (taskInfo == null) {
            throw new NullPointerException("task is null");
        }
        synchronized (lock) {
            return thriftClient.addTask(uuid, taskInfo);
        }

    }

    public String getTaskSummary() throws TException {
        synchronized (lock) {
            return thriftClient.getTaskSummary(uuid);
        }
    }

}
