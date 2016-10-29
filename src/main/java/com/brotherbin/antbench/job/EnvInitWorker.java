package com.brotherbin.antbench.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.brotherbin.antbench.base.AntConfiguration;
import com.brotherbin.antbench.base.RandomTool;
import com.brotherbin.antbench.ds.DruidDatasource;

/**
 * 压测初始化
 * @author brotherbin
 *
 */
public class EnvInitWorker {
	
	public static final Logger LOGGER = Logger.getLogger(EnvInitWorker.class);
	
	private RandomTool random = null;
	
	public EnvInitWorker() {
		this.random = new RandomTool();
	}
	
	public void start() {
		initTable();
		initData();
	}
	
	/**
	 * 建表
	 */
	private boolean initTable() {
		try (Connection conn = DruidDatasource.INSTANCE.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = null) {
			String sql1 = "drop table if exists t1_ant";
			String sql2 = "CREATE TABLE t1_ant ("
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
			LOGGER.info("create table t1_ant:[auto_inc pk, 2 index, 16 cols]");
			
			String sql3 = "drop table if exists t2_ant";
			String sql4 = "CREATE TABLE `t2_ant` ("
					+ "id VARCHAR(64) NOT NULL,"
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
			stmt.execute(sql3);
			stmt.execute(sql4);
			LOGGER.info("create table t2_ant:[varchar pk, 2 index, 16 cols]");
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 初始化表数据
	 */
	private boolean initData() {
		String sql1 = "insert into t1_ant(intcol1,intcol2,intcol3,intcol4,intcol5,"
				+ "charcol1,charcol2,charcol3,charcol4,charcol5,"
				+ "charcol6,charcol7,charcol8,charcol9,charcol10) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql2 = "insert into t2_ant(id,intcol1,intcol2,intcol3,intcol4,intcol5,"
				+ "charcol1,charcol2,charcol3,charcol4,charcol5,"
				+ "charcol6,charcol7,charcol8,charcol9,charcol10) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		// 需要初始化的数据的总行数
		int initDataRows = AntConfiguration.INSTANCE.getInitDataRows();
		// 批量插入的数据量
		int initBatchSize = AntConfiguration.INSTANCE.getInitBatchSize();
		// 批量操作次数
		int batchCounts = initDataRows / initBatchSize;
		// 批量操作后剩下的数据量
		int remainder = initDataRows % initBatchSize;
		Connection conn1 = null, conn2 = null;
		PreparedStatement preStmt1 = null, preStmt2 = null;
		try {
			conn1 = DruidDatasource.INSTANCE.getConnection();
			conn2 = DruidDatasource.INSTANCE.getConnection();
			preStmt1 = conn1.prepareStatement(sql1);
			preStmt2 = conn2.prepareStatement(sql2);
			for (int i = 0 ; i < batchCounts; i ++) {
				for (int j = 0; j < initBatchSize; j ++) {
					preStmt1.setInt(1, random.getInt());
					preStmt1.setInt(2, random.getInt());
					preStmt1.setInt(3, random.getInt());
					preStmt1.setInt(4, random.getInt());
					preStmt1.setInt(5, random.getInt());
					preStmt1.setString(6, random.getStr());
					preStmt1.setString(7, random.getStr());
					preStmt1.setString(8, random.getStr());
					preStmt1.setString(9, random.getStr());
					preStmt1.setString(10, random.getStr());
					preStmt1.setString(11, random.getStr());
					preStmt1.setString(12, random.getStr());
					preStmt1.setString(13, random.getStr());
					preStmt1.setString(14, random.getStr());
					preStmt1.setString(15, random.getStr());
					preStmt1.addBatch();
					
					preStmt2.setString(1, random.getStrPk());
					preStmt2.setInt(2, random.getInt());
					preStmt2.setInt(3, random.getInt());
					preStmt2.setInt(4, random.getInt());
					preStmt2.setInt(5, random.getInt());
					preStmt2.setInt(6, random.getInt());
					preStmt2.setString(7, random.getStr());
					preStmt2.setString(8, random.getStr());
					preStmt2.setString(9, random.getStr());
					preStmt2.setString(10, random.getStr());
					preStmt2.setString(11, random.getStr());
					preStmt2.setString(12, random.getStr());
					preStmt2.setString(13, random.getStr());
					preStmt2.setString(14, random.getStr());
					preStmt2.setString(15, random.getStr());
					preStmt2.setString(16, random.getStr());
					preStmt2.addBatch();
				}
				preStmt1.executeBatch();
				preStmt2.executeBatch();
			}
			for (int k = 0; k < remainder; k ++) {
				preStmt1.setInt(1, random.getInt());
				preStmt1.setInt(2, random.getInt());
				preStmt1.setInt(3, random.getInt());
				preStmt1.setInt(4, random.getInt());
				preStmt1.setInt(5, random.getInt());
				preStmt1.setString(6, random.getStr());
				preStmt1.setString(7, random.getStr());
				preStmt1.setString(8, random.getStr());
				preStmt1.setString(9, random.getStr());
				preStmt1.setString(10, random.getStr());
				preStmt1.setString(11, random.getStr());
				preStmt1.setString(12, random.getStr());
				preStmt1.setString(13, random.getStr());
				preStmt1.setString(14, random.getStr());
				preStmt1.setString(15, random.getStr());
				preStmt1.addBatch();
				
				preStmt2.setString(1, random.getStrPk());
				preStmt2.setInt(2, random.getInt());
				preStmt2.setInt(3, random.getInt());
				preStmt2.setInt(4, random.getInt());
				preStmt2.setInt(5, random.getInt());
				preStmt2.setInt(6, random.getInt());
				preStmt2.setString(7, random.getStr());
				preStmt2.setString(8, random.getStr());
				preStmt2.setString(9, random.getStr());
				preStmt2.setString(10, random.getStr());
				preStmt2.setString(11, random.getStr());
				preStmt2.setString(12, random.getStr());
				preStmt2.setString(13, random.getStr());
				preStmt2.setString(14, random.getStr());
				preStmt2.setString(15, random.getStr());
				preStmt2.setString(16, random.getStr());
				preStmt2.addBatch();
			}
			if (remainder > 0) {
				preStmt1.executeBatch();
				preStmt2.executeBatch();
			}
			LOGGER.info("init data: " + initDataRows + " rows !");
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return false;
		} finally {
			try {
				if (preStmt1 != null) preStmt1.close();
				if (preStmt2 != null) preStmt2.close();
				if (conn1 != null) conn1.close();
				if (conn2 != null) conn2.close();
			} catch (Exception e2) {
				LOGGER.error(e2);
			}
		}
		return true;
	}
	
}
