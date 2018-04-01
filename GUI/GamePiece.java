package GUI2;

import java.awt.Color;

/**
 * 
 * Class GamePiece contains getters and setters to enable the game to be played
 * and colours to be retrieved from the board.
 * 
 * @author Benjamin Fellows, Tori Keegan
 *
 */

public class GamePiece {

	String word = "";
	Color colour;
	Boolean clicked;

	/**
	 * Constructor for the GamePiece class
	 * 
	 * @param word
	 *            is the word
	 * @param colour
	 *            is the color associated with the word
	 * @param clicked
	 *            indicates whether the tile has been selected
	 */

	public GamePiece(String word, Color colour, Boolean clicked) {
		this.word = word;
		this.colour = colour;
		this.clicked = clicked;
	}
	
	/**
	 * Method to get the word 
	 * @return word 
	 */

	public String getWord() {
		return word;
	}
	
	/**
	 * Method to set the word
	 * @param word
	 */

	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * Method to get the color
	 * @return color
	 */

	public Color getColour() {
		return colour;
	}
	
	/**
	 * Method to set the color
	 * @param color
	 */

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	/**
	 * Boolean method to see if the tile has been clicked
	 * @return clicked
	 */

	public Boolean getClicked() {
		return clicked;
	}
	
	/**
	 * Method to set if it's been clicked
	 * @param clicked
	 */

	public void setClicked(Boolean clicked) {
		this.clicked = clicked;
	}

}
