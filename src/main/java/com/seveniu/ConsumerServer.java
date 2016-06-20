package com.seveniu;

import com.seveniu.consumer.Consumer;
import com.seveniu.consumer.RemoteConsumerConfig;
import com.seveniu.thrift.ConsumerProxy;
import com.seveniu.thrift.ConsumerThrift;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by seveniu on 5/26/16.
 * ThriftServer
 */
public class ConsumerServer {

    private static Logger logger = LoggerFactory.getLogger(ConsumerServer.class);

    public static void start(String crawlRegUrl, Consumer consumer, RemoteConsumerConfig config) {
        config.validate();
        if ("thrift".equals(config.getType())) {
            thrift(consumer, config.getPort());
            reg(crawlRegUrl, config);
        }
    }

    private static void thrift(Consumer consumer, int port) {
        new Thread(() -> {

            try {
                TServerSocket socket = new TServerSocket(port);
                ConsumerThrift.Processor processor = new ConsumerThrift.Processor<>(new ConsumerProxy(consumer));
                TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(socket).processor(processor));
                server.serve();
            } catch (TTransportException e) {
                e.printStackTrace();
            }
        }, "consumer-server-thread").start();

    }

    private static void reg(String crawlRegUrl, RemoteConsumerConfig config) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                try {
                    String result = sendPost(crawlRegUrl, config.toJson());
                    if ("true".equals(result)) {
                        logger.info("reg consumer success");
                    } else {
                        logger.error("reg consumer failed, error : {}", result);
                    }
                } catch (IOException e) {
                    logger.error("reg consumer failed, e : {}", e.getMessage());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer-reg-thread").start();
    }

    public static String sendPost(String url, String data) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type",
                "application/json");
//        con.setRequestProperty("User-Agent", USER_AGENT);

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(data.getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

//        if (responseCode == HttpURLConnection.HTTP_OK) { //success
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        return response.toString();
//        } else {
//            return "error, code : " + responseCode;
//        }
    }
}
