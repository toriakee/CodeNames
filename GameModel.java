package GUI2;

import java.awt.Color;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JTextField;

import Database.GameWordsMethods;

public class GameModel extends Observable {

	// Constants
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	public static final Color bckGrd = new Color(160, 160, 160);
	public static final Color red = new Color(255, 51, 51);
	public static final Color blue = new Color(51, 153, 255);
	// public static final Color yellow = new Color(255, 255, 102);

	// Hint TextFields
	public static JTextField spyHint;
	public static JTextField playerHint;

	// Score Calculator
	public static int blueScoreCounter = 0;
	// public JLabel redScore = new JLabel();
	// public JLabel blueScore = new JLabel();

	// <<<<<<<Field Variables for ButtonPanel>>>>>>>

	// GameButton array to hold buttons
	GameButton[] playerButtonArr = new GameButton[25];
	GameButton[] spyButtonArr = new GameButton[25];

	// Initial words array to be pulled from Database
	String[] wordsInit;

	String[] colorsInit = { "red", "red", "red", "red", "red", "blue", "blue", "blue", "blue", "blue", "blue", "blue",
			"blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue" };

	String[] wordsArr;
	String[] colorsArr = shuffleStringArray(colorsInit);

	public GameModel() {
		try {
			wordsArr = GameWordsMethods.randomSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup button arrays
		for (int i = 0; i < playerButtonArr.length; i++) {
			// Add to playerButtonArr
			JButton playerButton = new JButton();
			playerButton.setOpaque(true);
			GameButton playerGameButton = new GameButton(playerButton);
			playerButtonArr[i] = playerGameButton;

			// Add to spyButtonArr
			JButton spyButton = new JButton();
			playerButton.setOpaque(true);
			GameButton spyGameButton = new GameButton(spyButton);
			spyButtonArr[i] = spyGameButton;
		}

	}

	// HELPER METHODS
	/**
	 * Method to shuffle array of Colors.
	 * 
	 * @param ar
	 *            an array of Colors
	 */
	public Color[] shuffleColorArray(Color[] ar) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Color a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
		return ar;
	}

	/**
	 * EDIT
	 * 
	 * 
	 * Method to shuffle array of Strings Will be redundant once words are
	 * pulled from Database at random
	 * 
	 * @param ar
	 * @return
	 */
	public String[] shuffleStringArray(String[] ar) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			String a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
		return ar;
	}

	/**
	 * Method to retrieve the blue team score
	 * 
	 * @return blueScoreCounter of type integer which is incremented each time
	 *         the blue tile is selected by Player.
	 */

	public int getBlueScoreCounter() {
		return blueScoreCounter;
	}

	public void setBlueScoreCounter(int blueScoreCounter) {
		GameModel.blueScoreCounter = blueScoreCounter;
	}

	public String[] getWordsArr() {
		return wordsArr;
	}

	public String[] getColorsArr() {
		return colorsArr;
	}

	/**
	 * EDIT: RETURN
	 * 
	 * Method to ensure hint input is only 1 word
	 * 
	 * @param word
	 * @return
	 */
	public boolean stringWhiteSpaceCheck(String word) {

		Pattern pattern = Pattern.compile(" ");
		Matcher matcher = pattern.matcher(word);
		boolean found = matcher.find();
		return found;
	}

	/**
	 * 
	 * EDIT: RETURN
	 * 
	 * Method to ensure hint input: tile selection is only 1 word
	 * 
	 * @param word
	 * @return
	 */
	public boolean stringSpecialCharacterCheck(String word) {

		Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(word);
		boolean found = matcher.find();
		return found;
	}

}
