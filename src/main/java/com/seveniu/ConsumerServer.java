package com.seveniu;

import com.seveniu.consumer.Consumer;
import com.seveniu.crawlClient.ConsumerConfig;
import com.seveniu.crawlClient.CrawlClient;
import com.seveniu.thrift.ConsumerThrift;
import com.seveniu.thrift.ConsumerThriftImpl;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by seveniu on 5/26/16.
 * ThriftServer
 */
public class ConsumerServer {

    private static Logger logger = LoggerFactory.getLogger(ConsumerServer.class);
    private volatile boolean running = false;
    private String crawlHost;
    private int crawlPort;
    private String dataQueueHost;
    private int dataQueuePort;
    private Consumer consumer;
    private ConsumerConfig consumerConfig;
    private DataQueue dataQueue;

    public ConsumerServer(boolean running, String crawlHost, int crawlPort, String dataQueueHost, int dataQueuePort, Consumer consumer, com.seveniu.crawlClient.ConsumerConfig consumerConfig) {
        this.running = running;
        this.crawlHost = crawlHost;
        this.crawlPort = crawlPort;
        this.dataQueueHost = dataQueueHost;
        this.dataQueuePort = dataQueuePort;
        this.consumer = consumer;
        this.consumerConfig = consumerConfig;
        this.dataQueue = new DataQueue(dataQueueHost, dataQueuePort, consumerConfig.getName(), consumer);
    }

    public void start() throws ConnectException, TException {
        if ("thrift".equals(consumerConfig.getType())) {
            startConsumerServer(consumer, consumerConfig.getPort());
        }

        try {
            this.dataQueue.start();
            logger.info("connect crawl server after 3 second!");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CrawlClient.get().build(crawlHost, crawlPort);
        CrawlClient.get().reg(consumerConfig);
    }

    public void setDataConsumerThreadNum(int threadNum) {
        this.dataQueue.setThreadNum(threadNum);
    }

    public void setDataConsumerThreadPool(ThreadPoolExecutor executor) {
        this.dataQueue.setThreadPoolExecutor(executor);
    }


    private void startConsumerServer(Consumer consumer, int port) {
        if (running) {
            logger.debug(" server is running ");
            return;
        }
        new Thread(() -> {

            try {
                TServerSocket socket = new TServerSocket(port);
                ConsumerThrift.Processor processor = new ConsumerThrift.Processor<>(new ConsumerThriftImpl(consumer));
                TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(socket).processor(processor));
                running = true;
                server.serve();
            } catch (TTransportException e) {
                e.printStackTrace();
                running = false;
            }
        }, "consumer-server-thread").start();

    }

}
