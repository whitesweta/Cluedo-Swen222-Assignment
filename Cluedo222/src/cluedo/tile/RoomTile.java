package cluedo.tile;

import java.awt.Color;

import cluedo.items.Room;
import cluedo.other.Player;
import cluedo.other.Position;

/**
 * Represents a RoomTile on the board at a given position on the Canvas. If a
 * player can reach any of the doortile of the room,they are able to enter the
 * room that the roomtile belongs to extends a Boardtile
 * 
 * @author Shweta barapatre and Maria Libunao
 *
 */
public class RoomTile extends BoardTile {
	private Room room;
	private Player playerOnTile = null;

	/**
	 * Construct a Roomtile a a given position onto the board on the canvas
	 * 
	 * @param p
	 *            position of room tile
	 * @param r
	 *            room that is room tile is part of
	 */
	public RoomTile(Position p, Room r) {
		super(p);
		room = r;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColour() {
		return new Color(240, 230, 140);
	}

	/**
	 * {@inheritDoc}
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position posWhenMovedOut(Position newPos) {
		int minDifference = Integer.MAX_VALUE;
		Position outPos = null;
		for (DoorTile d : room.getDoortiles()) {
			int differenceX = Math.abs(newPos.getX() - d.getPosition().getX());
			int differenceY = Math.abs(newPos.getY() - d.getPosition().getY());
			if (differenceX + differenceY < minDifference) {
				outPos = d.getPosition();
				minDifference = differenceX + differenceY;
			}
		}
		return outPos;
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
	public boolean isOccupied() {
		return playerOnTile != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canMoveToTile(BoardTile oldTile, Position oldPos,
			int rolledAmt) {
		if (isOccupied()) {
			return false;
		}
		if (oldTile instanceof RoomTile) {//cannot move to another room tile with the same room
			if (((RoomTile) oldTile).room.equals(this.room)) {
				return false;
			}
		}
		for (DoorTile d : room.getDoortiles()) {//player has rolled sufficient amount to get to one of the door tiles
			int differenceX = Math.abs(oldPos.getX() - d.getPosition().getX());
			int differenceY = Math.abs(oldPos.getY() - d.getPosition().getY());
			if (differenceX + differenceY <= rolledAmt) {
				return true;
			}
		}
		return false;
	}
}
