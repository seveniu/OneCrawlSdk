package com.seveniu.util;

import java.io.IOException;
import java.sql.*;
import java.util.*;


public class DBUtil {

    private static Connection con = null;

    public synchronized static Connection openConnection() throws SQLException, ClassNotFoundException, IOException {
        if (null == con || con.isClosed()) {
            Properties p = new Properties();
            p.load(DBUtil.class.getResourceAsStream("/dataQueue.properties"));
            Class.forName(p.getProperty("driverClassName"));
            con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
                    p.getProperty("password"));
        }
        return con;
    }

    public synchronized static void closeConnection() throws SQLException {
        try {
            if (null != con)
                con.close();
        } finally {
            con = null;
        }
    }


    public static void update(String sql, Object... params)
            throws SQLException {
        PreparedStatement preStmt = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            preStmt.executeUpdate();
        } finally {
            if (null != preStmt)
                preStmt.close();
        }
    }

    public static Map<String, Object> queryMap(String sql, Object... params)
            throws SQLException {
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            rs = preStmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < columnCount; i++) {
                    String name = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(name);
                    map.put(name, value);
                }
                return map;
            }
            return null;
        } finally {
            if (null != rs)
                rs.close();
            if (null != preStmt)
                preStmt.close();
        }
    }

    public static List<Map<String, Object>> queryMapList(String sql, Object... params)
            throws SQLException {
        List<Map<String, Object>> lists = new ArrayList<>();
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                preStmt.setObject(i + 1, params[i]);// 下标从1开始
            rs = preStmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < columnCount; i++) {
                    String name = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(name);
                    map.put(name, value);
                }
                lists.add(map);
            }
        } finally {
            if (null != rs)
                rs.close();
            if (null != preStmt)
                preStmt.close();
        }
        return lists;
    }


}