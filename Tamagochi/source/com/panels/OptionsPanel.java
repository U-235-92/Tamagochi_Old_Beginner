package com.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.characters.Character;
import com.main.Main;
/***
 * 
 * @author ������� ������
 *
 * ������, �� ������� ������������ ������ ����������
 * ����� ������� Exit ����������� ������� ��������� ���������
 */
@SuppressWarnings("serial")
public class OptionsPanel extends JPanel implements KeyListener{
	private final static int WIDTH = Main.WIDTH;
	private final static int HEIGHT = 50;
	private final static int FPS = 60;
	//current choise options
	private int currentChoice = -1;
	//options character
	private String[] options = {"Eat", "Sleep", "Toilet", "Exit"};
	//fonts
	private Font optionsFont = new Font("Century Gothic", Font.BOLD, 30);
	//color
	private Color lettersColor = new Color(210, 120, 95);
	//Timer update panel
	Timer timer;
	private static Character character = new Character();

	public OptionsPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		setBackground(Color.BLACK);
		this.addKeyListener(this);
		if(timer == null) {
			timer = new Timer(FPS, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					OptionsPanel.this.repaint();
				}
			});
			timer.start();
		}
	}
	private void paintOptionsCharacter(Graphics g) {
		g.setColor(lettersColor);
		g.setFont(optionsFont);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(lettersColor);
			}
			switch(i) {
			case 0:
				g.drawString(options[i], WIDTH / 7 + i * 120, HEIGHT - 15);
				break;
			case 1:
				g.drawString(options[i], WIDTH / 7 + i * 105, HEIGHT - 15);
				break;
			case 2:
				g.drawString(options[i], WIDTH / 7 + i * 125, HEIGHT - 15);
				break;
			case 3:
				g.drawString(options[i], WIDTH / 7 + i * 130, HEIGHT - 15);
				break;
			}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintOptionsCharacter(g);
	}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) { //0 - Eat; 1 - Sleep; 2 - Toilet; 3 - Exit
			currentChoice++;
			if(currentChoice > options.length - 1) {
				currentChoice = 0;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			currentChoice--;
			if(currentChoice < 0) {
				currentChoice = options.length - 1;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			select();
		}
	}
	public void keyReleased(KeyEvent e) {}	
	private void select() {
		if(currentChoice == 0) { 
			character.eat();
		}
		if(currentChoice == 1) { 
			character.fatigue();
		}
		if(currentChoice == 2) { 
			character.toilet();
		}
		if(currentChoice == 3) { //EXIT
			try {
				writeInfo();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	private void writeInfo() throws IOException{
		String addresFolder = "character/parameters";
		File folder = new File(addresFolder);
		if(folder.exists() == false){
			folder.mkdirs();
		}
		writeCodeExitInfo();
		writeHungryInfo();
		writeToiletInfo();
		writeFatigueInfo();
		writeXPosInfo();
		writeYPosInfo();
		writeTimeExit();
		writeIdCharacter();
		writeCheckStart();
		writeAge();
	}
	private void writeCheckStart() throws IOException {
		String addresFile = "character/parameters/flag_start.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int flag = Main.setStartFlag(Main.getStartFlag() + 1); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(flag);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int flag = Main.setStartFlag(Main.getStartFlag() + 1); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(flag);
			out.close();
		}
	}
	private void writeCodeExitInfo() throws IOException {
		Main.setCode_exit(0);
		String addresFile = "character/parameters/codeExit.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int codeExit = Main.getCode_exit();
			PrintWriter out = new PrintWriter(addresFile);
			out.print(codeExit);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int codeExit = Main.getCode_exit(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(codeExit);
			out.close();
		}
	}
	private void writeHungryInfo() throws IOException {
		String addresFile = "character/parameters/currentHungry.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int countHungry = Character.getCurrentHungry(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(countHungry);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int countHungry = Character.getCurrentHungry(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(countHungry);
			out.close();
		}
	}
	private void writeToiletInfo() throws IOException {
		String addresFile = "character/parameters/currentToilet.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int countToilet = Character.getCurrentToilet(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(countToilet);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int countToilet = Character.getCurrentToilet(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(countToilet);
			out.close();
		}
	}
	private void writeFatigueInfo() throws IOException {
		String addresFile = "character/parameters/currentFatigue.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int countFatigue = Character.getCurrentFatigue(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(countFatigue);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int countFatigue = Character.getCurrentFatigue(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(countFatigue);
			out.close();
		}
	}
	private void writeXPosInfo() throws IOException {
		String addresFile = "character/parameters/currentXPos.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int x_pos_character = GamePanel.getX_character(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(x_pos_character);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int x_pos_character = GamePanel.getX_character(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(x_pos_character);
			out.close();
		}
	}
	private void writeYPosInfo() throws IOException {
		String addresFile = "character/parameters/currentYPos.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int y_pos_character = GamePanel.getY_character(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(y_pos_character);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int y_pos_character = GamePanel.getY_character(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(y_pos_character);
			out.close();
		}
	}
	private void writeTimeExit() throws IOException {
		String addresFile = "character/parameters/timeExit.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			long timeExit = System.currentTimeMillis(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(timeExit);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			long timeExit = System.currentTimeMillis(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(timeExit);
			out.close();
		}
	}
	private void writeIdCharacter() throws IOException {
		String addresFile = "character/parameters/idCharacter.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int idCharacter = Main.getIdCharacter(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(idCharacter);
			out.close();
		}
	}
	private void writeAge() throws IOException {
		String addresFile = "character/parameters/age.txt";
		File file = new File(addresFile);
		if(file.exists() == false) { //���� ���� �� ���������� - ������� ���
			file.createNewFile();
			int age = character.getAge(); //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(age);
			out.close();
		}
		else { //����� �������������� ������ � ������� ����
			file.createNewFile();
			//������� ���� �� ������ ������
			String del = ""; //���� � ������
			PrintWriter out = new PrintWriter(addresFile);
			out.print(del);
			out.close();
			//���������� ����� ������
			int age = character.getAge(); //���� � ������
			out = new PrintWriter(addresFile);
			out.print(age);
			out.close();
		}
	}
}
