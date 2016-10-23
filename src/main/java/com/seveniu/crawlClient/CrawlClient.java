package com.seveniu.crawlClient;

import com.alibaba.fastjson.JSON;
import com.seveniu.DataQueue;
import com.seveniu.consumer.Consumer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by seveniu on 7/3/16.
 * CrawlClient
 */
public class CrawlClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private HttpClient httpClient;
    private String host = "http://127.0.0.1:20001";

    private CrawlClient() {
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(reg);
        connectionManager.setDefaultMaxPerRoute(20);
        httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();
    }

    private static CrawlClient crawlClient = new CrawlClient();
    private TaskQueue taskQueue;
    private DataQueue dataQueue;
    private String uuid;

    public static CrawlClient get() {
        return crawlClient;
    }


    public void reg(String host, String queueHost, int queuePort, ConsumerConfig consumerConfig, Consumer consumer) {
        this.host = host;
        this.uuid = post(host + "/api/consumer/reg", JSON.toJSONString(consumerConfig));
        this.taskQueue = new TaskQueue(queueHost, queuePort, consumerConfig.getName());
        this.dataQueue = new DataQueue(queueHost, queuePort, consumerConfig.getName(), consumer);
    }

    public String getRunningTasks() {
        return requestGet(host + "/api/consumer/running-task?uuid=" + uuid);
    }

    public ResourceInfo getResourceInfo() {
        return JSON.parseObject(requestGet(host + "/api/consumer/resource-info?uuid=" + uuid), ResourceInfo.class);
    }


    public void addTask(TaskInfo taskInfo) {
        taskQueue.addTask(taskInfo);
    }

    public String getTaskSummary() {
        return requestGet(host + "/api/consumer/task-summary?uuid=" + uuid);
    }

    private String requestGet(String url) {
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("request error : " + e.getMessage(), e);
        }
    }

    private String post(String url, String body) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        try {
            HttpEntity entity = new ByteArrayEntity(body.getBytes("UTF-8"));
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("request error : " + e.getMessage(), e);
        }
    }

    public void setDateQueueThread(int num) {
        this.dataQueue.setThreadNum(num);
    }

    public void start() {
        this.dataQueue.start();
    }
}
