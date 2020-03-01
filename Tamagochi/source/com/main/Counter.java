package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/***
 * 
 * @author Николай Коптев
 * 
 * Цель данного класса - запретить на некоторое время 
 * loadTimeRestart начать игру заново, после гибели персонажа
 *
 */

public class Counter {
	private long currentTime = System.currentTimeMillis();
	private String direction = "character/parameters";
	private boolean flag = false;
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public boolean isFlag() {
		return flag;
	}
	private int loadTimeRestart() throws FileNotFoundException {
		String loadTimeRestart = "";
		String addresFile = "character/parameters/timeRestart.txt";
		Scanner in = new Scanner(new File(addresFile));
		while(in.hasNext()){
			loadTimeRestart += in.nextLine();
		}
		in.close();
		int timeRestart = Integer.parseInt(loadTimeRestart);
		return timeRestart;
	}
	public long loadTimeDeath() throws FileNotFoundException {
		String loadTimeDeath = "";
		String addresFile = "character/parameters/timeDeath.txt";
		Scanner in = new Scanner(new File(addresFile));
		while(in.hasNext()){
			loadTimeDeath += in.nextLine();
		}
		in.close();
		long timeDeath = Long.parseLong(loadTimeDeath);
		timeDeath = timeDeath / 1000;
		return timeDeath;
	}
	private void delete() {
		for(File file : new File(direction).listFiles()) {
			if(file.isFile()) {
				file.delete();
			}
		}
	}
	public void count() throws FileNotFoundException {
		currentTime = currentTime / 1000;
		if(currentTime - loadTimeDeath() > loadTimeRestart()) {
			flag = true;
			delete();
		}
	}
	public long sub() throws FileNotFoundException {
		currentTime = currentTime / 1000;
		return currentTime - loadTimeDeath();
	}
}
