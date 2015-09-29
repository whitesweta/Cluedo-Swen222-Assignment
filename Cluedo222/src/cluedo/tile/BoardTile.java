package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

/**
 * This is a class to represent all tiles on the board to be drawn on the canvas
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
public abstract class BoardTile {
	private Position pos;

	/**
	 * Construct a boardtile with a given position on the canvas
	 * 
	 * @param p
	 *            Position on canvas
	 */
	public BoardTile(Position p) {
		pos = p;
	}

	/**
	 * Returns the position of the tile on the canvas
	 * 
	 * @return Position of tile
	 */
	public Position getPosition() {
		return pos;
	}

	/**
	 * 
	 * @return the colour give to specific Tile
	 */
	public abstract Color getColour();

	/**
	 * Moves the player onto a new position. Only useful for RoomTiles because
	 * returns closest door tile if not a roomtile it just returns the tile
	 * position
	 * 
	 * @param newPos
	 *            new Position
	 * @return the position the player will be on if it is moved out of tile
	 */
	public abstract Position posWhenMovedOut(Position newPos);

	/**
	 * Removes the player from the tile so there is no player on that tile sets
	 * the player on the tile as null
	 */
	public abstract void movePlayerOut();

	/**
	 * sets the player to be on the tile
	 * 
	 * @param p
	 *            Player to be set on tile
	 */
	public abstract void movePlayerIn(Player p);

	/**
	 * returns true if the player is able to move to the location they have
	 * clickd on based on the amount that they have rolled
	 * 
	 * @param oldTile
	 * @param oldPos
	 * @param rolledAmt
	 * @return true if able to move to position
	 */
	public abstract boolean canMoveToTile(BoardTile oldTile, Position oldPos,int rolledAmt);
}
