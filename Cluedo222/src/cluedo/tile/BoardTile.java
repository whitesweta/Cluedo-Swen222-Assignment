package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

public abstract class BoardTile {
	private Position pos;

	public BoardTile(Position p){
		pos = p;
	}
	public abstract Color getColour();
	public abstract Position posWhenMovedOut(Position newPos);
	public abstract void movePlayerOut(Player p);
	public abstract boolean canMoveToTile(Position oldPos, int rolledAmt);
	public Position getPosition(){
		return pos;
	}
}
