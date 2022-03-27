package com.xdushepherd.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wangqianyi03
 * @date 2022/3/27 20:53
 */
public class SqlSession {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:tcp://localhost/~/test";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Connection dbConnection = null;
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }

}
