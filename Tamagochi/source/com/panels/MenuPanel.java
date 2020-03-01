package com.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.levels.Level;
import com.main.Main;
/***
 * 
 * @author Николай Коптев
 *
 */

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements KeyListener{
	private final static int WIDTH = Main.WIDTH;
	private final static int HEIGHT = Main.HEIGHT;
	private final static int FPS = 60;
	//positions font
	private int x_pos_font = 0;
	private int y_pos_font = 0;
	//index point menu
	private int currentChoice = -1;
	//menu
	private String[] options = {"Cat", "Knight", "Start", "Exit"};
	//check selected character
	private boolean characterSelect = false;
	//fonts
	private Font titleFont = new Font("Century Gothic", Font.BOLD, 64);
	private Font infoFont = new Font("Century Gothic", Font.BOLD, 24);
	private Font optionsFont = new Font("Century Gothic", Font.BOLD, 50);
	//color
	private Color lettersColor = new Color(210, 120, 95);
	//images
	private BufferedImage imageFont;
	//img adress
	private String fontAdress = "img/fonts/menuFont.png";
	
	private Timer timer;
	
	public MenuPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		this.addKeyListener(this);
		try {
			imageFont = ImageIO.read(new File(fontAdress));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(timer == null) {
			timer = new Timer(FPS, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuPanel.this.repaint();
				}
			});
			timer.start();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Paint Font
		g.drawImage(imageFont, x_pos_font - WIDTH, y_pos_font, WIDTH, HEIGHT, null);
		g.drawImage(imageFont, x_pos_font, y_pos_font, WIDTH, HEIGHT, null);
		//Move Font
		x_pos_font += 1;
		if(x_pos_font > WIDTH) {
			x_pos_font = 0;
		}		
		//Title
		g.setColor(lettersColor);
		g.setFont(titleFont);
		g.drawString("TAMAGOCHI", WIDTH / 5 - 10, 70);
		//Info
		if(Main.loadCheckStart() == 0) {
			g.setColor(Color.RED);
			g.setFont(infoFont);
			g.drawString("Choice a character and press 'Enter'", WIDTH / 6, 100);
		}
		//Options
		g.setColor(lettersColor);
		g.setFont(optionsFont);
		if(Main.loadCheckStart() == 0) {
			for(int i = 0; i < options.length; i++) {
				if(i == currentChoice) {
					g.setColor(Color.RED);
				}
				else {
					g.setColor(lettersColor);
				}
				switch(i) {
				case 0:
					if(characterSelect == true) {
						g.setColor(Color.GRAY);
					}
					else {
						g.setColor(lettersColor);
					}
					if(i == currentChoice) {
						g.setColor(Color.RED);
					}
					g.drawString(options[i], WIDTH / 2 - 60, 200 + i * 80); //CAT
					break;
				case 1:
					if(characterSelect == true) {
						g.setColor(Color.GRAY);
					}
					else {
						g.setColor(lettersColor);
					}
					if(i == currentChoice) {
						g.setColor(Color.RED);
					}
					g.drawString(options[i], WIDTH / 2 - 90, 200 + i * 80); //KNIGHT
					break;
				case 2:
					if(characterSelect == true) {
						g.setColor(lettersColor);
						if(i == currentChoice) {
							g.setColor(Color.RED);
						}
					}
					else {
						g.setColor(Color.GRAY);
					}
					if(i == currentChoice) {
						g.setColor(Color.RED);
					}
					g.drawString(options[i], WIDTH / 2 - 65, 200 + i * 80); //START
					break;
				case 3:
					g.drawString(options[i], WIDTH / 2 - 52, 200 + i * 80); //EXIT
					break;
				}
			}
		}
	}
	//enter pressed
	private void select() throws FileNotFoundException {
		if(Main.loadCheckStart() == 0) {
			if(characterSelect == false) {//Character not selected
				if(currentChoice == 0) { // CAT
					Main.setIdCharacter(currentChoice);
					characterSelect = true;
					currentChoice = 2;
				}
				if(currentChoice == 1) { // KNIGHT
					Main.setIdCharacter(currentChoice);
					characterSelect = true;
					currentChoice = 2;
				}
			}
			else { //Character selected
				if(currentChoice == 2) { //START
					timer.stop();
					Level level = new Level();
					level.createLevel();
				}
			}
			if(currentChoice == 3) { //EXIT
				System.exit(0);
			}
		}
	}
	private void keyPressCheck(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) { //0 = CAT; 1 = KNIGHT; 2 = START; 3 = EXIT
			if(characterSelect == true) {
				currentChoice--;
				if(currentChoice < 2) {
					currentChoice = options.length - 1;
				}
			}
			else {
				currentChoice--;
				if(currentChoice == 2) {
					currentChoice--;
				}
				if(currentChoice < 0) {
					currentChoice = options.length - 1;
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(characterSelect == true) {
				currentChoice++;
				if(currentChoice > options.length - 1) {
					currentChoice = 2;
				}
			}
			else {
				currentChoice++;
				if(currentChoice == 2) {
					currentChoice++;
				}
				if(currentChoice > options.length - 1) {
					currentChoice = 0;
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				select();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if(Main.loadCheckStart() == 0) {
			keyPressCheck(e);
		}
	}
	public void keyReleased(KeyEvent e) {}
}
