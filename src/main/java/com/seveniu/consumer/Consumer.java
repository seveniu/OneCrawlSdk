package com.seveniu.consumer;

import com.seveniu.node.Node;
import com.seveniu.task.CrawlTaskStatistic;
import com.seveniu.thrift.TaskStatus;

/**
 * Created by seveniu on 5/26/16.
 */
public interface Consumer {

    boolean has(String url);

    void done(Node node);

    void statistic(CrawlTaskStatistic statistic);

    void taskStatusChange(String taskId, TaskStatus taskStatus);


}
