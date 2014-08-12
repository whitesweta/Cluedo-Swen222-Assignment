package cluedo.tile;

import java.awt.Color;

import cluedo.other.Position;

public class SecretTile extends BoardTile{

	public SecretTile(Position p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Color getColour() {
		return Color.MAGENTA;
	}

}
