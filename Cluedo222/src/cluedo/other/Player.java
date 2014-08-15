package cluedo.other;

import java.util.HashSet;
import java.util.Set;

import cluedo.other.Character.CharaType;
import cluedo.other.Weapon.WeaponType;

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
	
	

	public boolean isEliminated() {
		return eliminated;
	}

	public void setEliminated(boolean eliminated) {
		this.eliminated = eliminated;
	}

	public Character getCharacter() {
		return character;
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
	
	public Set<Card> getCards(){
		return cards;
	}

	public Type refuteSuggestion(Set<Type> chosenTypes){
		for(Card c:cards){
			if(chosenTypes.contains(c.cardType())){
				return c.cardType();
			}
		}
		return null;
	}
}
