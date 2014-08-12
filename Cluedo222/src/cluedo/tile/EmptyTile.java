package cluedo.tile;

import java.awt.Color;

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

}
