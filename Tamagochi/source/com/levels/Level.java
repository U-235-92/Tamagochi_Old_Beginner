package com.levels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.characters.Character;
import com.main.Main;
import com.panels.AgePanel;
import com.panels.GamePanel;
import com.panels.OptionsPanel;
import com.panels.ParametersPanel;
/***
 * 
 * @author Николай Коптев
 *
 */
public class Level {
	public static JFrame frame;
	//character
	private static Character character = new Character();
	
	public void createLevel() throws FileNotFoundException {
		//Frame
		frame = new JFrame("Tamagochi");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//Panels
		JPanel panelBackgroundParameters = new JPanel();
		ParametersPanel panelParameters = new ParametersPanel();
		AgePanel  panelAge = new AgePanel();
		GamePanel panelGame = new GamePanel();
		OptionsPanel panelO = new OptionsPanel();
		
		//Setting Panels
		panelBackgroundParameters.setBackground(Color.BLACK);
		panelBackgroundParameters.setPreferredSize(new Dimension(Main.WIDTH, 71));
		panelBackgroundParameters.setLayout(new GridBagLayout()); 
		
		//Add
		frame.add(panelBackgroundParameters, BorderLayout.NORTH);
		panelBackgroundParameters.add(panelParameters);
		panelBackgroundParameters.add(panelAge);
		frame.add(panelGame, BorderLayout.CENTER);
		frame.add(panelO, BorderLayout.SOUTH);
		
		//pack frame
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		if(Main.frame != null) {
			Main.frame.setVisible(false);
		}
	}
	public static int doHungry() { 
		return character.setCurrentHungry(character.getCurrentHungry() - 1);
	}
	public static int doFatigue() { 
		return character.setCurrentFatigue(character.getCurrentFatigue() - 1);
	}
	public static int doToilet() { 
		return character.setCurrentToilet(character.getCurrentToilet() + 1);
	}
	public static int doAge() {
		return character.setAge(character.getAge() + 1);
	}
}	
