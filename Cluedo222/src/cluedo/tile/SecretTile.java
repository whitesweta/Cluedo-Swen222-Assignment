package cluedo.tile;

import java.awt.Color;

import cluedo.items.Room;
import cluedo.other.Player;
import cluedo.other.Position;

/**
 * Represent a SecretTile, which is a tile that connects to another secret tile
 * by a secret passageway Players are able to enter the room that the
 * corresponding secret tile links to, ending their move extends a BoardTile
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
public class SecretTile extends BoardTile {
	private Room origin;
	private Room passageTo;

	/**
	 * Construct a secret tile on the board at a given position and connecting
	 * room through secret passage way
	 * 
	 * @param p
	 *            position of tile
	 * @param o
	 *            origin
	 * @param pt
	 *            room that the passage links
	 */
	public SecretTile(Position p, Room o, Room pt) {
		super(p);
		origin = o;
		passageTo = pt;
	}

	public Room getOrigin() {
		return origin;
	}

	public Room getPassageTo() {
		return passageTo;
	}

	@Override
	public Color getColour() {
		return new Color(147, 100, 141);
	}

	@Override
	public Position posWhenMovedOut(Position newPos) {
		throw new RuntimeException("A player should not be in a secret tile");
	}

	@Override
	public void movePlayerOut() {
		throw new RuntimeException("A player should not be in a secret tile");
	}

	@Override
	public void movePlayerIn(Player p) {
		throw new RuntimeException("A player cannot move into a secret tile");
	}

	@Override
	public boolean canMoveToTile(BoardTile oldTile, Position oldPos,
			int rolledAmt) {
		return false;
	}
}
