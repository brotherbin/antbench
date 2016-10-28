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
	 * 压测总耗时，单位：S
	 */
	private int cost;
	
	public AntLeader() {
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
		this.cost = (int)(endTime - startTime) / 1000 ;
		report();
	}
	
	/**
	 * 派发任务
	 */
	public void dispatchWork() {
		int concurrency = AntConfiguration.INSTANCE.getConcurrency();
		for (int i = 0; i < concurrency; i ++) {
			new Thread(new AntWorker(this)).start();
		}
	}
	
	/**
	 * 计算并记录结果
	 */
	public void report() {
		tps =  exceptedWorkNum / this.cost;
		LOGGER.info("concurrency: " + AntConfiguration.INSTANCE.getConcurrency());
		LOGGER.info("exceped transactions: " + exceptedWorkNum);
		LOGGER.info("success transactions: " + this.successCounter.get());
		LOGGER.info("failure transactions: " + this.failureCounter.get());
		LOGGER.info("sum cost: " + cost + "s");
		LOGGER.info("TPS : " + tps);
		LOGGER.info("-----------finished-----------");
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
