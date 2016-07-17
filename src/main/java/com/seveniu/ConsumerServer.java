package com.seveniu;

import com.seveniu.consumer.Consumer;
import com.seveniu.crawlClient.ConsumerConfig;
import com.seveniu.crawlClient.CrawlClient;
import com.seveniu.thrift.ConsumerThrift;
import com.seveniu.thrift.ConsumerThriftImpl;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;

/**
 * Created by seveniu on 5/26/16.
 * ThriftServer
 */
public class ConsumerServer {

    private static Logger logger = LoggerFactory.getLogger(ConsumerServer.class);
    private static volatile boolean running = false;

    public static String start(String crawlHost, int crawlPort, Consumer consumer, ConsumerConfig config) throws TTransportException, ConnectException {
        if ("thrift".equals(config.getType())) {
            startConsumerServer(consumer, config.getPort());
        }

        try {
            logger.info("connect crawl server after 3 second!");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CrawlClient.get().build(crawlHost, crawlPort);
        return CrawlClient.get().reg(config);
    }

    private static void startConsumerServer(Consumer consumer, int port) {
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

//    public static String sendPost(String url, String data) throws IOException {
//
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type",
//                "application/json");
////        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        // For POST only - START
//        con.setDoOutput(true);
//        OutputStream os = con.getOutputStream();
//        os.write(data.getBytes("UTF-8"));
//        os.flush();
//        os.close();
//        // For POST only - END
//
//        int responseCode = con.getResponseCode();
//        System.out.println("POST Response Code :: " + responseCode);
//
////        if (responseCode == HttpURLConnection.HTTP_OK) { //success
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        // print result
//        return response.toString();
////        } else {
////            return "error, code : " + responseCode;
////        }
//    }
}
