package com.dbclientapp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class DatabaseConnectionManager {
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR = ":mysql:";
    private static final String LOCATION = "//localhost/";
    private static final String DATABASENAME = "client_schedule";
    private static final String JDBCURL = PROTOCOL + VENDOR + LOCATION + DATABASENAME + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String USERNAME = "sqlUser"; // Username
    private static final String PASSWORD = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    public static Connection openConnection()
    {
        try {
            Class.forName(DRIVER); // Locate Driver
            connection = DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
            System.out.println("Connection opened.");
        }
        catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed.");
        } catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}