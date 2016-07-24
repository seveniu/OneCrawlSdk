package com.seveniu.task;


import com.seveniu.common.json.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by seveniu on 5/15/16.
 * TaskStatistic
 */
public class CrawlTaskStatistic {
    private String taskId;
    private Date startTime;
    private Date endTime;
    private int startUrlCount = 0;
    private int createUrlCount;
    private int createTargetUrlCount;
    private int createNextUrlCount;
    private int successUrlCount;
    private int repeatUrlCount;
    private int netErrorUrlCount;
    private int doneUrlCount;
    private int parseErrorCount;
    private int createNodeCount;
    private int doneNodeCount;
    private int errorNodeCount;
    private List<String> netErrorUrlList = new ArrayList<>();
    private List<String> parseErrorUrlList = new ArrayList<>();

    // startUrlCount + createUrlCount = repeatUrlCount + successUrlCount + netErrorUrlCount
    // startUrlCount + createUrlCount = repeatUrlCount + createTargetUrlCount + createNextUrlCount
    // successUrlCount = doneUrlCount + parseErrorCount
    // createNodeCount = doneNodeCount + errorNodeCount


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStartUrlCount() {
        return startUrlCount;
    }

    public void setStartUrlCount(int startUrlCount) {
        this.startUrlCount = startUrlCount;
    }

    public int getCreateUrlCount() {
        return createUrlCount;
    }

    public void setCreateUrlCount(int createUrlCount) {
        this.createUrlCount = createUrlCount;
    }

    public int getCreateTargetUrlCount() {
        return createTargetUrlCount;
    }

    public void setCreateTargetUrlCount(int createTargetUrlCount) {
        this.createTargetUrlCount = createTargetUrlCount;
    }

    public int getCreateNextUrlCount() {
        return createNextUrlCount;
    }

    public void setCreateNextUrlCount(int createNextUrlCount) {
        this.createNextUrlCount = createNextUrlCount;
    }

    public int getSuccessUrlCount() {
        return successUrlCount;
    }

    public void setSuccessUrlCount(int successUrlCount) {
        this.successUrlCount = successUrlCount;
    }

    public int getRepeatUrlCount() {
        return repeatUrlCount;
    }

    public void setRepeatUrlCount(int repeatUrlCount) {
        this.repeatUrlCount = repeatUrlCount;
    }

    public int getNetErrorUrlCount() {
        return netErrorUrlCount;
    }

    public void setNetErrorUrlCount(int netErrorUrlCount) {
        this.netErrorUrlCount = netErrorUrlCount;
    }

    public int getDoneUrlCount() {
        return doneUrlCount;
    }

    public void setDoneUrlCount(int doneUrlCount) {
        this.doneUrlCount = doneUrlCount;
    }

    public int getParseErrorCount() {
        return parseErrorCount;
    }

    public void setParseErrorCount(int parseErrorCount) {
        this.parseErrorCount = parseErrorCount;
    }

    public int getCreateNodeCount() {
        return createNodeCount;
    }

    public void setCreateNodeCount(int createNodeCount) {
        this.createNodeCount = createNodeCount;
    }

    public int getDoneNodeCount() {
        return doneNodeCount;
    }

    public void setDoneNodeCount(int doneNodeCount) {
        this.doneNodeCount = doneNodeCount;
    }

    public int getErrorNodeCount() {
        return errorNodeCount;
    }

    public void setErrorNodeCount(int errorNodeCount) {
        this.errorNodeCount = errorNodeCount;
    }

    public List<String> getNetErrorUrlList() {
        return netErrorUrlList;
    }

    public void setNetErrorUrlList(List<String> netErrorUrlList) {
        this.netErrorUrlList = netErrorUrlList;
    }

    public List<String> getParseErrorUrlList() {
        return parseErrorUrlList;
    }

    public void setParseErrorUrlList(List<String> parseErrorUrlList) {
        this.parseErrorUrlList = parseErrorUrlList;
    }

    @Override
    public String toString() {
        return "TaskStatistic{" +
                "startUrlCount=" + startUrlCount +
                ", createUrlCount=" + createUrlCount +
                ", createTargetUrlCount=" + createTargetUrlCount +
                ", createNextUrlCount=" + createNextUrlCount +
                ", successUrlCount=" + successUrlCount +
                ", repeatUrlCount=" + repeatUrlCount +
                ", netErrorUrlCount=" + netErrorUrlCount +
                ", doneUrlCount=" + doneUrlCount +
                ", parseErrorCount=" + parseErrorCount +
                ", createNodeCount=" + createNodeCount +
                ", doneNodeCount=" + doneNodeCount +
                ", errorNodeCount=" + errorNodeCount +
                '}';
    }

    public static void main(String[] args) {
        String json = "{\"taskId\":\"50247\",\"startTime\":1469212411259,\"endTime\":1469212457163,\"startUrlCount\":3,\"createUrlCount\":41,\"createTargetUrlCount\":38,\"createNextUrlCount\":0,\"successUrlCount\":41,\"repeatUrlCount\":0,\"netErrorUrlCount\":0,\"doneUrlCount\":41,\"parseErrorCount\":0,\"createNodeCount\":38,\"doneNodeCount\":38,\"errorNodeCount\":0,\"netErrorUrlList\":[],\"parseErrorUrlList\":[]}";
        CrawlTaskStatistic statistic = Json.toObject(json, CrawlTaskStatistic.class);
        System.out.println(statistic);
    }
}
