package com.brotherbin.antbench;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.brotherbin.antbench.base.WorkCost;
import com.brotherbin.antbench.job.AntLeader;
import com.brotherbin.antbench.job.EnvInitWorker;
import com.brotherbin.antbench.job.QpsWorker;
import com.brotherbin.antbench.job.TpsWorker;

public class WorkerTest {

	@Before
	public void testInit() {
		EnvInitWorker init = new EnvInitWorker();
		init.start();
	}
	
	@Test
	public void testTpsWork() {
		TpsWorker worker = new TpsWorker(new AntLeader(AntLeader.TYPE_TPS));
		WorkCost cost = worker.work();
		Assert.assertTrue(cost.getValue() > 0 && cost.isSuccess());
	}
	
	@Test
	public void testQpsWork() {
		QpsWorker worker = new QpsWorker(new AntLeader(AntLeader.TYPE_QPS));
		WorkCost cost = worker.work();
		Assert.assertTrue(cost.getValue() > 0 && cost.isSuccess());
	}
}
