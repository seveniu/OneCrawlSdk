package com.seveniu;

import com.seveniu.consumer.Consumer;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by seveniu on 5/26/16.
 * ThriftServer
 */
public class ConsumerServer {

    private static Logger logger = LoggerFactory.getLogger(ConsumerServer.class);
    private volatile boolean running = false;
    private Consumer consumer;
    private int port;

    public ConsumerServer(Consumer consumer, int port) {
        this.consumer = consumer;
        this.port = port;
    }

    public void start() throws ConnectException, TException {
        startConsumerServer(consumer, port);

        try {
            logger.info("connect crawl server after 3 second!");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
