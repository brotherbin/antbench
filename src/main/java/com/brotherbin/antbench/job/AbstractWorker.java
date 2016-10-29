package com.brotherbin.antbench.job;

import org.apache.log4j.Logger;

import com.brotherbin.antbench.base.AntConfiguration;
import com.brotherbin.antbench.base.WorkCost;

public abstract class AbstractWorker implements Runnable {
	
	public static final Logger LOGGER = Logger.getLogger(AbstractWorker.class);
	
	private AntLeader leader = null;
	
	public AbstractWorker(AntLeader leader) {
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

	protected abstract WorkCost work();
	
}
