package cluedo.tile;

import java.awt.Color;

import cluedo.other.Room;

public class RoomTile implements BoardTile{
	private Room room;
	
	public RoomTile(Room r){
		room = r;
	}

	@Override
	public Color getColour() {
		return Color.ORANGE;
	}
}
