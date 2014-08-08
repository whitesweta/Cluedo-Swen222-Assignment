package cluedo.tile;

import java.awt.Color;

public class RoomTile implements BoardTile{
	private String name;
	
	public RoomTile(String nm){
		this.name = nm;
	}

	@Override
	public Color getColour() {
		return Color.ORANGE;
	}
}
