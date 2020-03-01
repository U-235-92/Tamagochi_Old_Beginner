package com.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.main.Counter;
import com.main.Main;
/***
 * 
 * @author Николай Коптев
 *
 * Отображается после гибели персонажа
 */
@SuppressWarnings("serial")
public class DeathPanel extends JPanel{
	private final static int WIDTH = Main.WIDTH;
	private final static int HEIGHT = Main.HEIGHT;
	private final static int WIDTH_RIP = 128;
	private final static int HEIGHT_RIP = 128;
	private final static int FPS = 60;
	//coords
	private int x_pos_font = 0;
	private int y_pos_font = 0;
	private int x_pos_rip =  WIDTH / 2 - WIDTH_RIP / 2;
	private int y_pos_rip = HEIGHT / 3 + HEIGHT_RIP + 50;
	//images
	private BufferedImage imageFont;
	private BufferedImage imageRIP;
	//img adress
	private String fontAdress = "img/fonts/deathFont.png";
	private String ripAdress = "img/characters/characterRIP.png";
	//messages
	String msg = "[ Your character is died :/ After while you can start ]";
	//Timer update panel
	Timer timer;
	//fonts
	private Font msgFont = new Font("Century Gothic", Font.BOLD, 20);
	//color
	private Color lettersColor = new Color(237, 170, 102);
	long current = System.currentTimeMillis();
	long c2 = System.currentTimeMillis() + Main.getTimeRestart();
	public DeathPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		try {
			imageFont = ImageIO.read(new File(fontAdress));
			imageRIP = ImageIO.read(new File(ripAdress));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(timer == null) {
			timer = new Timer(FPS, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DeathPanel.this.repaint();
				}
			});
			timer.start();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Paint Font
		g.drawImage(imageFont, x_pos_font, y_pos_font, WIDTH, HEIGHT, null);
		g.drawImage(imageRIP, x_pos_rip, y_pos_rip, WIDTH_RIP, HEIGHT_RIP, null);
		g.setColor(lettersColor);
		g.setFont(msgFont);
		//message
		g.drawString(msg, WIDTH / 8 - 10 , HEIGHT / 8 + 40);
	}
}
