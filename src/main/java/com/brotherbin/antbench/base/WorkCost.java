package com.brotherbin.antbench.base;

public class WorkCost implements Comparable<Long> {
	
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	
	private int status;
	private int value;

	public boolean isSuccess() {
		return status == SUCCESS;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(long value) {
		int v = (int) value;
		setValue(v);
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setSuccess() {
		this.status = SUCCESS;
	}
	
	public void setFailure() {
		this.status = FAILURE;
	}
	
	@Override
	public int compareTo(Long o) {
		if (this.value > o) {
			return 1;
		} else if (this.value < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
