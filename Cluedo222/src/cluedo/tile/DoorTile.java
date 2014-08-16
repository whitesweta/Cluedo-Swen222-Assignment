package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

/**
 * Represent a doortile on the board on the canvas Players can move onto a door
 * tile if they have enough steps. They are then able to enter the room that the
 * door is connected to extends a Boardtile
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */

public class DoorTile extends BoardTile {
	private Player playerOnTile = null;

	/**
	 * Construct a DoorTile with a given position
	 * 
	 * @param p
	 *            position of doortile
	 */
	public DoorTile(Position p) {
		super(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColour() {
		return new Color(170, 117, 112);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position posWhenMovedOut(Position newPos) {
		return this.getPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void movePlayerOut() {
		playerOnTile = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void movePlayerIn(Player p) {
		playerOnTile = p;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canMoveToTile(BoardTile oldTile, Position oldPos,
			int rolledAmt) {
		if (playerOnTile != null) {
			return false;
		}
		int differenceX = Math.abs(oldPos.getX() - this.getPosition().getX());
		int differenceY = Math.abs(oldPos.getY() - this.getPosition().getY());
		return differenceX + differenceY <= rolledAmt;
	}

}
