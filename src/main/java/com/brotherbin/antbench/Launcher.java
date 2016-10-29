package com.brotherbin.antbench;

import java.io.File;
import java.io.IOException;

import com.brotherbin.antbench.job.EnvInitWorker;
import com.brotherbin.antbench.job.AntLeader;

/**
 * 压测程序启动入口类
 * 
 * @author brotherbin
 */
public class Launcher {

	public static final String SYS_HOME = "ANT_HOME";
	
	public static final String QPS = "QPS";

	/**
	 * 入口方法
	 * 
	 * @param args
	 *            项目部署目录LOADER_HOME
	 */
	public static void main(String[] args) {
		if (getHomePath()==null) {
			System.out.println(SYS_HOME + "is not set!!!");
		}
		new EnvInitWorker().start();
		if (args!=null && args.length == 1 && QPS.equalsIgnoreCase(args[0])) {
			new Thread(new AntLeader(AntLeader.TYPE_QPS)).start();
		} else {
			new Thread(new AntLeader(AntLeader.TYPE_TPS)).start();
		}
		
	}

	private static String getHomePath() {
		String home = System.getProperty(SYS_HOME);
		if (home != null && home.endsWith(File.pathSeparator)) {
			home = home.substring(0, home.length() - 1);
			System.setProperty(SYS_HOME, home);
		}
		//HOME为空，默认尝试设置为上级目录
		if (home == null) {
			try {
				home = new File("..").getCanonicalPath();
				File homeFile = new File(home);
				if (homeFile.exists() && homeFile.isDirectory()) {
					System.setProperty(SYS_HOME, home);
				}
			} catch (IOException e) {
				// 如出错，则忽略。
			}
		}
		return home;
	}
}
