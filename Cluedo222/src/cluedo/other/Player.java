package cluedo.other;

import java.util.HashSet;
import java.util.Set;

public class Player {
	private Character character;
	private Set<Card> cards;
	private String name;
	private boolean eliminated;
	private Position position;

	public Player(Character c,String n){
		character = c;
		cards = new HashSet<Card>();
		name = n;
		eliminated = false;
		setInitialPosition();
	}

	public void addCard(Card c){
		cards.add(c);
	}

	public void move(Position p){
		position = p;
	}

	public Position getPosition() {
		return position;
	}
	
	public Card refuteSuggestion(Weapon.WeaponType w, Character.Name c){
		Card wCard = new Card(new Weapon(w),null);
		if(cards.contains(wCard)){
			return wCard;
		}
		Card cCard = new Card(new Character(c),null);
		if(cards.contains(cCard)){
			return cCard;
		}		
		return null;
	}

	private void setInitialPosition(){

		switch(character.getType()){
		case MISS_SCARLET:
			position = new Position(7,24);
			break;
		case COLONEL_MUSTARD:
			position = new Position(0,17);
			break;
		case MRS_WHITE:
			position = new Position(9, 0);
			break;
		case REVEREND_GREEN:
			position = new Position(14, 0);
			break;
		case MRS_PEACOCK:
			position = new Position(23, 6);
			break;
		case PROFESSOR_PLUM:
			position = new Position(23, 19);
			break;
		}
	}
}
