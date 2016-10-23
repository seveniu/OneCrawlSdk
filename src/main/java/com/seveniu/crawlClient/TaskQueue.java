package com.seveniu.crawlClient;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

/**
 * Created by seveniu on 10/21/16.
 * *
 */
public class TaskQueue {
    private final static String PREFIX = "task-";
    private Jedis jedis;
    private String host;
    private int port;
    private String key;

    public TaskQueue(String host, int port, String key) {
        this.host = host;
        this.port = port;
        this.key = PREFIX + key;
        this.jedis = new Jedis(host,port);
    }

    public void addTask(TaskInfo taskInfo) {
        if(taskInfo.getPriority() > 0) {
            jedis.lpush(key, JSON.toJSONString(taskInfo));
        } else {
            jedis.rpush(key, JSON.toJSONString(taskInfo));
        }
    }
}
