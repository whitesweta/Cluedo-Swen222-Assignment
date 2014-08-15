package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.other.Room;

public class SecretTile extends BoardTile{
	private Room origin;
	private Room passageTo;
	
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
	public boolean canMoveToTile(BoardTile oldTile,Position oldPos, int rolledAmt) {
		return false;
	}
}
