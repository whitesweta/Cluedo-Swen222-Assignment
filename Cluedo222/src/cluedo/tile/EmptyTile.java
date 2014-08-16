package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

/**
 * Represents an empty tile on the board on the canvas at a given position.
 * Player cannot be on an empty tile or move onto the tile extends a BoardTile
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
public class EmptyTile extends BoardTile {
	/**
	 * Constructs a empty tile on teh board at a given position on the canvase
	 * 
	 * @param p
	 */
	public EmptyTile(Position p) {
		super(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColour() {
		return Color.BLACK;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position posWhenMovedOut(Position newPos) {
		throw new RuntimeException("A player should not be in an empty tile");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void movePlayerIn(Player p) {
		throw new RuntimeException("A player cannot be in an empty tile");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void movePlayerOut() {
		throw new RuntimeException("A player should not be in an empty tile");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canMoveToTile(BoardTile oldTile, Position oldPos,
			int rolledAmt) {
		return false;
	}

}
