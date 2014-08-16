package cluedo.other;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a Player in the Game. The users can choose how many players they
 * want to create for the current game.
 * 
 * @author Shweta Barapatre and Maria Libunao
 */

public class Player {
	private Character character;
	private ArrayList<Card> cards;
	private String name;
	private boolean eliminated;
	private Position position;

	/**
	 * Construct a Player in the game on the board with a character and name
	 * 
	 * @param c
	 *            The character this player has chosen to be represented by
	 * @param n
	 *            The name of the player
	 */
	public Player(Character c, String n) {
		character = c;
		cards = new ArrayList<Card>();
		name = n;
		eliminated = false;
		setInitialPosition();
	}

	/**
	 * This method adds a card to the hand of the player
	 * 
	 * @param c
	 *            card to be added
	 */
	public void addCard(Card c) {
		cards.add(c);
	}

	/**
	 * This method allows the player to move on the board
	 * 
	 * @param p
	 *            the position they want to be moved to
	 */
	public void move(Position p) {
		position = p;
	}

	/**
	 * @return returns the position the player is currently in on the board
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @return Returns the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return returns true if the player has been elimnated, false if still in
	 *         the game
	 */
	public boolean isEliminated() {
		return eliminated;
	}

	/**
	 * Sets the player as eliminated or not
	 * 
	 * @param eliminated
	 *            true if eliminated
	 */
	public void setEliminated(boolean eliminated) {
		this.eliminated = eliminated;
	}

	/**
	 * @return Returns character type of player
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * Sets initial position of the player when game has just started, depending
	 * on what character they have chosen to be
	 */
	private void setInitialPosition() {

		switch (character.getType()) {
		case MISS_SCARLET:
			position = new Position(7, 24);
			break;
		case COLONEL_MUSTARD:
			position = new Position(0, 17);
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

	/**
	 * @return returns the set of card the player has in his/her hand
	 */

	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * @return Returns a card in this players hand if it matches one of the ones
	 *         suggested. returns null if no cards match
	 * @param chosenTypes
	 *            a set of the types that have been suggested by another player
	 *            to check against
	 */
	public Type refuteSuggestion(Set<Type> chosenTypes) {
		for (Card c : cards) {
			if (chosenTypes.contains(c.cardType())) {
				return c.cardType();
			}
		}
		return null;
	}

}
