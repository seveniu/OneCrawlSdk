package com.seveniu.crawlClient;

import com.seveniu.common.json.Json;
import com.seveniu.common.str.StrUtil;
import com.seveniu.def.TaskStatus;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;
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

    public void reg(ConsumerConfig consumerConfig) {
        try {
            if (consumerConfig != null) {
                synchronized (lock) {
                    this.uuid = thriftClient.reg(consumerConfig);
                }
            } else {
                throw new NullPointerException("consumer config is null");
            }
        } catch (TException e) {
            logger.error("reg {} error : {}", Json.toJson(consumerConfig), e.getMessage());
            e.printStackTrace();
        }
    }

    public String getRunningTasks() {
        try {
            if (uuid == null) {
                throw new NullPointerException("uuid is null");
            }
            return thriftClient.getRunningTasks(uuid);
        } catch (TException e) {
            logger.error("get running task error : {}", e.getMessage());
            return uuid;
        }
    }

    public TaskStatus addTask(TaskInfo taskInfo) {
        try {
            if (uuid == null) {
                throw new NullPointerException("uuid is null");
            }
            if (taskInfo == null) {
                throw new NullPointerException("task is null");
            }
            synchronized (lock) {
                return thriftClient.addTask(uuid, taskInfo);
            }
        } catch (TException e) {
            logger.error("add task {} error : {}", Json.toJson(taskInfo), e.getMessage());
            return TaskStatus.FAIL;
        }

    }

    public String getTaskSummary() {
        try {
            return thriftClient.getTaskSummary(uuid);
        } catch (TException e) {
            logger.error("get task summary error : {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void validateConfig(ConsumerConfig config) throws IllegalArgumentException {

        String type = config.getType();
        if (StrUtil.isEmpty(config.name)) {
            throw new IllegalArgumentException("name is empty");
        }
        if (StrUtil.isEmpty(type)) {
            throw new IllegalArgumentException("type is empty");
        }
        if (!type.equals("http") && !type.equals("thrift")) {
            throw new IllegalArgumentException("type is error : " + type);
        }

        if (type.equals("http")) {
            UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
            if (StrUtil.isEmpty(config.statisticUrl)) {
                throw new IllegalArgumentException("statisticUrl is empty");
            }
            if (StrUtil.isEmpty(config.doneUrl)) {
                throw new IllegalArgumentException("doneUrl is empty");
            }
            if (StrUtil.isEmpty(config.duplicateUrl)) {
                throw new IllegalArgumentException("duplicateUrl is empty");
            }
            if (!urlValidator.isValid(config.statisticUrl)) {
                throw new IllegalArgumentException("statisticUrl is error : " + config.statisticUrl);
            }
            if (!urlValidator.isValid(config.doneUrl)) {
                throw new IllegalArgumentException("doneUrl is error : " + config.doneUrl);
            }
            if (!urlValidator.isValid(config.duplicateUrl)) {
                throw new IllegalArgumentException("duplicateUrl is error : " + config.duplicateUrl);
            }
        }

        if (type.equals("thrift")) {
            if (StrUtil.isEmpty(config.host)) {
                throw new IllegalArgumentException("host is empty");
            }
            if (config.port == 0) {
                throw new IllegalArgumentException("port is not set");
            }
            if (!InetAddressValidator.getInstance().isValid(config.host)) {
                throw new IllegalArgumentException("host is error : " + config.host);
            }

        }
    }

}
