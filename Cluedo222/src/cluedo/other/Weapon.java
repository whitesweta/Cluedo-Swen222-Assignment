package cluedo.other;

/**
 * Represent a weapon in the game, implements Type.
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */

public class Weapon implements Item {
	public enum WeaponType implements Type {
		CANDLESTICK, DAGGER, LEAD_PIPE, REVOLVER, ROPE, SPANNER
	};

	private WeaponType weapon;
	private Room room;
	private Position position;

	/**
	 * Contructs a weapon with a given weapon type enum
	 * 
	 * @param w
	 *            the Type of weapon
	 */
	public Weapon(WeaponType w) {
		weapon = w;
	}

	/**
	 * gets the position of the weapon on the canvas
	 * 
	 * @return Position of weapon
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets position of weapon on canvas
	 * 
	 * @param position
	 *            the new position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Returns the room weapon is currently in
	 * 
	 * @return room that contains weapon
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Changes the room the weapon is stored in
	 * 
	 * @param room
	 *            the new room you want the weapon to be moved into
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Returns the Type of the weapon
	 */
	@Override
	public Type getType() {
		return weapon;
	}

}
