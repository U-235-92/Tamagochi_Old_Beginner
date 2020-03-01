package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

import com.levels.Level;
import com.panels.MenuPanel;
/***
 * 
 * @author ������� ������
 *
 */
public class Main {
	public final static int WIDTH = 640;
	public final static int HEIGHT = 480;
	public static JFrame frame;
	private static int code_exit = -1; // 1 - ���� � ����; 0 - ���� �� �������
	private static long time_death;
	private static int timeRestart = 600; // � ��������, ����� ������������ ����, ����� ������
	private static int idCharacter; //������������ ���������
	private static int startFlag = 0; //���� 0 - ����� ����, ���� 1 - ��������� �������� ������, ��������� �� ���������� � ������ ���, ����� ������ �� ���� � ���������� ������
	//���� 0, �� ����� ������������� ����� �����������, ���� 1 - ������������� ������
	
	public static long timeStart;
	
	public static int getStartFlag() {
		return startFlag;
	}
	public static int setStartFlag(int startFlag) {
		return Main.startFlag = startFlag;
	}
	public static int getCode_exit() {
		return code_exit;
	}
	public static void setCode_exit(int code_exit) {
		Main.code_exit = code_exit;
	}
	public static long getTime_death() {
		return time_death;
	}
	public static void setTime_death(long time_death) {
		Main.time_death = time_death;
	}
	public static int getTimeRestart() {
		return timeRestart;
	}
	public static int getIdCharacter() {
		return idCharacter;
	}
	public static void setIdCharacter(int idCharacter) {
		Main.idCharacter = idCharacter;
	}
	private static void build() {
		String title = "Tamagochi";
		//Frame
		frame = new JFrame(title);
		//Settings field
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//MenuPanel
		MenuPanel panelMenu = new MenuPanel();
		frame.add(panelMenu);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static int loadCheckStart() {
		String addresFile = "character/parameters/flag_start.txt";
		File file = new File(addresFile);
		int flagStart = 0;
		if(file.exists()) { // ���� ���� ����, ������ ������
			String flag = "";
			Scanner in = null;
			try {
				in = new Scanner(new File(addresFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while(in.hasNext()){
				flag += in.nextLine();
			}
			in.close();
			flagStart = Integer.parseInt(flag);
			
		}
		else {
			flagStart = Main.getStartFlag();
		}
		return flagStart;
	}
	public static void main(String[] args) {
		if(loadCheckStart() == 0) {
			build();
		}
		else {
			Level level = new Level();
			try {
				level.createLevel();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
