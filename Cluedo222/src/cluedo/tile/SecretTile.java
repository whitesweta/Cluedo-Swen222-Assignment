package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

public class SecretTile extends BoardTile{

	public SecretTile(Position p) {
		super(p);
	}

	@Override
	public Color getColour() {
		return Color.MAGENTA;
	}

	@Override
	public Position posWhenMovedOut(Position newPos) {
		throw new RuntimeException("A player should not be in a secret tile");
	}

	@Override
	public void movePlayerOut(Player p) {
		throw new RuntimeException("A player should not be in a secret tile");
	}

	@Override
	public boolean canMoveToTile(Position oldPos, int rolledAmt) {
		return false;
	}

}
