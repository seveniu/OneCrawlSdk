package com.seveniu;

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
        new DataQueue("asdf").start();
        TimeUnit.SECONDS.sleep(100);
    }

}