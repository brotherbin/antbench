package com.brotherbin.antbench.ds;

import com.alibaba.druid.pool.DruidDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDatasource {

	private static String jdbcUrl;
	private static String username;
	private static String password;
	private static String driverClassName;
	private static int initialSize;
	private static int maxActive;
	private static int minIdle;
	private static int maxWait;
	private static boolean poolPreparedStatements;
	private static int maxOpenPreparedStatements;
	private DruidDataSource dataSource;

	public static final DruidDatasource INSTANCE = new DruidDatasource("jdbc.properties");

	public void init(String cfg){
		Properties prop = new Properties();
		try {
			prop.load(DruidDatasource.class.getClassLoader().getResourceAsStream(cfg));
			jdbcUrl = prop.getProperty("druid.jdbcUrl");
			username = prop.getProperty("druid.username");
			password = prop.getProperty("druid.password");
			driverClassName = prop.getProperty("druid.driverClassName");
			initialSize = Integer.parseInt(prop.getProperty("druid.initialSize"));
			maxActive = Integer.parseInt(prop.getProperty("druid.maxActive"));
			minIdle = Integer.parseInt(prop.getProperty("druid.minIdle"));
			maxWait = Integer.parseInt(prop.getProperty("druid.maxWait"));
			poolPreparedStatements = Boolean.parseBoolean(prop.getProperty("druid.poolPreparedStatements"));
			maxOpenPreparedStatements = Integer.parseInt(prop.getProperty("druid.maxOpenPreparedStatements"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private DruidDatasource(String cfg) {
		init(cfg);
    	dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
    }

	public Connection getConnection() {
        try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
     
    public void releaseConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}