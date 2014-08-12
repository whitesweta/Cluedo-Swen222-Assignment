package cluedo.tile;

import java.awt.Color;

import cluedo.other.Position;

public class DoorTile extends BoardTile{

	public DoorTile(Position p) {
		super(p);
	}

	@Override
	public Color getColour() {
		return Color.GRAY;

	}

}
