package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

public class EmptyTile extends BoardTile{

	public EmptyTile(Position p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Color getColour() {
		return Color.BLACK;

	}

	@Override
	public Position posWhenMovedOut(Position newPos) {
		throw new RuntimeException("A player should not be in an empty tile");
	}
	
	@Override
	public void movePlayerIn(Player p) {
		throw new RuntimeException("A player cannot be in an empty tile");
	}

	@Override
	public void movePlayerOut() {
		throw new RuntimeException("A player should not be in an empty tile");
	}

	@Override
	public boolean canMoveToTile(Position oldPos, int rolledAmt) {
		return false;
	}

	

}
