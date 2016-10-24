package com.seveniu;

import com.seveniu.util.DBUtil;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by seveniu on 10/24/16.
 * *
 */
public class DataQueueTest {
    @Test
    public void start() throws Exception {
//        new DataQueue("a").start();
        TimeUnit.SECONDS.sleep(100);
    }
    @Test
    public void insert() throws Exception {
        DBUtil.openConnection();
        for (int i = 0; i < 200; i++) {
            DBUtil.update("insert into queue (name,data) values ('a','1')");
        }
    }
}