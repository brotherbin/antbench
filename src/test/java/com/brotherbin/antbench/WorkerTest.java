package com.brotherbin.antbench;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.brotherbin.antbench.base.WorkCost;
import com.brotherbin.antbench.job.BenchInitialization;
import com.brotherbin.antbench.job.AntLeader;
import com.brotherbin.antbench.job.AntWorker;

public class WorkerTest {

	@Before
	public void testInit() {
		BenchInitialization init = new BenchInitialization();
		init.init();
	}
	
	@Test
	public void testWork() {
		AntWorker worker = new AntWorker(new AntLeader());
		WorkCost cost = worker.work();
		Assert.assertTrue(cost.getValue() > 0 && cost.isSuccess());
	}
}
