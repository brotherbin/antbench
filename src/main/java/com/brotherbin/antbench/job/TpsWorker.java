package com.brotherbin.antbench.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.brotherbin.antbench.base.RandomTool;
import com.brotherbin.antbench.base.WorkCost;
import com.brotherbin.antbench.ds.DruidDatasource;

/**
 * 数据库操作工作者
 * @author 833901
 *
 */
public class TpsWorker extends AbstractWorker {
	
	private RandomTool random = null;

	public TpsWorker(AntLeader leader) {
		super(leader);
		this.random = new RandomTool();
	}

	/**
	 * 事务操作：
	 * 1.insert
	 * 2.get last id
	 * 3.insert
	 * @return
	 */
	public WorkCost work() {
		WorkCost cost = new WorkCost();
		String sql1 = "insert into t1_ant(intcol1,intcol2,intcol3,intcol4,intcol5,"
				+ "charcol1,charcol2,charcol3,charcol4,charcol5,"
				+ "charcol6,charcol7,charcol8,charcol9,charcol10) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql2 = "insert into t2_ant(id,intcol1,intcol2,intcol3,intcol4,intcol5,"
				+ "charcol1,charcol2,charcol3,charcol4,charcol5,"
				+ "charcol6,charcol7,charcol8,charcol9,charcol10) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		long startTime = System.currentTimeMillis();
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement preStmt1 = null, preStmt2 = null;
		try {
			conn = DruidDatasource.INSTANCE.getConnection();
			conn.setAutoCommit(false);
			preStmt1 = conn.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
			preStmt2 = conn.prepareStatement(sql2);
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
			preStmt1.executeUpdate();
			
			rs = preStmt1.getGeneratedKeys();
			int lastId = rs.next() ? rs.getInt(1) : random.getInt();
			
			preStmt2.setString(1, random.getStrPk());
			preStmt2.setInt(2, lastId);
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
			preStmt2.execute();
			
			conn.commit();
			
			cost.setValue(System.currentTimeMillis() - startTime);
			cost.setSuccess();
		} catch (Exception e) {
			cost.setValue(System.currentTimeMillis() - startTime);
			cost.setFailure();
			LOGGER.error(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (preStmt1 != null) preStmt1.close();
				if (preStmt2 != null) preStmt2.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {
				LOGGER.error(e2);
			}
		}
		return cost;
	}
}
