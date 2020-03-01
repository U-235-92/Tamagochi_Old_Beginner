package com.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.characters.Character;
import com.main.Main;
/***
 * 
 * @author Николай Коптев
 * На данной панели размещается игровой фон и персонаж 
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
	private final static int WIDTH = Main.WIDTH;
	private final static int HEIGHT = Main.HEIGHT;
	private final static int FPS = 300;
	//positions font
	private int x_pos_font = 0;
	private int y_pos_font = 0;
	
	private static int x_character;
	private static int y_character;
	//images
	private BufferedImage imageFont;
	//img adress
	private String fontAdress;
	//Character
	private Character character;
	//Timer update panel
	Timer timer;
	private File file;
	
	public GamePanel() throws FileNotFoundException {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		//переписывание времени смерти, если персонаж умер в игре
		String addresFile = "character/parameters/timeDeath.txt";
		file = new File(addresFile);
		//проверка смерти персонажа по значению Main.codeExit
		addresFile = "character/parameters/codeExit.txt";
		file = new File(addresFile);
		if(file.exists()) { 
			String readCodeExit = "";
			Scanner in = new Scanner(new File(addresFile));
			while(in.hasNext()){
				readCodeExit += in.nextLine();
			}
			in.close();
			Main.setCode_exit(Integer.parseInt(readCodeExit));
			character.setCurrentHungry(0);
		}
		//размещение персонажа
		if(character == null) {
			//размещение персонажа на поле
			addresFile = "character/parameters/currentXPos.txt";
			file = new File(addresFile);
			if(file.exists() == false) { //если файл не найден - игра только запущена. создать персонажа
				character = new Character(Main.WIDTH + 30, 320, Character.WIDTH, Character.HEIGHT); 
			}
			else { //иначе читаем записанные данные персонажа
				String read_X_Pos = "";
				Scanner in = new Scanner(new File(addresFile));
				while(in.hasNext()){
					read_X_Pos += in.nextLine();
				}
				in.close();
				int x_pos = Integer.parseInt(read_X_Pos);
				character = new Character(x_pos, 320, Character.WIDTH, Character.HEIGHT); 
				addresFile = "character/parameters/timeExit.txt";
				file = new File(addresFile);
				if(file.exists()) { 
					checkReloadTime();
				}
			}
		}
		try {
			if(getID() == 0) {
				fontAdress = "img/fonts/catFont.png";
			}
			if(getID() == 1) {
				fontAdress = "img/fonts/knightFont.png";
			}
			imageFont = ImageIO.read(new File(fontAdress));
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(timer == null) {
			timer = new Timer(FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GamePanel.this.repaint();
			}
		});
		timer.start();
		}
	}
	public static int getX_character() {
		return x_character;
	}
	public static int getY_character() {
		return y_character;
	}
	private void paintFont(Graphics g) {
		//Paint Font
		g.drawImage(imageFont, x_pos_font - WIDTH, y_pos_font, WIDTH, HEIGHT, null);
		g.drawImage(imageFont, x_pos_font, y_pos_font, WIDTH, HEIGHT, null);
		//Move Font
		x_pos_font += 3;
		if(x_pos_font > WIDTH) {
			x_pos_font = 0;
		}		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintFont(g);
		character.paintCharacter(g);
		character.move();
		x_character = character.getX_pos(); 
		y_character = character.getY_pos(); 
	}
	private int getID() throws FileNotFoundException {
		int id = 0;
		String addresFile = "character/parameters/idCharacter.txt";
		file = new File(addresFile);
		if(file.exists()) { // если файл есть, читаем данные
			String idCharacter = "";
			Scanner in = new Scanner(new File(addresFile));
			while(in.hasNext()){
				idCharacter += in.nextLine();
			}
			in.close();
			id = Integer.parseInt(idCharacter);
			
		}
		else {
			id = Main.getIdCharacter();
		}
		return id;
	}
	private void loadCurrentHungry() throws FileNotFoundException {
		//загрузка текущего значения голода
		String readCurrentHungry = "";
		String addresFile = "character/parameters/currentHungry.txt";
		Scanner in = new Scanner(new File(addresFile));
		while(in.hasNext()){
			readCurrentHungry += in.nextLine();
		}
		in.close();
		Character.setCurrentHungry(Integer.parseInt(readCurrentHungry));
	}
	private void writeTimeDeath() throws FileNotFoundException {
		String addresFile = "character/parameters/timeDeath.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //если файл не существует - создаем его
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Main.setTime_death(System.currentTimeMillis());
			PrintWriter out = new PrintWriter(addresFile);
			out.print(Main.getTime_death());
			out.close();
		}
		else { //иначе перезаписываем данные в готовый файл
			String readTimeDeath = "";
			Scanner in = new Scanner(new File(addresFile));
			while(in.hasNext()){
				readTimeDeath += in.nextLine();
			}
			in.close();
			Main.setTime_death(Long.parseLong(readTimeDeath));
		}
	}
	private void checkReloadTime() throws FileNotFoundException{
		String timeExit = "";
		String addresFile = "character/parameters/timeExit.txt";
		Scanner in = new Scanner(new File(addresFile));
		while(in.hasNext()){
			timeExit += in.nextLine();
		}
		in.close();
		long parseTimeExit = Long.parseLong(timeExit);
		parseTimeExit = parseTimeExit / 1000;
		long currentTime = System.currentTimeMillis() / 1000; //в секундах
		//загрузка текущего значения голода
		loadCurrentHungry();
		// время "простоя игры" превысило параметр голода - персонаж умирает, запись времени смерти
		if(currentTime - parseTimeExit > Character.getCurrentHungry()) { 
			character.setCurrentHungry(0);
			writeTimeDeath();
		}
	}
}
