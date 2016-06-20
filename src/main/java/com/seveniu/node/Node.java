package com.seveniu.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seveniu on 5/12/16.
 * Node
 */
public class Node {
    private String url;
    private String taskId;
    private List<PageResult> pages = new ArrayList<>();

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPages(List<PageResult> pages) {
        this.pages = pages;
    }

    public String getUrl() {
        return url;
    }

    public List<PageResult> getPages() {
        return pages;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Node{" +
                "url='" + url + '\'' +
                ", pages=" + pages +
                '}';
    }
}
