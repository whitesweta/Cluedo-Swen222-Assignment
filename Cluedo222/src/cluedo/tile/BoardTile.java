package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

public abstract class BoardTile {
	private Position pos;

	public BoardTile(Position p){
		pos = p;
	}

	public Position getPosition(){
		return pos;
	}
	
	public abstract Color getColour();
	public abstract Position posWhenMovedOut(Position newPos);
	public abstract void movePlayerOut();
	public abstract void movePlayerIn(Player p);
	public abstract boolean canMoveToTile(BoardTile oldTile,Position oldPos, int rolledAmt);
}
