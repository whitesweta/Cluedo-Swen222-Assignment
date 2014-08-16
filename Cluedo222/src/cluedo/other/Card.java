package cluedo.other;

import java.awt.Image;

/**
 * Represents a card that a player can have in the game
 * 
 * @author Shweta Barapatre and Maria Libunao 
 * 
 */
public class Card {

	private Image cardImage;
	private Item item;
	
	/**
	 * Construct a card with an item and displays the corresponding card image
	 * 
	 * @param i the item that is on the card
	 * @param im the image that corresponds to the item of the card
	 */

	public Card(Item i, Image im){
		cardImage = im;
		item = i;
	}
	
	/**
	 * This method is called to get the Item of the card
	 * @return returns the item type
	 */
	public Type cardType(){
		return item.getType();
	}
	
	/**
	 * This method is called to get the Image of the card
	 * @return returns the image
	 */
	public Image getImage(){
		return cardImage;
	}

	/**
	 * @return returns the card image address and the type of item 
	 */
	@Override
	public String toString() {
		return "Card [cardImage=" + cardImage + ", item=" + item.getType() + "]";
	}
}
