package com.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.levels.Level;
import com.main.Main;
/***
 * 
 * @author Николай Коптев
 *
 * 
 */
public class AgePanel extends JPanel{
	private final static int WIDTH = Main.WIDTH - 520;
	private final static int HEIGHT = 70;
	private final static int FPS = 5000;
	private int age = 0;
	private Timer timer;
	//font
	private Font parametersFont = new Font("Century Gothic", Font.BOLD, 20);
	public AgePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		if(timer == null) {
			timer = new Timer(FPS, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AgePanel.this.repaint();
				}
			});
			timer.start();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		age = Level.doAge();
		g.setColor(Color.GREEN);
		g.setFont(parametersFont);
		g.drawString("Age: " + age, WIDTH / 30, HEIGHT - 45);
	}
}
