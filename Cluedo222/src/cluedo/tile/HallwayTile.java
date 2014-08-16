package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

/**
 * Represents a hallway tile on the board at a given position on the canvas. A
 * Player is able to move on the hallway as many steps as they have rolled
 * extends a BoardTile
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
public class HallwayTile extends BoardTile {
	private Player playerOnTile = null;

	/**
	 * Constructs a hallway tile on the board at a given position
	 * 
	 * @param p
	 *            position of the tile
	 */
	public HallwayTile(Position p) {
		super(p);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColour() {
		return new Color(130, 201, 164);

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
