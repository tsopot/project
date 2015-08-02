package com.project.httpserver.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;

public class DaoFactory {

    private static final String URL = "jdbc:mysql://localhost/http_server";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /*private static DataSource dataSource;

    static {
        DriverAdapterCPDS adapterCPDS = new DriverAdapterCPDS();

        try {
            adapterCPDS.setDriver(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapterCPDS.setUrl(URL);
        adapterCPDS.setUser(USER);
        adapterCPDS.setPassword(PASSWORD);

        SharedPoolDataSource poolDataSource = new SharedPoolDataSource();
        poolDataSource.setConnectionPoolDataSource(adapterCPDS);

        dataSource = poolDataSource;
    }*/

    private static ComboPooledDataSource cpds;

    static {
        try {
            cpds = new ComboPooledDataSource();

            cpds.setDriverClass(DRIVER);
            cpds.setJdbcUrl(URL);
            cpds.setUser(USER);
            cpds.setPassword(PASSWORD);
            cpds.setMaxStatements(100);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static RequestJdbcDao getRequestJdbcDao() { return new RequestJdbcDao(cpds);}

}

