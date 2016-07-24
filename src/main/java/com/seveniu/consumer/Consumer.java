package com.seveniu.consumer;

import com.seveniu.def.TaskStatus;
import com.seveniu.node.Node;
import com.seveniu.task.CrawlTaskStatistic;

/**
 * Created by seveniu on 5/26/16.
 * Consumer
 */
public interface Consumer {

    boolean has(String url);

    void done(Node node);

    void statistic(CrawlTaskStatistic statistic);

    void taskStatusChange(String taskId, TaskStatus taskStatus);


}
