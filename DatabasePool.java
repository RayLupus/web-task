package com.alibaba.intl.qa.ui.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaochuan.luxc 2014-10-9
 */
public class DatabasePool {

    public static Map<Integer, Connection> pool = new HashMap<Integer, Connection>();
    static {
        try {
            Driver oracleDriver = new oracle.jdbc.driver.OracleDriver();
            Driver mysqlDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(oracleDriver);
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException e) {
            throw new RuntimeException("aui.agent初始化失败");
        }
    }

    public static Integer registerConnection(String dbUrl, String username, String password) {
        int key = (dbUrl + username + password).hashCode();
        if (pool.containsKey(key)) {
            return key;
        }
        try {
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            pool.put(key, conn);
            return key;
        } catch (SQLException e) {
            return null;
        }
    }

    public static Connection getConnection(Integer key) {
        if (key == null) return null;
        return pool.get(key);
    }

    public static Connection getConnecttion(String dbUrl, String username, String password) {
        int key = (dbUrl + username + password).hashCode();
        if (pool.containsKey(key)) {
            return pool.get(key);
        }
        try {
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            pool.put(key, conn);
            return conn;
        } catch (SQLException e) {
            return null;
        }
    }

}
