package cluedo.other;


import java.util.HashSet;
import java.util.Set;

public class Player {
	private Character character;
	private Set<Card> cards;
	private String name;
	private boolean eliminated;
	private int x;
	private int y;

	public Player(Character c,String n){
		character = c;
		cards = new HashSet<Card>();
		name = n;
		eliminated = false;
	}
}
