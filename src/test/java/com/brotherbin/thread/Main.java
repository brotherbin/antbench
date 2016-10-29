package com.brotherbin.thread;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class Main {

	public static void main(String[] args) {
		Thread[] threads = new Thread[10];
		State[] states = new State[10];
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread(new Calculator(i), "Thread-" + i);
			threads[i].setPriority(i % 2 == 0 ? Thread.MAX_PRIORITY : Thread.MIN_PRIORITY);
		}
		try (FileWriter file = new FileWriter("D:\\temp\\log.txt"); PrintWriter pw = new PrintWriter(file);) {
			for (int i = 0; i < 10; i++) {
				pw.println("Main : Status of Thread " + i + " : " + threads[i].getState());
				states[i] = threads[i].getState();
			}
			for (int i = 0; i < 10; i++) {
				threads[i].start();
			}
			boolean finish = false;
			while (!finish) {
				for (int i = 0; i < 10; i++) {
					if (threads[i].getState() != states[i]) {
						writeThreadInfo(pw, threads[i], states[i]);
						states[i] = threads[i].getState();
					}
				}

				finish = true;
				for (int i = 0; i < 10; i++) {
					finish = finish && (threads[i].getState() == State.TERMINATED);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeThreadInfo(PrintWriter pw, Thread thread, State state) {
		pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
		pw.printf("Main : Priority: %d\n", thread.getPriority());
		pw.printf("Main : Old State: %s\n", state);
		pw.printf("Main : New State: %s\n", thread.getState());
		pw.printf("Main : ************************************\n");
	}
}
