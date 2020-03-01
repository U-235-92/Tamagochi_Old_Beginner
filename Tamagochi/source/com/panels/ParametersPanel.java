package com.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.characters.Character;
import com.levels.Level;
import com.main.Counter;
import com.main.Main;
/***
 * 
 * @author Николай Коптев
 * Предназначена для отображения параметров персонажа
 */
@SuppressWarnings("serial")
public class ParametersPanel extends JPanel{
	private final static int WIDTH = Main.WIDTH - 120;
	private final static int HEIGHT = 70;
	private final static int FPS = 1000;
	private final static int MOOD_WIDTH = 32;
	private final static int MOOD_HEIGHT = 32;
	private final static int MAX_PERCENT = 100;
	//parameters Character in %
	private int percentageHungry;
	private int percentageFatigue;
	private int percentageToilet;
	//Timer update panel
	private Timer timer;
	private static int count = 8;
	//parameters character
	private String[] parameters = {"Satiety: ", "Vivacity: ", "Want to toilet: ", "Mood: "};
	//fonts
	private Font parametersFont = new Font("Century Gothic", Font.BOLD, 20);
	private ArrayList<BufferedImage> listMoods = new ArrayList<BufferedImage>();
	public ParametersPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		try {
			listMoods.add(ImageIO.read(new File("img/mood/goodMood.png")));
			listMoods.add(ImageIO.read(new File("img/mood/neutralMood.png")));
			listMoods.add(ImageIO.read(new File("img/mood/sadMood.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(timer == null) {
			timer = new Timer(FPS, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ParametersPanel.this.repaint();
				}
			});
			timer.start();
		}
	}
	public static int getCount() {
		return count;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintParametersCharacter(g);
	}
	private void paintParametersCharacter(Graphics g) {
		percentageHungry = (int) (Level.doHungry() * 100 / Character.MAX_COUNT_HUNGRY);
		percentageFatigue = (int) (Level.doFatigue() * 100 / Character.MAX_COUNT_FATIGUE);
		percentageToilet = (int) (Level.doToilet() * 100 / Character.MAX_COUNT_TOILET);
		g.setColor(Color.GREEN);
		g.setFont(parametersFont);
		for(int i = 0; i < parameters.length; i++) {
			switch(i) {
			case 0:
				g.drawString(parameters[i] + percentageHungry + " % |", WIDTH / 30, HEIGHT - 45);//Hungry 
				break;
			case 1:
				g.drawString(parameters[i] + percentageFatigue + " % |", WIDTH / 30 + i * 150, HEIGHT - 45); //Fatigue
				break;
			case 2:
				g.drawString(parameters[i] + percentageToilet + " % |", WIDTH / 20 + i * 150, HEIGHT - 45); //Toilet
				break;
			case 3:
				g.drawString(parameters[i], WIDTH / 8 , HEIGHT - 15); //Mood
				break;
			}
		}
		if(percentageHungry > MAX_PERCENT / 2 && percentageFatigue > MAX_PERCENT / 2 && percentageToilet < MAX_PERCENT / 2) {	
			
			g.drawImage(listMoods.get(0), WIDTH / 8 + 70, HEIGHT - 37, MOOD_WIDTH, MOOD_HEIGHT, null);
			g.drawString("I'm feel good :)", WIDTH / 8 + 110, HEIGHT - 15);
		}
		if(percentageHungry <= MAX_PERCENT / 2 && percentageHungry > MAX_PERCENT / 4
				|| percentageFatigue <= MAX_PERCENT / 2 && percentageFatigue > MAX_PERCENT / 4
				|| percentageToilet >= MAX_PERCENT / 2 && percentageToilet < MAX_PERCENT * 3 / 4) { 
			
			g.drawImage(listMoods.get(1), WIDTH / 8 + 70, HEIGHT - 37, MOOD_WIDTH, MOOD_HEIGHT, null);
			g.setColor(Color.YELLOW);
			g.drawString("I need your attention!", WIDTH / 8 + 110, HEIGHT - 15);
		}
		if(percentageHungry <= MAX_PERCENT / 4 || percentageFatigue <= MAX_PERCENT / 4 || percentageToilet >= MAX_PERCENT * 3 / 4) {
			g.drawImage(listMoods.get(2), WIDTH / 8 + 70, HEIGHT - 37, MOOD_WIDTH, MOOD_HEIGHT, null);
			g.setColor(Color.RED);
			g.drawString("I'm feel bad, you don't love me? :(", WIDTH / 8 + 110, HEIGHT - 15);
		}
		if(percentageHungry < 0 || percentageFatigue < 0 || percentageToilet > Character.MAX_COUNT_TOILET) {
			Main.setCode_exit(1);
			Level.frame.setVisible(false);
			JFrame frame = new JFrame("Tamagochi");
			DeathPanel panel = new DeathPanel();
			frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			try {
				writeInfo();
				Counter c = new Counter();
				c.count();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void writeInfo() throws IOException{
		String addresFolder = "character/parameters";
		File folder = new File(addresFolder);
		if(folder.exists() == false){
			folder.mkdirs();
		}
		writeTimeDeath();
		writeCodeExit();
		writeTimeRestart();
		writeIdCharacter();
		writeCheckStart();
	}
	private void writeCheckStart() throws IOException {
		String addresFile = "character/parameters/flag_start.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //если файл не существует - создаем его
			file.createNewFile();
			int flag = Main.setStartFlag(Main.getStartFlag() + 1); //инфа к записи
			PrintWriter out = new PrintWriter(addresFile);
			out.print(flag);
			out.close();
		}
		else { //иначе перезаписываем данные в готовый файл
			file.createNewFile();
			//очищаем файл от старой записи
			String del = ""; //инфа к записи
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//записываем новые данные
			int flag = Main.setStartFlag(Main.getStartFlag() + 1); //инфа к записи
			out = new PrintWriter(addresFile);
			out.print(flag);
			out.close();
		}
	}
	private void writeTimeDeath() throws IOException {
		String addresFile = "character/parameters/timeDeath.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //проверяем, если файл не существует, то создаем его{
			file.createNewFile();
			Main.setTime_death(System.currentTimeMillis());
			PrintWriter out = new PrintWriter(addresFile);
			out.print(Main.getTime_death());
			out.close();
		}
		else { //иначе перезаписываем данные 
			String readTimeDeath = "";
			Scanner in = new Scanner(new File(addresFile));
			while(in.hasNext()){
				readTimeDeath += in.nextLine();
			}
			in.close();
			Main.setTime_death(Long.parseLong(readTimeDeath));
		}
	}
	private void writeCodeExit() throws IOException {
		String addresFile = "character/parameters/codeExit.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //если файл не существует - создаем его
			file.createNewFile();
			int codeExit = Main.getCode_exit();
			PrintWriter out = new PrintWriter(addresFile);
			out.print(codeExit);
			out.close();
		}
		else { //иначе перезаписываем данные в готовый файл
			file.createNewFile();
			//очищаем файл от старой записи
			String del = ""; //инфа к записи
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//записываем новые данные
			int codeExit = Main.getCode_exit();
			out = new PrintWriter(addresFile);
			out.print(codeExit);
			out.close();
		}
	}
	private void writeTimeRestart() throws IOException {
		String addresFile = "character/parameters/timeRestart.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //если файл не существует - создаем его
			file.createNewFile();
			int timeRestart = Main.getTimeRestart();
			PrintWriter out = new PrintWriter(addresFile);
			out.print(timeRestart);
			out.close();
		}
		else { //иначе перезаписываем данные в готовый файл
			file.createNewFile();
			//очищаем файл от старой записи
			String del = ""; //инфа к записи
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//записываем новые данные
			int timeRestart = Main.getTimeRestart();
			out = new PrintWriter(addresFile);
			out.print(timeRestart);
			out.close();
		}
	}
	private void writeIdCharacter() throws IOException {
		String addresFile = "character/parameters/idCharacter.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //если файл не существует - создаем его
			file.createNewFile();
			int idCharacter = Main.getIdCharacter(); //инфа к записи
			PrintWriter out = new PrintWriter(addresFile);
			out.print(idCharacter);
			out.close();
		}
		else { //иначе перезаписываем данные в готовый файл
			file.createNewFile();
			//очищаем файл от старой записи
			String del = ""; //инфа к записи
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//записываем новые данные
			int idCharacter = Main.getIdCharacter(); //инфа к записи
			out = new PrintWriter(addresFile);
			out.print(idCharacter);
			out.close();
		}
	}
}
