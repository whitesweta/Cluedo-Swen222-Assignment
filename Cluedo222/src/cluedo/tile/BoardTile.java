package cluedo.tile;

import java.awt.Color;

import cluedo.other.Position;

public abstract class BoardTile {
	private Position pos;

	public BoardTile(Position p){
		pos = p;
	}
	public abstract Color getColour();
	public Position getPosition(){
		return pos;
	}
}
