package com.brotherbin.thread;

public class Calculator implements Runnable {

	private int number;

	public Calculator(int num) {
		this.number = num;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			System.out.printf("%s: %d * %d = %d", Thread.currentThread().getName(), number, i, i * number);
		}
	}

}
