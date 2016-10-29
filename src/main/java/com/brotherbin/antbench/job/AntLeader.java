package com.brotherbin.antbench.job;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.brotherbin.antbench.base.AntConfiguration;

/**
 * 压测程序领导者，负责分配任务和结果计算
 * @author 833901
 *
 */
public class AntLeader implements Runnable {
	
	public static final Logger LOGGER = Logger.getLogger(AntLeader.class);
	
	public static final int TYPE_TPS = 1;
	
	public static final int TYPE_QPS = 2;
	
	private int workType = TYPE_TPS;

	/**
	 * 成功操作计数器
	 */
	private AtomicInteger successCounter = null;
	
	/**
	 * 失败操作计数器
	 */
	private AtomicInteger failureCounter = null;
	
	/**
	 * 配置的任务量
	 */
	private int exceptedWorkNum;
	
	/**
	 * 每秒处理的事务数
	 */
	private int tps;
	
	/**
	 * 每秒处理的查询数
	 */
	private int qps;
	
	/**
	 * 压测总耗时，单位：S
	 */
	private long cost;
	
	public AntLeader(int workType) {
		if (workType == TYPE_QPS) {
			this.workType = TYPE_QPS;
		}
		// 并发数
		int concurrency = AntConfiguration.INSTANCE.getConcurrency();
		// 循环次数
		int iterations = AntConfiguration.INSTANCE.getIterations();
		exceptedWorkNum = concurrency * iterations; 
		this.successCounter = new AtomicInteger(0);
		this.failureCounter = new AtomicInteger(0);
	}
	

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		LOGGER.info("starting......");
		dispatchWork();
		while ((successCounter.get() + failureCounter.get()) < exceptedWorkNum) {
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
		}
		long endTime = System.currentTimeMillis();
		this.cost = endTime - startTime;
		report();
	}
	
	/**
	 * 派发任务
	 */
	public void dispatchWork() {
		int concurrency = AntConfiguration.INSTANCE.getConcurrency();
		for (int i = 0; i < concurrency; i ++) {
			if (this.workType == TYPE_QPS) {
				new Thread(new QpsWorker(this)).start();
			} else {
				new Thread(new TpsWorker(this)).start();
			}
			
		}
	}
	
	/**
	 * 计算并记录结果
	 */
	public void report() {
		
		StringBuilder report = new StringBuilder("\n");
		report.append("concurrency: ").append(AntConfiguration.INSTANCE.getConcurrency()).append("\n");
		if (this.workType == TYPE_TPS) {
			tps =  (int)((long)exceptedWorkNum * 1000 / this.cost);
			report.append("exceped transactions: ").append(exceptedWorkNum).append("\n");
			report.append("success transactions: ").append(this.successCounter.get()).append("\n");
			report.append("failure transactions: ").append(this.failureCounter.get()).append("\n");
			report.append("sum cost: ").append(cost).append("ms\n");
			report.append("TPS : ").append(tps).append("\n");
		} else {
			qps =  (int)((long)exceptedWorkNum * 1000 / this.cost);
			report.append("exceped queries: ").append(exceptedWorkNum).append("\n");
			report.append("success queries: ").append(this.successCounter.get()).append("\n");
			report.append("failure queries: ").append(this.failureCounter.get()).append("\n");
			report.append("sum cost: ").append(cost).append("ms\n");
			report.append("QPS : ").append(qps).append("\n");
		}
		report.append("-----------finished-----------");
		LOGGER.info(report.toString());
	}
	
	/**
	 * 成功计数器加1
	 */
	public void countSuccess() {
		this.successCounter.incrementAndGet();
	}
	
	/**
	 * 失败记录器加1
	 */
	public void countFailure() {
		this.failureCounter.incrementAndGet();
	}

}
