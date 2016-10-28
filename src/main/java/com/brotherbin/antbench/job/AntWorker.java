package com.brotherbin.antbench.job;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import com.brotherbin.antbench.base.AntConfiguration;
import com.brotherbin.antbench.base.WorkCost;
import com.brotherbin.antbench.ds.DruidDatasource;

/**
 * 数据库操作工作者
 * @author 833901
 *
 */
public class AntWorker implements Runnable {

	public static final Logger LOGGER = Logger.getLogger(AntWorker.class);
	
	private AntLeader leader = null;
	
	public AntWorker(AntLeader leader) {
		this.leader = leader;
	}
	
	@Override
	public void run() {
		int iterations = AntConfiguration.INSTANCE.getIterations();
		WorkCost cost = null;
		for (int i = 0; i < iterations; i ++) {
			cost = work();
			if (cost.isSuccess()) {
				this.leader.countSuccess();
			} else {
				this.leader.countFailure();
			}
		}
	}
	
	/**
	 * 事务操作：
	 * 1.insert
	 * 2.select by primary key
	 * 3.select by index
	 * 4.select by index
	 * @return
	 */
	public WorkCost work() {
		WorkCost cost = new WorkCost();
		String sql1 = "insert into t1_loader("
				+ "intcol1,intcol2,intcol3,intcol4,intcol5,"
				+ "charcol1,charcol2,charcol3,charcol4,charcol5,"
				+ "charcol6,charcol7,charcol8,charcol9,charcol10) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql2 = "select * from t1_loader where id = ?";
		String sql3 = "select * from t1_loader where intcol1 = ?";
		String sql4 = "select * from t1_loader where charcol1 = ?";
		long startTime = System.currentTimeMillis();
		try (Connection conn = DruidDatasource.INSTANCE.getConnection();
				PreparedStatement preStmt1 = conn.prepareStatement(sql1);
				PreparedStatement preStmt2 = conn.prepareStatement(sql2);
				PreparedStatement preStmt3 = conn.prepareStatement(sql3);
				PreparedStatement preStmt4 = conn.prepareStatement(sql4);){
			conn.setAutoCommit(false);
			
			preStmt1.setInt(1, getInt());
			preStmt1.setInt(2, getInt());
			preStmt1.setInt(3, getInt());
			preStmt1.setInt(4, getInt());
			preStmt1.setInt(5, getInt());
			preStmt1.setString(6, getStr());
			preStmt1.setString(7, getStr());
			preStmt1.setString(8, getStr());
			preStmt1.setString(9, getStr());
			preStmt1.setString(10, getStr());
			preStmt1.setString(11, getStr());
			preStmt1.setString(12, getStr());
			preStmt1.setString(13, getStr());
			preStmt1.setString(14, getStr());
			preStmt1.setString(15, getStr());
			preStmt1.execute();
			
			preStmt2.setInt(1, getInt());
			preStmt2.executeQuery();
			
			preStmt3.setInt(1, getInt());
			preStmt3.executeQuery();
			
			preStmt4.setString(1, getStr());
			preStmt4.executeQuery();
			
			conn.commit();
			cost.setValue(System.currentTimeMillis() - startTime);
			cost.setSuccess();
		} catch (Exception e) {
			cost.setValue(System.currentTimeMillis() - startTime);
			cost.setFailure();
			LOGGER.error(e);
		}
		return cost;
	}
	
	public int getInt() {
		return RandomUtils.nextInt(1, Integer.MAX_VALUE);
	}
	
	public String getStr() {
		return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(1, 128));
	}
	
}
