package com.brotherbin.antbench.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import com.brotherbin.antbench.base.AntConfiguration;
import com.brotherbin.antbench.ds.DruidDatasource;

/**
 * 压测初始化
 * @author brotherbin
 *
 */
public class BenchInitialization {
	
	public static final Logger LOGGER = Logger.getLogger(BenchInitialization.class);
	
	public void init() {
		initTable();
		initData();
	}
	
	/**
	 * 建表
	 */
	private void initTable() {
		try (Connection conn = DruidDatasource.INSTANCE.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = null) {
			String sql1 = "drop table if exists t1_loader";
			String sql2 = "CREATE TABLE `t1_loader` ("
					+ "id INT NOT NULL AUTO_INCREMENT,"
					+ "intcol1 INT ,"
					+ "intcol2 INT ,"
					+ "intcol3 INT ,"
					+ "intcol4 INT ,"
					+ "intcol5 INT ,"
					+ "charcol1 VARCHAR(128),"
					+ "charcol2 VARCHAR(128),"
					+ "charcol3 VARCHAR(128),"
					+ "charcol4 VARCHAR(128),"
					+ "charcol5 VARCHAR(128),"
					+ "charcol6 VARCHAR(128),"
					+ "charcol7 VARCHAR(128),"
					+ "charcol8 VARCHAR(128),"
					+ "charcol9 VARCHAR(128),"
					+ "charcol10 VARCHAR(128),"
					+ "PRIMARY KEY (id), "
					+ "KEY(intcol1),"
					+ "KEY(charcol1)"
					+ ");";
			stmt.execute(sql1);
			stmt.execute(sql2);
			LOGGER.info("created table t1_loader : 6 int columns and 10 varchar columns !");
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	/**
	 * 初始化表数据
	 */
	private void initData() {
		String sql = "insert into t1_loader("
				+ "intcol1,intcol2,intcol3,intcol4,intcol5,"
				+ "charcol1,charcol2,charcol3,charcol4,charcol5,"
				+ "charcol6,charcol7,charcol8,charcol9,charcol10) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		// 需要初始化的数据的总行数
		int initDataRows = AntConfiguration.INSTANCE.getInitDataRows();
		// 批量插入的数据量
		int initBatchSize = AntConfiguration.INSTANCE.getInitBatchSize();
		// 批量操作次数
		int batchCounts = initDataRows / initBatchSize;
		// 批量操作后剩下的数据量
		int remainder = initDataRows % initBatchSize;
		try (Connection conn = DruidDatasource.INSTANCE.getConnection();
				PreparedStatement preStmt = conn.prepareStatement(sql)) {
			for (int i = 0 ; i < batchCounts; i ++) {
				for (int j = 0; j < initBatchSize; j ++) {
					preStmt.setInt(1, getInt());
					preStmt.setInt(2, getInt());
					preStmt.setInt(3, getInt());
					preStmt.setInt(4, getInt());
					preStmt.setInt(5, getInt());
					preStmt.setString(6, getStr());
					preStmt.setString(7, getStr());
					preStmt.setString(8, getStr());
					preStmt.setString(9, getStr());
					preStmt.setString(10, getStr());
					preStmt.setString(11, getStr());
					preStmt.setString(12, getStr());
					preStmt.setString(13, getStr());
					preStmt.setString(14, getStr());
					preStmt.setString(15, getStr());
					preStmt.addBatch();
				}
				preStmt.executeBatch();
			}
			for (int k = 0; k < remainder; k ++) {
				preStmt.setInt(1, getInt());
				preStmt.setInt(2, getInt());
				preStmt.setInt(3, getInt());
				preStmt.setInt(4, getInt());
				preStmt.setInt(5, getInt());
				preStmt.setString(6, getStr());
				preStmt.setString(7, getStr());
				preStmt.setString(8, getStr());
				preStmt.setString(9, getStr());
				preStmt.setString(10, getStr());
				preStmt.setString(11, getStr());
				preStmt.setString(12, getStr());
				preStmt.setString(13, getStr());
				preStmt.setString(14, getStr());
				preStmt.setString(15, getStr());
				preStmt.addBatch();
			}
			if (remainder > 0) {
				preStmt.executeBatch();
			}
			LOGGER.info("init data: " + initDataRows + " rows !");
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	/**
	 * 获取随机整数
	 * @return
	 */
	public int getInt() {
		return RandomUtils.nextInt(1, Integer.MAX_VALUE);
	}
	
	/**
	 * 获取随机字符串，由字母和数字组成，长度为1-128位
	 * @return
	 */
	public String getStr() {
		return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(1, 128));
	}
}
