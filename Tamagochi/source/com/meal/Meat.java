package com.meal;

import java.util.Random;

import com.characters.Character;
/***
 * 
 * @author Николай Коптев
 *
 */
public class Meat{
	private int increment;
	private int max = Character.MAX_COUNT_HUNGRY;
	private int min = max / 2;
	public int rndIncrement() {
		Random rnd = new Random();
		increment = rnd.nextInt(max) + min;
		return increment;
	}
}
