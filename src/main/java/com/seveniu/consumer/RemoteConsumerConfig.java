package com.seveniu.consumer;

import com.seveniu.common.json.Json;
import com.seveniu.common.str.StrUtil;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * Created by seveniu on 5/23/16.
 * ConsumerConfig
 */
public class RemoteConsumerConfig {
    private String name;
    private String type;// http, thrift

    // http
    private String duplicateUrl;
    private String doneUrl;
    private String statisticUrl;
    private String taskUrl;

    // thrift
    private String host;
    private int port;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuplicateUrl() {
        return duplicateUrl;
    }

    public void setDuplicateUrl(String duplicateUrl) {
        this.duplicateUrl = duplicateUrl;
    }

    public String getDoneUrl() {
        return doneUrl;
    }

    public void setDoneUrl(String doneUrl) {
        this.doneUrl = doneUrl;
    }

    public String getStatisticUrl() {
        return statisticUrl;
    }

    public void setStatisticUrl(String statisticUrl) {
        this.statisticUrl = statisticUrl;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void validate() throws IllegalArgumentException {
        RemoteConsumerConfig config = this;

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

    public static RemoteConsumerConfig fromJson(String data) throws IllegalArgumentException {
        RemoteConsumerConfig config = Json.toObject(data, RemoteConsumerConfig.class);
        config.validate();
        return config;
    }

    public String toJson() {
        return Json.toJson(this);
    }
}

