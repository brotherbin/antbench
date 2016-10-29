package com.brotherbin.antbench.job;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.brotherbin.antbench.base.RandomTool;
import com.brotherbin.antbench.base.WorkCost;
import com.brotherbin.antbench.ds.DruidDatasource;

public class QpsWorker extends AbstractWorker {

	private RandomTool random = null;
	
	public QpsWorker(AntLeader leader) {
		super(leader);
		this.random = new RandomTool();
	}

	@Override
	public WorkCost work() {
		WorkCost cost = new WorkCost();
		String sql = "select * from t1_ant where id = ?";
		long startTime = System.currentTimeMillis();
		try(Connection conn = DruidDatasource.INSTANCE.getConnection();
				PreparedStatement preStmt = conn.prepareStatement(sql);) {
			preStmt.setInt(1, random.getInt());
			preStmt.executeQuery();
			cost.setSuccess();
			cost.setValue(System.currentTimeMillis() - startTime);
		} catch (Exception e) {
			cost.setFailure();
			cost.setValue(System.currentTimeMillis() - startTime);
			LOGGER.error(e);
		}
		return cost;
	}

}
