package com.seveniu.crawlClient;

import com.seveniu.common.str.StrUtil;
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

/**
 * Created by seveniu on 7/3/16.
 * CrawlClient
 */
public class CrawlClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static CrawlClient crawlClient = new CrawlClient();

    private CrawlClient() {

    }

    public static CrawlClient get() {
        return crawlClient;
    }

    private CrawlThrift.Client thriftClient;

    public CrawlThrift.Client build(String host, int port) throws TTransportException {

        TTransport transport = new TSocket(host, port);
        transport.open();

        logger.info("template client connect /{}:{}", host, port);

        TProtocol protocol = new TBinaryProtocol(transport);
        this.thriftClient = new CrawlThrift.Client(protocol);
        return this.thriftClient;
    }

    public CrawlThrift.Client getThriftClient() {
        return thriftClient;
    }

    private static final Object lock = new Object();

    public String reg(ConsumerConfig consumerConfig) {
        try {
            if (consumerConfig == null) {
                return null;
            }
            synchronized (lock) {
                return thriftClient.reg(consumerConfig);
            }
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addTask(String uuid, TaskInfo taskInfo) {
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
            e.printStackTrace();
        }

        return false;
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
