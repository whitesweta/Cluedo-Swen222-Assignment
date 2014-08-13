package cluedo.other;

import java.awt.Image;

public class Card {
	private Image cardImage;
	private Item item;

	public Card(Item i, Image im){
		cardImage = im;
		item = i;
	}
	
	public Type cardType(){
		return item.getType();
	}

	@Override
	public String toString() {
		return "Card [cardImage=" + cardImage + ", item=" + item + "]";
	}
}
