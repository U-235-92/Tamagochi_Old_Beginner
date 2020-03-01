package com.characters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.main.Main;
import com.meal.Fish;
import com.meal.Meat;
/***
 * 
 * @author Николай Коптев
 *
 */
public class Character {
	//coordinates and sizes
	protected int x_pos;
	protected int y_pos;
	protected int width_character;
	protected int height_character;
	private int dx = 10;
	//check left or right side panel
	protected boolean checkLeft = false;
	protected boolean checkRight = true;
	//max count
	public static final int MAX_COUNT_HUNGRY = 120; //sec
	public static final int MAX_COUNT_FATIGUE = 180; //sec
	public static final int MAX_COUNT_TOILET = 300; //sec
	public static final int HEIGHT = 128;
	public static final int WIDTH = 128;
	protected static int currentHungry = MAX_COUNT_HUNGRY;
	protected static int currentFatigue = MAX_COUNT_FATIGUE;
	protected static int currentToilet = 0;
	protected static int age = -1;
	//eat for character
	private static Fish fish = new Fish();
	private static Meat meat = new Meat();
	//count frames animation
	private int countAnim;
	//List frames animation
	private ArrayList<BufferedImage> listAnimMoveToLeft = new ArrayList<BufferedImage>(); //move to left
	private ArrayList<BufferedImage> listAnimMoveToRight = new ArrayList<BufferedImage>(); //move to right
	
	public Character() {
		super();
	}
	public Character(int x, int y, int width, int height) {
		try {
			loadAge();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.x_pos = x;
		this.y_pos = y;
		this.width_character = width;
		this.height_character = height;
		countAnim = 0;
		try {
			if(getID() == 0) {
				//Images move
				listAnimMoveToLeft.add(ImageIO.read(new File("img/characters/cat/spriteCatTL1.png")));
				listAnimMoveToLeft.add(ImageIO.read(new File("img/characters/cat/spriteCatTL2.png")));
				
				listAnimMoveToRight.add(ImageIO.read(new File("img/characters/cat/spriteCatTR1.png")));
				listAnimMoveToRight.add(ImageIO.read(new File("img/characters/cat/spriteCatTR2.png")));
			}
			if(getID() == 1){
				//Images move
				
				listAnimMoveToLeft.add(ImageIO.read(new File("img/characters/knight/spriteKnightL2.png")));
				listAnimMoveToLeft.add(ImageIO.read(new File("img/characters/knight/spriteKnightL3.png")));
				listAnimMoveToLeft.add(ImageIO.read(new File("img/characters/knight/spriteKnightL4.png")));
				listAnimMoveToLeft.add(ImageIO.read(new File("img/characters/knight/spriteKnightL5.png")));
				listAnimMoveToLeft.add(ImageIO.read(new File("img/characters/knight/spriteKnightL6.png")));
				
				
				
				listAnimMoveToRight.add(ImageIO.read(new File("img/characters/knight/spriteKnightR2.png")));
				listAnimMoveToRight.add(ImageIO.read(new File("img/characters/knight/spriteKnightR3.png")));
				listAnimMoveToRight.add(ImageIO.read(new File("img/characters/knight/spriteKnightR4.png")));
				listAnimMoveToRight.add(ImageIO.read(new File("img/characters/knight/spriteKnightR5.png")));
				listAnimMoveToRight.add(ImageIO.read(new File("img/characters/knight/spriteKnightR6.png")));
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static int getAge() {
		return age;
	}
	public static int setAge(int age) {
		return Character.age = age;
	}
	public int getX_pos() {
		return x_pos;
	}
	public void setX_pos(int x_pos) {
		this.x_pos = x_pos;
	}
	public int getY_pos() {
		return y_pos;
	}
	public void setY_pos(int y_pos) {
		this.y_pos = y_pos;
	}
	public static int getCurrentHungry() {
		return currentHungry;
	}
	public static int setCurrentHungry(int currentHungry) {
		return Character.currentHungry = currentHungry;
	}
	public static int getCurrentFatigue() {
		return currentFatigue;
	}
	public static int setCurrentFatigue(int currentFatigue) {
		return Character.currentFatigue = currentFatigue;
	}
	public static int getCurrentToilet() {
		return currentToilet;
	}
	public static int setCurrentToilet(int currentToilet) {
		return Character.currentToilet = currentToilet;
	}
	public int getID() throws FileNotFoundException {
		int id = 0;
		String addresFile = "character/parameters/idCharacter.txt";
		File file = new File(addresFile);
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
	public void move() {
		if(checkLeft == false && checkRight == true) {
			x_pos = x_pos - dx;
			if(x_pos < 12) {
				checkLeft = true;
				checkRight = false;
			}
		}
		else {
				x_pos = x_pos + dx;
				if(x_pos > Main.WIDTH - width_character) {
				checkLeft = false;
				checkRight = true;
			}
		}
	}
	public void paintCharacter(Graphics g) {
		if(checkRight == true && checkLeft == false) {
			g.drawImage(listAnimMoveToLeft.get(countAnim), x_pos, y_pos, width_character, height_character, null);
			countAnim++;
			if(countAnim > listAnimMoveToLeft.size() - 1) {
				countAnim = 0;
			}
		}
		else {
			g.drawImage(listAnimMoveToRight.get(countAnim), x_pos, y_pos, width_character, height_character, null);
			countAnim++;
			if(countAnim > listAnimMoveToRight.size() - 1) {
				countAnim = 0;
			}
		}
	}
	public void eat() {
		try {
			if(getID() == 0) {
				currentHungry += fish.rndIncrement(); 
				if(currentHungry - MAX_COUNT_HUNGRY >= 0) {
					currentHungry = MAX_COUNT_HUNGRY + 1;
				}
				else {
					currentHungry = currentHungry + 1;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if(getID() == 1) {
				currentHungry += meat.rndIncrement(); 
				if(currentHungry - MAX_COUNT_HUNGRY >= 0) {
					currentHungry = MAX_COUNT_HUNGRY + 1;
				}
				else {
					currentHungry = currentHungry + 1;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void toilet() {
		currentToilet = 0;
	}
	public void fatigue() {
		currentFatigue = MAX_COUNT_FATIGUE;
		currentFatigue += 1;
	}
	private void loadAge() throws FileNotFoundException {
		int age = 0;
		String addresFile = "character/parameters/age.txt";
		File file = new File(addresFile);
		if(file.exists()) { // если файл есть, читаем данные
			String ageCharacter = "";
			Scanner in = new Scanner(new File(addresFile));
			while(in.hasNext()){
				ageCharacter += in.nextLine();
			}
			in.close();
			age = Integer.parseInt(ageCharacter);
			Character.setAge(age - 2);
			
		}
		else {
			age = Character.getAge();
		}
	}
}
